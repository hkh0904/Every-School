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
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => const LoginPage()));
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
