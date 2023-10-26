import 'package:everyschool/page/consulting/consulting_teacher_info.dart';
import 'package:everyschool/page/consulting/select_date.dart';
import 'package:flutter/material.dart';

class ConsultingReservation extends StatefulWidget {
  const ConsultingReservation({super.key});

  @override
  State<ConsultingReservation> createState() => _ConsultingReservationState();
}

class _ConsultingReservationState extends State<ConsultingReservation> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        leading: IconButton(
            icon: const Icon(Icons.menu, color: Colors.black),
            visualDensity:
                const VisualDensity(horizontal: -4.0, vertical: -4.0),
            padding: const EdgeInsets.all(0), // 패딩을 조절합니다.
            alignment: Alignment.center, // 아이콘을 가운데 정렬합니다.
            splashRadius: 24.0, // 클릭 시 스플래시 효과의 반지름을 조절합니다.
            onPressed: () {}),
        elevation: 0,
        title: Text(
          '상담 신청',
          style: TextStyle(color: Colors.black, fontWeight: FontWeight.w700),
        ),
        centerTitle: true,
        actions: [
          IconButton(
              icon: const Icon(Icons.notifications_none, color: Colors.black),
              visualDensity:
                  const VisualDensity(horizontal: -4.0, vertical: -4.0),
              padding: const EdgeInsets.all(0), // 패딩을 조절합니다.
              alignment: Alignment.center, // 아이콘을 가운데 정렬합니다.
              splashRadius: 24.0, // 클릭 시 스플래시 효과의 반지름을 조절합니다.
              onPressed: () {}),
          IconButton(
              icon: const Icon(Icons.settings, color: Colors.black),
              visualDensity:
                  const VisualDensity(horizontal: -2.0, vertical: -4.0),
              padding: const EdgeInsets.all(0), // 패딩을 조절합니다.
              alignment: Alignment.center, // 아이콘을 가운데 정렬합니다.
              splashRadius: 24.0, // 클릭 시 스플래시 효과의 반지름을 조절합니다.
              onPressed: () {})
        ],
      ),
      body: Padding(
        padding: EdgeInsets.fromLTRB(30, 20, 30, 20),
        child: Column(children: [
          ConsultingTeacherInfo(),
          SelectDate(),
        ]),
      ),
    );
  }
}
