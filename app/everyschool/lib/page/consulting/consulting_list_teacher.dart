import 'Package:flutter/material.dart';
import 'package:everyschool/page/consulting/consulting_card.dart';

class ConsultingListTeacher extends StatefulWidget {
  const ConsultingListTeacher({super.key});

  @override
  State<ConsultingListTeacher> createState() => _ConsultingListTeacherState();
}

class _ConsultingListTeacherState extends State<ConsultingListTeacher> {
  var upcomingConsulting = [
    {
      'type': '방문 상담',
      'dateTime': '2023.09.12 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유'
    },
    {
      'type': '방문 상담',
      'dateTime': '2023.09.11 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '졸라귀찮다'
    },
    {
      'type': '전화 상담',
      'dateTime': '2023.09.10 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '제발살려줘'
    },
  ];

  var pastConsulting = [
    {
      'type': '방문 상담',
      'dateTime': '2023.09.12 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유어쩌고저쩌고상담사유'
    },
    {
      'type': '방문 상담',
      'dateTime': '2023.09.11 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '졸라귀찮다'
    },
    {
      'type': '전화 상담',
      'dateTime': '2023.09.10 14:00',
      'consultant': '1학년 3반 OOO선생님',
      'applicant': '1학년 3반 강OO(모) 김OO',
      'detail': '제발살려줘'
    },
  ];

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
      body: SingleChildScrollView(
        child: Container(
          color: Color(0xffF5F5F5),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                margin: EdgeInsets.fromLTRB(30, 25, 30, 10),
                child: Text(
                  '예정된 상담',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
                ),
              ),
              ConsultingCard(
                  consultingList: upcomingConsulting, state: 'upcoming'),
              Container(
                margin: EdgeInsets.fromLTRB(30, 10, 30, 10),
                child: Text('지난 상담',
                    style:
                        TextStyle(fontSize: 18, fontWeight: FontWeight.w600)),
              ),
              ConsultingCard(consultingList: pastConsulting, state: 'past')
            ],
          ),
        ),
      ),
    );
  }
}
