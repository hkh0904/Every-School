import 'package:everyschool/page/report_consulting/consulting_list_teacher.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/page/report_consulting/teacher_report_get_page.dart';
import 'package:flutter/material.dart';

class ReportConsultingPage extends StatefulWidget {
  const ReportConsultingPage({super.key});

  @override
  State<ReportConsultingPage> createState() => _ReportConsultingPageState();
}

class _ReportConsultingPageState extends State<ReportConsultingPage>
    with TickerProviderStateMixin {
  late final TabController _tabController;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 3, vsync: this);
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: NestedScrollView(
          headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
            return [
              SliverAppBar(
                title: Text(
                  '신고 및 상담',
                  style: TextStyle(
                      fontSize: 21,
                      fontWeight: FontWeight.bold,
                      color: Colors.black),
                ),
                backgroundColor: Colors.grey[50],
                bottom: TabBar(
                  controller: _tabController,
                  indicatorColor: Colors.black,
                  tabs: const <Widget>[
                    Tab(
                        child: Text(
                      '상담 내역',
                      style: TextStyle(color: Colors.black, fontSize: 16),
                    )),
                    Tab(
                        child: Text(
                      '나의 신고 내역',
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 16,
                      ),
                    )),
                    Tab(
                        child: Text(
                      '상담 내역',
                      style: TextStyle(
                        color: Colors.black,
                        fontSize: 16,
                      ),
                    )),
                  ],
                ),
                pinned: false,
              ),
            ];
          },
          body: TabBarView(
            controller: _tabController,
            children: const <Widget>[
              ConsultingListTeacher(),
              ReportListPage(),
              TeacherReportGetPage(),
            ],
          ),
        ),
      ),
    );
  }
}
