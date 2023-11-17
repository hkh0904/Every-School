import 'package:everyschool/api/report_api.dart';
import 'package:everyschool/page/report/report_card.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class TeacherReportGetPage extends StatefulWidget {
  const TeacherReportGetPage({super.key});

  @override
  State<TeacherReportGetPage> createState() => _TeacherReportGetPageState();
}

class _TeacherReportGetPageState extends State<TeacherReportGetPage> {
  final storage = FlutterSecureStorage();

  getList() async {
    var userType = await storage.read(key: 'usertype') ?? "";

    final myInfo = context.read<UserStore>().userInfo;
    final year = context.read<UserStore>().year;

    var waitingList = await ReportApi()
        .teacherGetReportList(year, myInfo['school']['schoolId'], 7001);
    var ingList = await ReportApi()
        .teacherGetReportList(year, myInfo['school']['schoolId'], 7002);
    var pastList = await ReportApi()
        .teacherGetReportList(year, myInfo['school']['schoolId'], 7003);

    var allList = ingList['content'] + pastList['content'];

    var reportInfo = {"repList": waitingList, "completeList": allList};

    return reportInfo;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getList(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
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
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w600),
                        )),
                    Container(
                        margin: EdgeInsets.fromLTRB(30, 5, 30, 15),
                        padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
                        color: Color(0xffF4F6FD),
                        child: Text(
                          '📍 신고내역 처리는 관리자 페이지에서 가능하며, 어플에서는 조회만 가능합니다.',
                          style: TextStyle(fontSize: 15),
                        )),
                    ReportCard(
                        state: 'waiting',
                        reportingList: snapshot.data['repList']['content']),
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
