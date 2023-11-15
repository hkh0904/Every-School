import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
import 'package:everyschool/page/community/school_schedule.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:everyschool/store/user_store.dart';
import 'dart:convert';

class CommunityMenu extends StatefulWidget {
  const CommunityMenu({super.key});

  @override
  State<CommunityMenu> createState() => _CommunityMenuState();
}

class _CommunityMenuState extends State<CommunityMenu> {
  final UserApi userApi = UserApi();
  Map<String, dynamic> schoolData = {};

  @override
  void initState() {
    super.initState();
    _loadSchoolData();
  }

  Future<void> _loadSchoolData() async {
    final userType = context.read<UserStore>().userInfo["userType"];
    late final schoolId;
    if (userType == 1002) {
      final storage = FlutterSecureStorage();
      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);
      schoolId = selectDescendant["school"]["schoolId"];
    } else {
      schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
    }
    var response;
    try {
      response = await userApi.getSchoolData(schoolId);
      setState(() {
        schoolData = response;
      });
      print(response);
    } catch (e) {
      print('커뮤니티 메뉴 에러 : $e');
    }
  }

  Future<void> _launchURL(String urlString) async {
    final Uri url = Uri.parse(urlString);
    if (!await launchUrl(url)) {
      throw Exception('Could not launch $url');
    }
  }

  @override
  Widget build(BuildContext context) {
    List<Widget> columns = [
      Column(
        children: [
          GestureDetector(
            onTap: () {
              if (schoolData['url'] == null) {
                showDialog(
                  barrierDismissible: true,
                  context: context,
                  builder: (context) {
                    return AlertDialog(
                      contentPadding: EdgeInsets.zero,
                      content: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          SizedBox(height: 40),
                          Center(
                            child: Text(
                              '학교 사이트가 존재하지 않습니다',
                              style: TextStyle(
                                  fontWeight: FontWeight.w700, fontSize: 18),
                            ),
                          ),
                          SizedBox(height: 40),
                          GestureDetector(
                            onTap: () {
                              Navigator.of(context).pop(); // 다이얼로그 닫기
                            },
                            child: Container(
                              height: 50,
                              color: Color(0xff15075f),
                              child: Center(
                                child: Text(
                                  "확인",
                                  style: TextStyle(
                                      color: Colors.white,
                                      fontWeight: FontWeight.w700,
                                      fontSize: 18),
                                ),
                              ),
                            ),
                          ),
                        ],
                      ),
                    );
                  },
                );
              } else {
                _launchURL(schoolData['url']);
              }
            },
            child: Container(
              height: 55,
              width: 55,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
              decoration: BoxDecoration(
                color: Color(0xffF1F1F1),
                borderRadius: BorderRadius.circular(100),
              ),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.asset('assets/images/community/school.png'),
              ),
            ),
          ),
          Text(
            '학교 홈',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) =>
                        SchoolSchedule()), // SchoolSchedule은 이동하려는 페이지의 클래스 이름이어야 합니다.
              );
            },
            child: Container(
              height: 55,
              width: 55,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
              decoration: BoxDecoration(
                color: Color(0xFFF1F1F1),
                borderRadius: BorderRadius.circular(100),
              ),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.asset('assets/images/community/calendar.png'),
              ),
            ),
          ),
          Text(
            '학사일정',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => PostlistPage(
                        pageTitle:
                            '학사 공지')), // SchoolSchedule은 이동하려는 페이지의 클래스 이름이어야 합니다.
              );
            },
            child: Container(
              height: 55,
              width: 55,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
              decoration: BoxDecoration(
                color: Color(0xffF1F1F1),
                borderRadius: BorderRadius.circular(100),
              ),
              child: Padding(
                padding: const EdgeInsets.all(8.0),
                child: Image.asset('assets/images/community/annouce.png'),
              ),
            ),
          ),
          Text(
            '학사공지',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => PostlistPage(
                        pageTitle:
                            '가정통신문')), // SchoolSchedule은 이동하려는 페이지의 클래스 이름이어야 합니다.
              );
            },
            child: Container(
              height: 55,
              width: 55,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
              decoration: BoxDecoration(
                color: Color(0xffF1F1F1),
                borderRadius: BorderRadius.circular(100),
              ),
              child: Padding(
                padding: const EdgeInsets.all(9.0),
                child: Image.asset('assets/images/community/task.png'),
              ),
            ),
          ),
          Text(
            '가정통신문',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      )
    ];
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: columns,
    );
  }
}
