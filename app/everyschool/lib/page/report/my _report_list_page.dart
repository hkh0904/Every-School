import 'package:everyschool/api/report_api.dart';
import 'package:everyschool/page/report/report_card.dart';
import 'package:everyschool/page/report/report_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class ReportListPage extends StatefulWidget {
  const ReportListPage({super.key});

  @override
  State<ReportListPage> createState() => _ReportListPageState();
}

class _ReportListPageState extends State<ReportListPage> {
  final storage = FlutterSecureStorage();

  reportList() async {
    var userType = await storage.read(key: 'usertype') ?? "";

    final myInfo = context.read<UserStore>().userInfo;
    final year = context.read<UserStore>().year;

    var repList = await ReportApi()
        .getReportList(year, myInfo['school']['schoolId'], 7001);

    var ingList = await ReportApi()
        .getReportList(year, myInfo['school']['schoolId'], 7002);

    var completeList = await ReportApi()
        .getReportList(year, myInfo['school']['schoolId'], 7003);

    var allList = repList['content'] + ingList['content'];

    var reportInfo = {
      "userType": int.parse(userType),
      "repList": allList,
      "completeList": completeList['content']
    };
    print(reportInfo);
    return reportInfo;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: reportList(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
                appBar: snapshot.data['userType'] == 1001
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
                        if (snapshot.data['userType'] == 1001)
                          Container(
                            margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                            child: Text(
                              '처리 대기 중',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w600),
                            ),
                          ),
                        if (snapshot.data['userType'] == 1003)
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
                            reportingList: snapshot.data['repList']),
                        Container(
                            margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                            child: Text(
                              '이전 신고 내역',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w600),
                            )),
                        ReportCard(
                            state: 'past',
                            reportingList: snapshot.data['completeList']),
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
