import 'Package:flutter/material.dart';

class ConsultingListTeacher extends StatefulWidget {
  const ConsultingListTeacher({super.key});

  @override
  State<ConsultingListTeacher> createState() => _ConsultingListTeacherState();
}

class _ConsultingListTeacherState extends State<ConsultingListTeacher> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('상담 내역'),
      ),
    );
  }
}
