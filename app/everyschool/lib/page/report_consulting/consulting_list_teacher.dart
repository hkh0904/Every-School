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
              Container(
                  margin: EdgeInsets.fromLTRB(30, 5, 30, 15),
                  padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
                  color: Color(0xffF4F6FD),
                  child: Text(
                    '📍 상담내역 처리는 관리자 페이지에서 가능하며, 어플에서는 조회만 가능합니다.',
                    style: TextStyle(fontSize: 15),
                  )),
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
