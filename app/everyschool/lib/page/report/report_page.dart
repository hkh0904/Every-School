import 'dart:io';

import 'package:dio/dio.dart';
import 'package:everyschool/api/report_api.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ReportPage extends StatefulWidget {
  const ReportPage({super.key, this.updateRepPage});
  final updateRepPage;

  @override
  State<ReportPage> createState() => _ReportPageState();
}

class _ReportPageState extends State<ReportPage> {
  final List<Map<String, dynamic>> _reportTypes = [
    {'title': '학칙위반(흡연, 기물파손 등)', 'number': 9003},
    {'title': '학교폭력', 'number': 9001},
    {'title': '도난, 절도', 'number': 9002},
    {'title': '악성민원', 'number': 9004},
    {'title': '기타', 'number': 9000}
  ];

  String? typeId;
  String? _selectedType;
  DateTime? _selectedDate;
  TimeOfDay? _selectedTime;
  String? _locationInput;
  String? _suspectInput;
  String? _detailInput;
  List<File> _filePaths = [];
  final TextEditingController _fileController = TextEditingController();

  postFile() async {
    FormData formData = FormData.fromMap({
      "typeId": typeId,
      "files": _filePaths
          .map((file) => MultipartFile.fromFileSync(file.path))
          .toList(),
      "description": _detailInput,
      "who": _suspectInput,
      "when": '${_selectedDate.toString()} ${_selectedTime.toString()}',
      "where": _locationInput,
      "what": _selectedType,
      "how": null,
      "why": null
    });

    final year = context.read<UserStore>().year;
    final schoolId = context.read<UserStore>().userInfo['school']['schoolId'];

    var response = await ReportApi().writeReport(year, schoolId, formData);
    if (response.runtimeType != Null) {
      showDialog(
        context: context,
        builder: ((context) {
          return AlertDialog(
            contentPadding: EdgeInsets.zero,
            content: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                SizedBox(
                  height: 40,
                ),
                Center(
                    child: Text("신고가 완료되었습니다.",
                        style: TextStyle(
                            fontWeight: FontWeight.w700, fontSize: 18))),
                SizedBox(
                  height: 20,
                ),
                Center(
                    child: Padding(
                  padding: const EdgeInsets.fromLTRB(20, 0, 20, 0),
                  child: Text("진행 사항은 신고 목록에서 확인 가능합니다.",
                      style: TextStyle(fontSize: 16)),
                )),
                SizedBox(
                  height: 40,
                ),
                GestureDetector(
                  onTap: () {
                    widget.updateRepPage();
                    Navigator.of(context).pop();
                    Navigator.of(context).pop();
                  },
                  child: Container(
                    height: 50,
                    color: Color(0xff15075f),
                    child: Center(
                      child: Text(
                        "확인",
                        style: TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.w700,
                            fontSize: 18),
                      ),
                    ),
                  ),
                ),
              ],
            ),
          );
        }),
      );
    }
  }

  String extractFileName(String filePath) {
    List<String> parts = filePath.split('/');
    return parts.isNotEmpty ? parts.last : filePath;
  }

  Future<void> _pickFiles() async {
    FilePickerResult? result =
        await FilePicker.platform.pickFiles(allowMultiple: true);

    if (result != null) {
      List<File> newFiles =
          result.paths.map((path) => File(path as String)).toList();
      setState(() {
        _filePaths.addAll(newFiles);
      });

      _fileController.text = _filePaths.isNotEmpty
          ? _filePaths.map((file) => extractFileName(file.path)).join(", ")
          : "첨부 파일이 없습니다";
    } else {
      // 사용자가 선택을 취소한 경우
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('신고하기',
            style: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.w700,
            )),
        centerTitle: true,
        backgroundColor: Colors.grey[50],
        elevation: 0,
        leading: BackButton(color: Colors.black),
      ),
      body: SingleChildScrollView(
        child: Container(
          margin: EdgeInsets.fromLTRB(20, 20, 20, 20),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 5),
                child:
                    Text('분류 선택', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
                child: DecoratedBox(
                  decoration: BoxDecoration(
                      border: Border.all(color: Color(0xffd9d9d9), width: 1)),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                    child: DropdownButton(
                      hint: Text('신고 분류를 선택해주세요'),
                      value: _selectedType,
                      items: _reportTypes
                          .map<DropdownMenuItem<String>>(
                              (type) => DropdownMenuItem<String>(
                                    value: type['title'] as String,
                                    child: Text(type['title'] as String),
                                  ))
                          .toList(),
                      onChanged: (value) {
                        // 선택한 분류의 number를 typeId에 저장
                        setState(() {
                          _selectedType = value!;
                          typeId = _reportTypes
                              .firstWhere(
                                  (type) => type['title'] == value)['number']
                              .toString();
                        });
                      },
                      underline: Container(),
                      isExpanded: true,
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 10, 0, 5),
                child: Text('일시', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Row(
                children: [
                  Expanded(
                    child: Container(
                      margin: EdgeInsets.fromLTRB(0, 0, 5, 0),
                      decoration: BoxDecoration(
                          border:
                              Border.all(color: Color(0xffd9d9d9), width: 1)),
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                        child: TextField(
                          readOnly: true,
                          onTap: () async {
                            DateTime? selectedDate = await showDatePicker(
                              context: context,
                              initialDate: DateTime.now(),
                              firstDate: DateTime(2000),
                              lastDate: DateTime(2101),
                            );

                            if (selectedDate != null &&
                                selectedDate != _selectedDate) {
                              setState(() {
                                _selectedDate = selectedDate;
                              });
                            }
                          },
                          controller: TextEditingController(
                            text: _selectedDate != null
                                ? "${_selectedDate!.year}-${_selectedDate!.month}-${_selectedDate!.day}"
                                : "",
                          ),
                          decoration: InputDecoration(
                              border: InputBorder.none, hintText: '날짜를 선택하세요'),
                        ),
                      ),
                    ),
                  ),
                  Expanded(
                    child: Container(
                      margin: EdgeInsets.fromLTRB(5, 0, 0, 0),
                      decoration: BoxDecoration(
                          border:
                              Border.all(color: Color(0xffd9d9d9), width: 1)),
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                        child: TextField(
                          readOnly: true,
                          onTap: () async {
                            TimeOfDay? selectedTime = await showTimePicker(
                              context: context,
                              initialTime: TimeOfDay.now(),
                            );

                            if (selectedTime != null &&
                                selectedTime != _selectedTime) {
                              setState(() {
                                _selectedTime = selectedTime;
                              });
                            }
                          },
                          controller: TextEditingController(
                            text: _selectedTime != null
                                ? "${_selectedTime!.period == DayPeriod.am ? '오전' : '오후'} ${_selectedTime!.hourOfPeriod}시 ${_selectedTime!.minute}분"
                                : "",
                          ),
                          decoration: InputDecoration(
                              border: InputBorder.none, hintText: '시간을 선택하세요'),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 5),
                child: Text('장소', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Color(0xffd9d9d9), width: 1),
                ),
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                  child: TextField(
                    textDirection: TextDirection.ltr,
                    onChanged: (value) {
                      _locationInput = value;
                    },
                    controller: TextEditingController(text: _locationInput),
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: '발생 장소를 입력해주세요',
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 5),
                child: Text('주체', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Color(0xffd9d9d9), width: 1),
                ),
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                  child: TextField(
                    textDirection: TextDirection.ltr,
                    onChanged: (value) {
                      _suspectInput = value;
                    },
                    controller: TextEditingController(text: _suspectInput),
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: '사건 행위자의 정보를 입력해주세요',
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 5),
                child:
                    Text('상세 내용', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Color(0xffd9d9d9), width: 1),
                ),
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                  child: TextField(
                    keyboardType: TextInputType.multiline,
                    minLines: 6,
                    maxLines: null,
                    onChanged: (value) {
                      setState(() {
                        _detailInput = value;
                      });
                    },
                    // controller: TextEditingController(text: _detailInput ?? ""),
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: '상세 내용을 입력해주세요',
                    ),
                  ),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 5),
                child:
                    Text('첨부 파일', style: TextStyle(color: Color(0xff4A5056))),
              ),
              Row(
                children: [
                  Container(
                    width: MediaQuery.of(context).size.width * 0.7,
                    height: 50,
                    decoration: BoxDecoration(
                      border: Border.all(color: Color(0xffd9d9d9), width: 1),
                    ),
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: TextField(
                        readOnly: true,
                        controller: _fileController,
                        decoration: InputDecoration(
                          border: InputBorder.none,
                          hintText: '첨부 파일이 없습니다',
                        ),
                      ),
                    ),
                  ),
                  Expanded(
                    child: SizedBox(
                      height: 50,
                      child: ElevatedButton(
                        style: ElevatedButton.styleFrom(
                            backgroundColor: Color(0xff15075F),
                            shape: LinearBorder(),
                            elevation: 0),
                        onPressed: _pickFiles,
                        child: Text('첨부하기'),
                      ),
                    ),
                  )
                ],
              ),
              Container(
                margin: EdgeInsets.fromLTRB(0, 10, 0, 0),
                child: const Row(
                  children: [
                    Icon(Icons.priority_high_rounded,
                        color: Color(0xff4A5056), size: 14),
                    Text(
                      '허위 신고는 금지됩니다.',
                      style: TextStyle(color: Color(0xff4A5056)),
                    )
                  ],
                ),
              )
            ],
          ),
        ),
      ),
      bottomNavigationBar: SizedBox(
        height: 60,
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
              backgroundColor: Color(0xff15075F), shape: LinearBorder()),
          onPressed: _selectedType != null &&
                  typeId != null &&
                  _selectedDate != null &&
                  _selectedTime != null &&
                  _locationInput != null &&
                  _suspectInput != null &&
                  _detailInput != null
              ? () {
                  // 버튼이 활성화된 경우 실행할 동작
                  postFile();
                }
              : null,
          child: Text(
            '신고하기',
            style: TextStyle(
                color: Colors.white, fontSize: 18, fontWeight: FontWeight.w700),
          ),
        ),
      ),
    );
  }
}
