import 'package:everyschool/page/report/report_card.dart';
import 'package:flutter/material.dart';

class TeacherReportGetPage extends StatefulWidget {
  const TeacherReportGetPage({super.key});

  @override
  State<TeacherReportGetPage> createState() => _TeacherReportGetPageState();
}

class _TeacherReportGetPageState extends State<TeacherReportGetPage> {
  var pastReportingList = [
    {
      'type': '학교폭력',
      'dateTime': '2023.09.12 14:00',
      'name': '1학년 3반 OOO',
      'state': 'waiting'
    },
    {
      'type': '학교폭력',
      'dateTime': '2023.09.11 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': 'waiting'
    },
    {
      'type': '도난신고',
      'dateTime': '2023.09.10 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': 'waiting'
    },
  ];

  var waitingReportingList = [
    {
      'type': '학교폭력',
      'dateTime': '2023.09.12 14:00',
      'name': '1학년 3반 OOO',
      'state': 'past'
    },
    {
      'type': '학교폭력',
      'dateTime': '2023.09.11 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': 'past'
    },
    {
      'type': '도난신고',
      'dateTime': '2023.09.10 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': 'past'
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SingleChildScrollView(
      child: Container(
        color: Color(0xffF5F5F5),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
                margin: EdgeInsets.fromLTRB(30, 25, 30, 0),
                child: Text(
                  '처리 대기 중',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
                )),
            Container(
                margin: EdgeInsets.fromLTRB(30, 5, 30, 15),
                padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
                color: Color(0xffF4F6FD),
                child: Text(
                  '📍 신고내역 처리는 관리자 페이지에서 가능하며, 어플에서는 조회만 가능합니다.',
                  style: TextStyle(fontSize: 15),
                )),
            ReportCard(state: 'waiting', reportingList: waitingReportingList),
            Container(
                margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                child: Text(
                  '이전 신고 내역',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
                )),
            ReportCard(state: 'past', reportingList: pastReportingList),
          ],
        ),
      ),
    ));
  }
}
