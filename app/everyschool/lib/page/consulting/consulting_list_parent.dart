import 'package:everyschool/page/consulting/consulting_card_detail.dart';
import 'package:flutter/material.dart';

class ConsultingListParent extends StatefulWidget {
  const ConsultingListParent({Key? key});

  @override
  State<ConsultingListParent> createState() => _ConsultingListParentState();
}

class _ConsultingListParentState extends State<ConsultingListParent> {
  var consultingList = [
    {
      'type': '방문 상담',
      'dateTime': '2023.09.12 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유',
      'state': '승인 대기중'
    },
    {
      'type': '방문 상담',
      'dateTime': '2023.09.11 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유',
      'state': '예약 완료'
    },
    {
      'type': '전화 상담',
      'dateTime': '2023.09.10 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유',
      'state': '상담 완료'
    },
  ];

  Color _getColorFromState(String state) {
    switch (state) {
      case '승인 대기중':
        return Color(0xff7E6CD9);
      case '예약 완료':
        return Color(0xff77B6FF);
      case '상담 완료':
        return Color(0xffFDCE01);
      default:
        return Colors.black;
    }
  }

  @override
  Widget build(BuildContext context) {
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
      ),
      body: Container(
        width: MediaQuery.of(context).size.width,
        color: Color(0xffF5F5F5),
        child: Container(
          margin: EdgeInsets.fromLTRB(30, 25, 30, 0),
          child: ListView.builder(
            // physics: NeverScrollableScrollPhysics(),
            itemCount: consultingList.length,
            itemBuilder: (context, index) {
              return GestureDetector(
                onTap: () {
                  ConsultingCardDetail(consultingList[index])
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
                            color: _getColorFromState(
                                consultingList[index]['state'] as String),
                            borderRadius: BorderRadius.only(
                                bottomLeft: Radius.circular(8),
                                topLeft: Radius.circular(8))),
                      ),
                      Expanded(
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Text(consultingList[index]['type'] as String,
                                    style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w700,
                                    )),
                                Text(consultingList[index]['consultant']
                                    as String),
                                Text(
                                    consultingList[index]['dateTime'] as String,
                                    style: TextStyle(color: Color(0xff999999))),
                              ],
                            ),
                            Container(
                                alignment: Alignment.center,
                                margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                width: 75,
                                height: 30,
                                decoration: BoxDecoration(
                                    color: _getColorFromState(
                                        consultingList[index]['state']
                                            as String),
                                    borderRadius: BorderRadius.circular(8)),
                                child: Text(
                                  consultingList[index]['state'] as String,
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
  }
}
