import 'package:everyschool/page/login/login_page.dart';
import 'package:everyschool/page/mypage/mypage_personal_menu.dart';
import 'package:everyschool/page/mypage/mypage_userinfo.dart';
import 'package:everyschool/page/mypage/mypage_usermenu.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MyPage extends StatefulWidget {
  const MyPage({super.key});

  @override
  State<MyPage> createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> {
  void logout() {
    Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(builder: (context) => const LoginPage()),
        (Route<dynamic> route) => false);
  }

  final storage = FlutterSecureStorage();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            MypageUserInfo(),
            MypageUsermenu(),
            MypagePersonalMenu(),
            GestureDetector(
              onTap: () async {
                await storage.delete(key: 'token');
                await storage.delete(key: 'userkey');
                await storage.delete(key: 'usertype');
                logout();
              },
              child: Text(
                '로그아웃',
                style: TextStyle(color: Color(0xff7B7B7B)),
              ),
            )
          ],
        ),
      ),
    );
  }
}
