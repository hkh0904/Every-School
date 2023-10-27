import 'Package:flutter/material.dart';

class ConsultingListParent extends StatefulWidget {
  const ConsultingListParent({super.key});

  @override
  State<ConsultingListParent> createState() => _ConsultingListParentState();
}

class _ConsultingListParentState extends State<ConsultingListParent> {
  var consultingList = [
    {
      'type': '진로 상담',
      'dateTime': '2023.09.12 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': '승인 대기중'
    },
    {
      'type': '진로 상담',
      'dateTime': '2023.09.11 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': '예약 완료'
    },
    {
      'type': '진로 상담',
      'dateTime': '2023.09.10 14:00',
      'name': '1학년 3반 OOO선생님',
      'state': '상담 완료'
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          title: Text('상담 내역',
              style: TextStyle(
                  color: Colors.black,
                  fontSize: 18,
                  fontWeight: FontWeight.w700)),
          centerTitle: true,
          elevation: 0,
        ),
        body: Container(
          width: MediaQuery.of(context).size.width,
          color: Color(0xffF5F5F5),
          child: Container(
            margin: EdgeInsets.fromLTRB(30, 0, 30, 0),
            child: ListView.builder(
                physics: NeverScrollableScrollPhysics(),
                itemCount: consultingList.length,
                itemBuilder: (context, index) {
                  return Container(
                    height: 100,
                    color: Colors.grey[50],
                    child: Row(children: [
                      Column(children: [
                        Text(consultingList[index]['type'] as String),
                        Text(consultingList[index]['name'] as String)
                      ]),
                    ]),
                  );
                }),
          ),
        ));
  }
}
