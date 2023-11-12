import 'dart:convert';

import 'package:everyschool/api/consulting_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/consulting/consulting_reason.dart';
import 'package:everyschool/page/consulting/consulting_teacher_info.dart';
import 'package:everyschool/page/consulting/select_date.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class ConsultingReservation extends StatefulWidget {
  const ConsultingReservation({super.key});

  @override
  State<ConsultingReservation> createState() => _ConsultingReservationState();
}

class _ConsultingReservationState extends State<ConsultingReservation> {
  String? selectedType;
  DateTime? selDate;
  String? selTime;
  String? selReason;

  void consultApprove(String? selectedType, DateTime? selDate, String? selTime,
      String? selReason) {
    if (selectedType != null &&
        selDate != null &&
        selTime != null &&
        selReason != null) {
      print('신청이 완료되었습니다');
    } else {
      print('입력 정보를 확인해주세요');
    }
  }

  getSchedule() async {
    final storage = FlutterSecureStorage();

    final year = await context.read<UserStore>().year;
    final descendantInfo = await storage.read(key: 'descendant') ?? "";
    var selectDescendant = jsonDecode(descendantInfo);

    var schoolId = selectDescendant['school']['schoolId'];

    var teacherInfo = await ConsultingApi().getTeacherId(year, schoolId);
    print(teacherInfo);
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    getSchedule();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        leading: BackButton(
          color: Colors.black,
        ),
        elevation: 0,
        title: Text(
          '상담 신청',
          style: TextStyle(color: Colors.black, fontWeight: FontWeight.w700),
        ),
        centerTitle: true,
        actions: [
          IconButton(
              icon: const Icon(Icons.notifications_none, color: Colors.black),
              visualDensity:
                  const VisualDensity(horizontal: -4.0, vertical: -4.0),
              padding: const EdgeInsets.all(0),
              alignment: Alignment.center,
              splashRadius: 24.0,
              onPressed: () {}),
          IconButton(
              icon: const Icon(Icons.settings, color: Colors.black),
              visualDensity:
                  const VisualDensity(horizontal: -2.0, vertical: -4.0),
              padding: const EdgeInsets.all(0),
              alignment: Alignment.center,
              splashRadius: 24.0,
              onPressed: () {})
        ],
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: EdgeInsets.fromLTRB(30, 20, 30, 20),
          child:
              Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
            ConsultingTeacherInfo(),
            Container(
              margin: EdgeInsets.fromLTRB(0, 20, 0, 20),
              child: Text(
                '상담 유형 선택',
                style: TextStyle(fontWeight: FontWeight.w600, fontSize: 18),
              ),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                GestureDetector(
                  onTap: () {
                    setState(() {
                      selectedType = '방문 상담';
                    });
                  },
                  child: Row(
                    children: [
                      Radio(
                        value: '방문 상담',
                        groupValue: selectedType,
                        onChanged: (value) {
                          setState(() {
                            selectedType = value.toString();
                          });
                        },
                        visualDensity:
                            VisualDensity(horizontal: -4, vertical: -4),
                        activeColor: Color(0xff8975EC),
                      ),
                      Text('방문 상담', style: TextStyle(fontSize: 16)),
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
                        visualDensity:
                            VisualDensity(horizontal: -4, vertical: -4),
                        activeColor: Color(0xff8975EC),
                      ),
                      Text('전화상담', style: TextStyle(fontSize: 16)),
                    ],
                  ),
                ),
              ],
            ),
            SelectDate(
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
                onPressed: () {},
                style: TextButton.styleFrom(
                  padding: EdgeInsets.fromLTRB(0, 15, 0, 15),
                  backgroundColor: Color(0xff15075F),
                ),
                child: GestureDetector(
                  onTap: () {
                    print(selectedType);
                    print(selDate);
                    print(selTime);
                    print(selReason);
                    consultApprove(selectedType, selDate, selTime, selReason);
                  },
                  child: Text('상담 신청하기',
                      style: TextStyle(color: Colors.white, fontSize: 18)),
                ),
              ),
            )
          ]),
        ),
      ),
    );
  }
}
