import 'Package:flutter/material.dart';
import 'package:everyschool/api/consulting_api.dart';
import 'package:everyschool/page/consulting/consulting_card.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:provider/provider.dart';

class ConsultingListTeacher extends StatefulWidget {
  const ConsultingListTeacher({super.key});

  @override
  State<ConsultingListTeacher> createState() => _ConsultingListTeacherState();
}

class _ConsultingListTeacherState extends State<ConsultingListTeacher> {
  consultingList() async {
    var myInfo = context.read<UserStore>().userInfo;
    final year = context.read<UserStore>().year;

    var response = await ConsultingApi()
        .getConsultingList(myInfo['school']['schoolId'], year);

    var upcomingConsulting = [];
    var pastConsulting = [];

    for (var item in response) {
      if (item["status"] == "승인 대기중" || item["status"] == "승인 완료") {
        upcomingConsulting.add(item);
      } else {
        pastConsulting.add(item);
      }
    }

    if (response == 0) {
      return 0;
    } else if (response.length == 0) {
      return 1;
    } else {
      return {
        "upcomingConsulting": upcomingConsulting,
        "pastConsulting": pastConsulting
      };
    }
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: consultingList(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            if (snapshot.data != 0 &&
                snapshot.data != 1 &&
                snapshot.data.length > 0) {
              return Scaffold(
                body: SingleChildScrollView(
                  child: Container(
                    constraints: BoxConstraints(
                        minHeight: MediaQuery.of(context).size.height),
                    color: Color(0xffF5F5F5),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Container(
                          margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                          child: Text(
                            '예정된 상담',
                            style: TextStyle(
                                fontSize: 18, fontWeight: FontWeight.w600),
                          ),
                        ),
                        Container(
                            margin: EdgeInsets.fromLTRB(30, 5, 30, 15),
                            padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
                            color: Color(0xffF4F6FD),
                            child: Text(
                              '📍 상담내역 처리는 관리자 페이지에서 가능하며, 어플에서는 조회만 가능합니다.',
                              style: TextStyle(fontSize: 15),
                            )),
                        ConsultingCard(
                            consultingList: snapshot.data['upcomingConsulting'],
                            state: 'upcoming'),
                        Container(
                          margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                          child: Text('지난 상담',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.w600)),
                        ),
                        ConsultingCard(
                            consultingList: snapshot.data['pastConsulting'],
                            state: 'past')
                      ],
                    ),
                  ),
                ),
              );
            } else if (snapshot.data == 0) {
              return Text('상담 내역을 불러오던 중 오류가 발생하였습니다.');
            } else {
              return Scaffold(
                  body: Center(
                      child: Text(
                '상담 기록이 없습니다.',
                style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
              )));
            }
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
