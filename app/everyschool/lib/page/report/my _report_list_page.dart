import 'package:everyschool/page/report/report_card.dart';
import 'package:flutter/material.dart';

class ReportListPage extends StatefulWidget {
  const ReportListPage({super.key});

  @override
  State<ReportListPage> createState() => _ReportListPageState();
}

class _ReportListPageState extends State<ReportListPage> {
  var pastReportingList = [
    {'type': '학교폭력', 'dateTime': '2023.09.12 14:00', 'state': 'waiting'},
    {'type': '학교폭력', 'dateTime': '2023.09.11 14:00', 'state': 'waiting'},
    {'type': '도난신고', 'dateTime': '2023.09.10 14:00', 'state': 'waiting'},
  ];

  var waitingReportingList = [
    {'type': '학교폭력', 'dateTime': '2023.09.12 14:00', 'state': 'past'},
    {'type': '학교폭력', 'dateTime': '2023.09.11 14:00', 'state': 'past'},
    {'type': '도난신고', 'dateTime': '2023.09.10 14:00', 'state': 'past'},
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          title: Text(
            '나의 신고내역',
            style: TextStyle(
              color: Colors.black,
              fontSize: 18,
              fontWeight: FontWeight.w700,
            ),
          ),
          centerTitle: true,
          elevation: 0,
        ),
        body: SingleChildScrollView(
          child: Container(
            color: Color(0xffF5F5F5),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                    margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                    child: Text(
                      '처리 대기 중',
                      style:
                          TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
                    )),
                ReportCard(
                    state: 'waiting', reportingList: waitingReportingList),
                Container(
                    margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                    child: Text(
                      '이전 신고 내역',
                      style:
                          TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
                    )),
                ReportCard(state: 'past', reportingList: pastReportingList),
              ],
            ),
          ),
        ));
  }
}
