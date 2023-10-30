import 'package:everyschool/page/home/community_new.dart';
import 'package:everyschool/page/home/menu_buttons.dart';
import 'package:everyschool/page/home/school_noti.dart';
import 'package:everyschool/page/home/user_info.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';

class HomeBody extends StatefulWidget {
  const HomeBody({super.key});

  @override
  State<HomeBody> createState() => _HomeBodyState();
}

class _HomeBodyState extends State<HomeBody> {
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        SliverAppBar(
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
                  Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const LoginPage()));
                })
          ],
          actionsIconTheme: const IconThemeData(
            color: Colors.black,
          ),
        ),
        SliverToBoxAdapter(
          child: Image.asset('assets/images/home/banner.png'),
        ),
        SliverToBoxAdapter(
          child: Container(
              margin: EdgeInsets.fromLTRB(20, 15, 20, 15), child: SchoolInfo()),
        ),
        SliverToBoxAdapter(
          child: Container(
              // height: 60,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 20),
              child: MenuButtons()),
        ),
        SliverToBoxAdapter(
          child: SchoolNoti(),
        ),
        SliverToBoxAdapter(
          child: CommunityNew(),
        )
      ],
    );
  }
}
