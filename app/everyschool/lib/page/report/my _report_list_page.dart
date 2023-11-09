import 'package:everyschool/api/report_api.dart';
import 'package:everyschool/page/report/report_card.dart';
import 'package:everyschool/page/report/report_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class ReportListPage extends StatefulWidget {
  const ReportListPage({super.key});

  @override
  State<ReportListPage> createState() => _ReportListPageState();
}

class _ReportListPageState extends State<ReportListPage> {
  final storage = FlutterSecureStorage();

  getuserType() async {
    var userType = await storage.read(key: 'usertype') ?? "";
    return int.parse(userType);
  }

  getList() async {
    final myInfo = context.read<UserStore>().userInfo;
    final year = context.read<UserStore>().year;

    var response =
        await ReportApi().getReportList(year, myInfo['school']['schoolId']);
    print(response);
  }

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
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
                appBar: snapshot.data == 1001
                    ? AppBar(
                        backgroundColor: Colors.grey[50],
                        title: Text(
                          '나의 신고내역',
                          style: TextStyle(
                            color: Colors.black,
                            fontWeight: FontWeight.w700,
                          ),
                        ),
                        centerTitle: true,
                        elevation: 0,
                        actions: [
                          Align(
                            child: GestureDetector(
                              onTap: () {
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) =>
                                            const ReportPage()));
                              },
                              child: Container(
                                padding: EdgeInsets.fromLTRB(5, 5, 10, 5),
                                decoration: BoxDecoration(
                                  border: Border.all(
                                      color: Color(0xffbababa), width: 1.0),
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(20.0)),
                                ),
                                margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                child: const Row(
                                  children: [
                                    Icon(Icons.error_outline,
                                        color: Colors.red),
                                    SizedBox(width: 5),
                                    Text('신고하기',
                                        style: TextStyle(
                                          color: Colors.black,
                                          fontSize: 16,
                                          fontWeight: FontWeight.w700,
                                        )),
                                  ],
                                ),
                              ),
                            ),
                          ),
                        ],
                      )
                    : null,
                body: SingleChildScrollView(
                  child: Container(
                    color: Color(0xffF5F5F5),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        if (snapshot.data == 1001)
                          Container(
                            margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                            child: Text(
                              '처리 대기 중',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w600),
                            ),
                          ),
                        if (snapshot.data == 1003)
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Container(
                                margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                                child: Text(
                                  '처리 대기 중',
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w600),
                                ),
                              ),
                              GestureDetector(
                                onTap: () {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) =>
                                              const ReportPage()));
                                },
                                child: Container(
                                  padding: EdgeInsets.fromLTRB(5, 5, 10, 5),
                                  decoration: BoxDecoration(
                                    border: Border.all(
                                        color: Color(0xffbababa), width: 1.0),
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(20.0)),
                                  ),
                                  margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                  child: const Row(
                                    children: [
                                      Icon(Icons.error_outline,
                                          color: Colors.red),
                                      SizedBox(width: 5),
                                      Text('신고하기',
                                          style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 16,
                                            fontWeight: FontWeight.w700,
                                          )),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ReportCard(
                            state: 'waiting',
                            reportingList: waitingReportingList),
                        Container(
                            margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                            child: Text(
                              '이전 신고 내역',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w600),
                            )),
                        ReportCard(
                            state: 'past', reportingList: pastReportingList),
                      ],
                    ),
                  ),
                ));
          } else if (snapshot.hasError) {
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'Error: ${snapshot.error}',
                style: TextStyle(fontSize: 15),
              ),
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
