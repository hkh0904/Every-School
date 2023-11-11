import 'dart:convert';

import 'package:everyschool/api/consulting_api.dart';
import 'package:everyschool/page/consulting/consulting_card_detail.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class ConsultingListParent extends StatefulWidget {
  const ConsultingListParent({Key? key});

  @override
  State<ConsultingListParent> createState() => _ConsultingListParentState();
}

class _ConsultingListParentState extends State<ConsultingListParent> {
  final storage = FlutterSecureStorage();

  consultingList() async {
    final descendantInfo = await storage.read(key: 'descendant') ?? "";
    var selectDescendant = jsonDecode(descendantInfo);

    final year = context.read<UserStore>().year;

    var response = await ConsultingApi()
        .getConsultingList(selectDescendant['school']['schoolId'], year);
    print(response);
    return response;
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    consultingList();
  }

  Color _getColorFromState(String state) {
    switch (state) {
      case '승인 대기중':
        return Color(0xff7E6CD9);
      case '승인 완료':
        return Color(0xff77B6FF);
      case '상담 완료':
        return Color(0xffFDCE01);
      default:
        return Colors.black;
    }
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: consultingList(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
              appBar: AppBar(
                backgroundColor: Colors.grey[50],
                title: Text(
                  '상담 내역',
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 18,
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
                                    const ConsultingReservation()));
                      },
                      child: Container(
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 5),
                        decoration: BoxDecoration(
                          border:
                              Border.all(color: Color(0xffbababa), width: 1.0),
                          borderRadius: BorderRadius.all(Radius.circular(20.0)),
                        ),
                        margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                        child: const Row(
                          children: [
                            Icon(Icons.calendar_month_rounded,
                                color: Colors.red),
                            SizedBox(width: 5),
                            Text('상담 신청',
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
              ),
              body: Container(
                width: MediaQuery.of(context).size.width,
                color: Color(0xffF5F5F5),
                child: Container(
                  margin: EdgeInsets.fromLTRB(30, 25, 30, 0),
                  child: ListView.builder(
                    // physics: NeverScrollableScrollPhysics(),
                    itemCount: snapshot.data.length,
                    itemBuilder: (context, index) {
                      return GestureDetector(
                        onTap: () async {
                          final descendantInfo =
                              await storage.read(key: 'descendant') ?? "";
                          var selectDescendant = jsonDecode(descendantInfo);

                          final year = context.read<UserStore>().year;
                          var consultingDetail = await ConsultingApi()
                              .getConsultDetail(
                                  selectDescendant['school']['schoolId'],
                                  year,
                                  snapshot.data[index]['consultId']);
                          print(consultingDetail);
                          ConsultingCardDetail(consultingDetail)
                              .cardDetail(context);
                        },
                        child: Container(
                          height: 100,
                          margin: EdgeInsets.fromLTRB(0, 0, 0, 25),
                          decoration: BoxDecoration(
                              color: Colors.grey[50],
                              borderRadius: BorderRadius.circular(8)),
                          child: Row(
                            children: [
                              Container(
                                margin: EdgeInsets.fromLTRB(0, 0, 15, 0),
                                width: 5,
                                decoration: BoxDecoration(
                                    color: _getColorFromState(snapshot
                                        .data[index]['status'] as String),
                                    borderRadius: BorderRadius.only(
                                        bottomLeft: Radius.circular(8),
                                        topLeft: Radius.circular(8))),
                              ),
                              Expanded(
                                child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        Text(
                                            snapshot.data[index]['type']
                                                as String,
                                            style: TextStyle(
                                              fontSize: 18,
                                              fontWeight: FontWeight.w700,
                                            )),
                                        // Text(snapshot.data[index]['info'] as String),
                                        Text(
                                            snapshot.data[index]
                                                ['consultDateTime'] as String,
                                            style: TextStyle(
                                                color: Color(0xff999999))),
                                      ],
                                    ),
                                    Container(
                                        alignment: Alignment.center,
                                        margin:
                                            EdgeInsets.fromLTRB(0, 0, 20, 0),
                                        width: 75,
                                        height: 30,
                                        decoration: BoxDecoration(
                                            color: _getColorFromState(
                                                snapshot.data[index]['status']
                                                    as String),
                                            borderRadius:
                                                BorderRadius.circular(8)),
                                        child: Text(
                                          snapshot.data[index]['status']
                                              as String,
                                          style: TextStyle(
                                              color: Colors.grey[50],
                                              fontWeight: FontWeight.w600),
                                        )),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
                ),
              ),
            );
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
