import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MenuButtons extends StatefulWidget {
  const MenuButtons({Key? key});

  @override
  State<MenuButtons> createState() => _MenuButtonsState();
}

class _MenuButtonsState extends State<MenuButtons> {
  final storage = FlutterSecureStorage();

  getuserType() async {
    final storage = FlutterSecureStorage();
    var userType = await storage.read(key: 'usertype') ?? "";
    return int.parse(userType);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            List<Widget> columns = [
              Column(
                children: [
                  GestureDetector(
                    onTap: () {
                      if (snapshot.data == 1003) {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    ReportConsultingPage(index: 1)));
                      } else if (snapshot.data == 1001) {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => ReportListPage()));
                      }
                    },
                    child: Container(
                      height: 55,
                      width: 55,
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(color: Color(0xffd9d9d9))),
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Image.asset('assets/images/home/report.png'),
                      ),
                    ),
                  ),
                  Text(
                    '신고내역',
                    style: TextStyle(fontWeight: FontWeight.w600),
                  )
                ],
              ),
              Column(
                children: [
                  GestureDetector(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  PostlistPage(pageTitle: '가정통신문')));
                    },
                    child: Container(
                      height: 55,
                      width: 55,
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(color: Color(0xffd9d9d9))),
                      child: Padding(
                        padding: const EdgeInsets.all(10.0),
                        child: Image.asset('assets/images/home/noti.png'),
                      ),
                    ),
                  ),
                  Text(
                    '가정통신문',
                    style: TextStyle(fontWeight: FontWeight.w600),
                  )
                ],
              ),
              Column(
                children: [
                  Container(
                    height: 55,
                    width: 55,
                    margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(5),
                        border: Border.all(color: Color(0xffd9d9d9))),
                    child: Padding(
                      padding: const EdgeInsets.all(9.0),
                      child: Image.asset('assets/images/home/bill.png'),
                    ),
                  ),
                  Text(
                    '고지서',
                    style: TextStyle(fontWeight: FontWeight.w600),
                  )
                ],
              )
            ];

            if (snapshot.data == 1001) {
              columns.insert(
                  0,
                  Column(
                    children: [
                      Container(
                          height: 55,
                          width: 55,
                          margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(5),
                              border: Border.all(color: Color(0xffd9d9d9))),
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(5, 0, 5, 0),
                            child: Image.asset('assets/images/home/food.png'),
                          )),
                      Text(
                        '오늘의 급식',
                        style: TextStyle(fontWeight: FontWeight.w600),
                      )
                    ],
                  ));
            } else if (snapshot.data == 1002) {
              columns.insert(
                  0,
                  Column(
                    children: [
                      GestureDetector(
                        onTap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      ConsultingReservation()));
                        },
                        child: Container(
                            height: 55,
                            width: 55,
                            margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(5),
                                border: Border.all(color: Color(0xffd9d9d9))),
                            child: Padding(
                              padding: const EdgeInsets.all(10.0),
                              child:
                                  Image.asset('assets/images/home/csltapp.png'),
                            )),
                      ),
                      Text(
                        '상담신청',
                        style: TextStyle(fontWeight: FontWeight.w600),
                      )
                    ],
                  ));
            } else {
              columns.insert(
                  0,
                  Column(
                    children: [
                      GestureDetector(
                        onTap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      ReportConsultingPage(index: 0)));
                        },
                        child: Container(
                          height: 55,
                          width: 55,
                          margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                          decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(5),
                              border: Border.all(color: Color(0xffd9d9d9))),
                          child: Padding(
                            padding: const EdgeInsets.all(9.0),
                            child:
                                Image.asset('assets/images/home/csltlist2.png'),
                          ),
                        ),
                      ),
                      Text(
                        '상담내역',
                        style: TextStyle(fontWeight: FontWeight.w600),
                      )
                    ],
                  ));
            }

            return Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: columns,
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
