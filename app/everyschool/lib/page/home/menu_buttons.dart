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
                  Container(
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
                  Text(
                    '신고내역',
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
                      padding: const EdgeInsets.all(10.0),
                      child: Image.asset('assets/images/home/noti.png'),
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
                      Container(
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
                      Container(
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
