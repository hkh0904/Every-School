import 'package:everyschool/page/mypage/mypage_personal_menu.dart';
import 'package:everyschool/page/mypage/mypage_userinfo.dart';
import 'package:everyschool/page/mypage/mypage_usermenu.dart';
import 'package:flutter/material.dart';

class MyPage extends StatefulWidget {
  const MyPage({super.key});

  @override
  State<MyPage> createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: const Column(
          children: [
            MypageUserInfo(),
            MypageUsermenu(),
            MypagePersonalMenu(),
            Text(
              '로그아웃',
              style: TextStyle(color: Color(0xff7B7B7B)),
            )
          ],
        ),
      ),
    );
  }
}
