import 'package:everyschool/page/home/home_body.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        elevation: 0,
        centerTitle: true,
        title: Image.asset('assets/images/home/title.png', height: 50),
        actions: [
          IconButton(
              icon: const Icon(Icons.notifications_none),
              visualDensity:
                  const VisualDensity(horizontal: -4.0, vertical: -4.0),
              padding: const EdgeInsets.all(0), // 패딩을 조절합니다.
              alignment: Alignment.center, // 아이콘을 가운데 정렬합니다.
              splashRadius: 24.0, // 클릭 시 스플래시 효과의 반지름을 조절합니다.
              onPressed: () {}),
          IconButton(
              icon: const Icon(Icons.settings),
              visualDensity:
                  const VisualDensity(horizontal: -2.0, vertical: -4.0),
              padding: const EdgeInsets.all(0), // 패딩을 조절합니다.
              alignment: Alignment.center, // 아이콘을 가운데 정렬합니다.
              splashRadius: 24.0, // 클릭 시 스플래시 효과의 반지름을 조절합니다.
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (context) => const LoginPage()));
              })
        ],
        actionsIconTheme: const IconThemeData(
          color: Colors.black,
        ),
      ),
      body: const HomeBody(),
    );
  }
}
