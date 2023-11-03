import 'Package:flutter/material.dart';
import 'package:everyschool/page/consulting/consulting_list_parent.dart';
import 'package:everyschool/page/report_consulting/consulting_list_teacher.dart';

class ConsultingListPage extends StatefulWidget {
  const ConsultingListPage({super.key});

  @override
  State<ConsultingListPage> createState() => _ConsultingListPageState();
}

class _ConsultingListPageState extends State<ConsultingListPage> {
  var user_num = 1;

  @override
  Widget build(BuildContext context) {
    return user_num == 1 ? ConsultingListParent() : ConsultingListTeacher();
  }
}
