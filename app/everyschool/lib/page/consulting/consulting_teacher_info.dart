import 'package:flutter/material.dart';

class ConsultingTeacherInfo extends StatefulWidget {
  const ConsultingTeacherInfo({super.key});

  @override
  State<ConsultingTeacherInfo> createState() => _ConsultingTeacherInfoState();
}

class _ConsultingTeacherInfoState extends State<ConsultingTeacherInfo> {
  var teacherInfo = {
    'grade': 1,
    'class': 3,
    'name': '김나나',
    'msg': '상담 신청 시간은 평일 16:00 ~ 18:00 까지 가능합니다. 긴급 상담은 채팅을 보내주세요.'
  };

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          '선생님 정보',
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.w600),
        ),
        Container(
          margin: EdgeInsets.fromLTRB(0, 5, 0, 0),
          child: Text(
              '선택 : ${teacherInfo['grade']}학년 ${teacherInfo['class']}반 ${teacherInfo['name']}선생님'),
        ),
        Container(
            margin: EdgeInsets.fromLTRB(0, 10, 0, 0),
            padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
            color: Color(0xffF4F6FD),
            child: Text('📢 ${teacherInfo['msg']}')),
      ],
    );
  }
}
