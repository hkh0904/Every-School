import 'dart:convert';

import 'package:everyschool/api/consulting_api.dart';
import 'package:everyschool/api/firebase_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/consulting/consulting_reason.dart';
import 'package:everyschool/page/consulting/consulting_teacher_info.dart';
import 'package:everyschool/page/consulting/select_date.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class ConsultingReservation extends StatefulWidget {
  const ConsultingReservation({super.key, this.updatePage});
  final updatePage;

  @override
  State<ConsultingReservation> createState() => _ConsultingReservationState();
}

class _ConsultingReservationState extends State<ConsultingReservation> {
  String? selectedType;
  DateTime? selDate;
  String? selTime;
  String? selReason;

  var Monday;

  void consultApprove(teacherUserKey, myUserKey) async {
    if (selectedType != null &&
        selDate != null &&
        selTime != null &&
        selReason != null) {
      DateTime combinedDateTime = DateTime(
        selDate!.year,
        selDate!.month,
        selDate!.day,
        int.parse(selTime!.split(':')[0]),
        int.parse(selTime!.split(':')[1]),
      );

      // DateTime 객체를 리스트로 변환
      List<int> dateTimeList = [
        combinedDateTime.year,
        combinedDateTime.month,
        combinedDateTime.day,
        combinedDateTime.hour,
        combinedDateTime.minute,
        combinedDateTime.second,
        combinedDateTime.millisecond,
      ];

      int typeNum;

      if (selectedType == '방문 상담') {
        typeNum = 4001;
      } else {
        typeNum = 4002;
      }

      final year = context.read<UserStore>().year;

      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);

      var result = await ConsultingApi().registerConsult(
          year,
          selectDescendant['school']['schoolId'],
          teacherUserKey,
          selectDescendant['userKey'],
          typeNum,
          dateTimeList,
          selReason);

      if (result['message'] == 'CREATED') {
        showDialog(
          context: context,
          builder: (BuildContext context) {
            return AlertDialog(
              title: Text("신청 완료"),
              content: Text("상담 신청이 완료되었습니다."),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.of(context).pop();
                    Navigator.of(context).pop();
                  },
                  child: Text("확인"),
                ),
              ],
            );
          },
        );

        setState(() {
          selectedType = null;
          selDate = null;
          selTime = null;
          selReason = null;
        });
        widget.updatePage();
      }
    } else {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          // AlertDialog를 반환합니다.
          return AlertDialog(
            title: Text("신청 실패"),
            content: Text("입력 정보를 확인해주세요."),
            actions: [
              // 확인 버튼
              TextButton(
                onPressed: () {
                  // Modal을 닫습니다.
                  Navigator.of(context).pop();
                },
                child: Text("확인"),
              ),
            ],
          );
        },
      );
    }
  }

  getSchedule() async {
    final storage = FlutterSecureStorage();

    final year = await context.read<UserStore>().year;
    final descendantInfo = await storage.read(key: 'descendant') ?? "";
    var selectDescendant = jsonDecode(descendantInfo);

    var schoolId = selectDescendant['school']['schoolId'];
    var classId = selectDescendant['schoolClass']['schoolClassId'];

    var teacherInfo = await ConsultingApi().getTeacherId(year, schoolId);

    var consultSchedule = await ConsultingApi()
        .getConsultSchedule(schoolId, year, schoolId, classId);

    return {"teacherInfo": teacherInfo, "consultSchedule": consultSchedule};
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getSchedule(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
              appBar: AppBar(
                  backgroundColor: Colors.grey[50],
                  leading: BackButton(
                    color: Colors.black,
                  ),
                  elevation: 0,
                  title: Text(
                    '상담 신청',
                    style: TextStyle(
                        color: Colors.black, fontWeight: FontWeight.w700),
                  ),
                  centerTitle: true),
              body: SingleChildScrollView(
                child: Padding(
                  padding: EdgeInsets.fromLTRB(30, 20, 30, 20),
                  child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        ConsultingTeacherInfo(
                            teacherInfo: snapshot.data['teacherInfo'],
                            consultSchedule: snapshot.data['consultSchedule']),
                        Container(
                          margin: EdgeInsets.fromLTRB(0, 20, 0, 20),
                          child: Text(
                            '상담 유형 선택',
                            style: TextStyle(
                                fontWeight: FontWeight.w600, fontSize: 18),
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                          children: [
                            GestureDetector(
                              onTap: () {
                                setState(() {
                                  selectedType = '방문상담';
                                });
                              },
                              child: Row(
                                children: [
                                  Radio(
                                    value: '방문상담',
                                    groupValue: selectedType,
                                    onChanged: (value) {
                                      setState(() {
                                        selectedType = value.toString();
                                      });
                                    },
                                    visualDensity: VisualDensity(
                                        horizontal: -4, vertical: -4),
                                    activeColor: Color(0xff8975EC),
                                  ),
                                  Text('방문상담', style: TextStyle(fontSize: 16)),
                                ],
                              ),
                            ),
                            GestureDetector(
                              onTap: () {
                                setState(() {
                                  selectedType = '전화상담';
                                });
                              },
                              child: Row(
                                children: [
                                  Radio(
                                    value: '전화상담',
                                    groupValue: selectedType,
                                    onChanged: (value) {
                                      setState(() {
                                        selectedType = value.toString();
                                      });
                                    },
                                    visualDensity: VisualDensity(
                                        horizontal: -4, vertical: -4),
                                    activeColor: Color(0xff8975EC),
                                  ),
                                  Text('전화상담', style: TextStyle(fontSize: 16)),
                                ],
                              ),
                            ),
                          ],
                        ),
                        SelectDate(
                          selDate: selDate,
                          scheduleTimes: snapshot.data['consultSchedule'],
                          onDateSelected: (date) {
                            setState(() {
                              selDate = date;
                            });
                          },
                          onTimeSelected: (time) {
                            setState(() {
                              selTime = time;
                            });
                          },
                        ),
                        ConsultingReason(onReasonSelected: (reason) {
                          setState(() {
                            selReason = reason;
                          });
                        }),
                        SizedBox(
                          width: MediaQuery.of(context).size.width,
                          child: TextButton(
                            onPressed: () async {
                              var storage = FlutterSecureStorage();
                              var myUserKey =
                                  await storage.read(key: 'userKey') ?? "";
                              var teacherUserKey =
                                  snapshot.data['teacherInfo']['userKey'];
                              consultApprove(teacherUserKey, myUserKey);
                            },
                            style: TextButton.styleFrom(
                              padding: EdgeInsets.fromLTRB(0, 15, 0, 15),
                              backgroundColor: Color(0xff15075F),
                            ),
                            child: Text('상담 신청하기',
                                style: TextStyle(
                                    color: Colors.white, fontSize: 18)),
                          ),
                        )
                      ]),
                ),
              ),
            );
          } else if (snapshot.hasError) {
            return Container(
              height: 800,
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
