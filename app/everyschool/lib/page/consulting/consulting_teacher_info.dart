import 'package:flutter/material.dart';

class ConsultingTeacherInfo extends StatefulWidget {
  const ConsultingTeacherInfo(
      {super.key, this.teacherInfo, this.consultSchedule});
  final teacherInfo;
  final consultSchedule;

  @override
  State<ConsultingTeacherInfo> createState() => _ConsultingTeacherInfoState();
}

class _ConsultingTeacherInfoState extends State<ConsultingTeacherInfo> {
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
          child: Text('선택 : ${widget.teacherInfo['name']} 선생님'),
        ),
        Container(
            width: MediaQuery.of(context).size.width,
            margin: EdgeInsets.fromLTRB(0, 10, 0, 0),
            padding: EdgeInsets.fromLTRB(8, 10, 8, 10),
            color: Color(0xffF4F6FD),
            child: Text('📢 ${widget.consultSchedule['description']}')),
      ],
    );
  }
}
