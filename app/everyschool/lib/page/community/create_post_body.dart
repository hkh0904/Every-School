import 'dart:io';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:provider/provider.dart';
import 'package:file_picker/file_picker.dart';
import 'package:everyschool/api/community_api.dart';

class CreatePostBody extends StatefulWidget {
  const CreatePostBody({Key? key}) : super(key: key);

  @override
  State<CreatePostBody> createState() => _CreatePostBodyState();
}

class _CreatePostBodyState extends State<CreatePostBody> {
  final CommunityApi communityApi = CommunityApi();
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _contentController = TextEditingController();
  bool _isCommentAllowed = true;
  final List<File> _filePaths = [];
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  Future<void> pickFile() async {
    FilePickerResult? result =
        await FilePicker.platform.pickFiles(allowMultiple: true);

    if (result != null) {
      List<File> newFiles =
          result.paths.map((path) => File(path as String)).toList();
      setState(() {
        _filePaths.addAll(newFiles);
      });
    }
  }

  sendPost() async {
    Map<String, dynamic> data = {
      "title": _titleController.text,
      "content": _contentController.text,
      "isUsedComment": _isCommentAllowed,
    };

    if (_filePaths.isNotEmpty) {
      data["files"] = _filePaths
          .map((file) => MultipartFile.fromFileSync(file.path))
          .toList();
    }

    FormData formData = FormData.fromMap(data);

    final schoolId = context.read<UserStore>().userInfo['school']['schoolId'];
    final schoolYear =
        context.read<UserStore>().userInfo['schoolClass']['schoolYear'];

    // API 호출
    var response = communityApi.createPost(schoolYear, schoolId, formData);
    if (response.runtimeType != Null) {
      bool shouldPop = await showDialog(
        barrierDismissible: false,
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
                    child: Text("작성이 완료되었습니다.",
                        style: TextStyle(
                            fontWeight: FontWeight.w700, fontSize: 18))),
                SizedBox(
                  height: 20,
                ),
                SizedBox(
                  height: 20,
                ),
                GestureDetector(
                  onTap: () {
                    Navigator.of(context).pop(true); // true를 반환
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
      // showDialog에서 true가 반환되면, 이전 페이지로 돌아갑니다.
      if (shouldPop) {
        Navigator.of(_scaffoldKey.currentContext!).pop('refresh');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
      },
      child: WillPopScope(
        onWillPop: () async {
          Navigator.pop(context); // 이전 페이지로 돌아갑니다.
          return true;
        },
        child: Scaffold(
          key: _scaffoldKey,
          body: SingleChildScrollView(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  '제목',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w700,
                  ),
                ),
                SizedBox(height: 10),
                TextFormField(
                  controller: _titleController,
                  decoration: InputDecoration(
                    contentPadding:
                        EdgeInsets.symmetric(vertical: 10, horizontal: 15),
                    hintText: '제목을 입력하세요',
                    border: OutlineInputBorder(),
                    focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(color: Color(0XFF15075F), width: 1),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Text(
                  '내용',
                  style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.w700,
                  ),
                ),
                SizedBox(height: 10),
                TextFormField(
                  controller: _contentController,
                  decoration: InputDecoration(
                    contentPadding:
                        EdgeInsets.symmetric(vertical: 10, horizontal: 15),
                    hintText: '내용을 입력하세요',
                    border: OutlineInputBorder(),
                    focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(color: Color(0XFF15075F), width: 1),
                    ),
                  ),
                  maxLines: 15,
                ),
                SizedBox(height: 20),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      '첨부파일',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Transform.translate(
                          offset: Offset(20.0, 0.0), // X축으로 10 만큼 이동시킵니다.
                          child: Text(
                            '댓글 허용',
                            style: TextStyle(
                              color: Colors.grey,
                              fontSize: 16,
                              fontWeight: FontWeight.w700,
                            ),
                          ),
                        ),
                        Transform.translate(
                          offset: Offset(10.0, 0.0), // X축으로 -10 만큼 이동시킵니다.
                          child: Checkbox(
                            value: _isCommentAllowed,
                            onChanged: (bool? newValue) {
                              setState(() {
                                _isCommentAllowed = newValue!;
                              });
                            },
                            activeColor: Color(0XFF15075F),
                            checkColor: Colors.white,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
                SizedBox(height: 10),
                Container(
                  padding: EdgeInsets.fromLTRB(20, 0, 10, 0),
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.grey,
                      width: 1.0,
                    ),
                  ),
                  child: Column(
                    children: [
                      for (var file in _filePaths)
                        Row(
                          children: [
                            Expanded(
                              child: Text(
                                file.path
                                    .split('/')
                                    .last, // This will give you the file name
                                style: TextStyle(
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            IconButton(
                              iconSize: 30,
                              onPressed: () {
                                setState(() {
                                  _filePaths.remove(file); // 파일 삭제
                                });
                              },
                              icon: Icon(
                                Icons.remove,
                                color: Colors.red,
                              ),
                            ),
                          ],
                        ),
                      Row(
                        children: [
                          Expanded(
                            child: _filePaths.isEmpty
                                ? Text(
                                    '파일을 추가하세요.',
                                    style: TextStyle(
                                      color: Colors.grey,
                                    ),
                                  )
                                : Container(),
                          ),
                          IconButton(
                            iconSize: 30,
                            onPressed: pickFile,
                            icon: Icon(
                              Icons.add,
                              color: Color(0XFF15075F),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
                SizedBox(
                  height: 30,
                ),
                Row(
                  children: [
                    Expanded(
                        // Expanded 위젯을 사용하여 버튼이 가능한 모든 공간을 차지하도록 합니다.
                        child: ElevatedButton(
                      onPressed: _titleController.text.isNotEmpty &&
                              _contentController.text.isNotEmpty
                          ? sendPost
                          : null,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: _titleController.text.isNotEmpty &&
                                _contentController.text.isNotEmpty
                            ? Color(0XFF15075F)
                            : Colors.grey[50],
                        shape: RoundedRectangleBorder(),
                        padding: EdgeInsets.symmetric(vertical: 13),
                      ),
                      child: Text(
                        '작성 완료',
                        style: TextStyle(
                          color: _titleController.text.isNotEmpty &&
                                  _contentController.text.isNotEmpty
                              ? Colors.white
                              : Colors.grey[700],
                          fontSize: 20,
                          fontWeight: FontWeight.w700,
                        ),
                      ),
                    )),
                  ],
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
