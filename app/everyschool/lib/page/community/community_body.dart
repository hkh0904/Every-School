import 'package:everyschool/page/community/community_menu.dart';
import 'package:everyschool/page/community/community_board.dart';
import 'package:everyschool/page/community/popular_post.dart';
import 'package:everyschool/page/community/hot_post.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';

class CommunityBody extends StatefulWidget {
  const CommunityBody({super.key});

  @override
  State<CommunityBody> createState() => _CommunityBodyState();
}

class _CommunityBodyState extends State<CommunityBody> {
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
          child: Container(
              margin: EdgeInsets.fromLTRB(20, 15, 20, 15),
              child: Text(
                '광주수완고등학교',
                style: TextStyle(fontSize: 22, fontWeight: FontWeight.w800),
              )),
        ),
        SliverToBoxAdapter(
          child: Container(
              // height: 60,
              margin: EdgeInsets.fromLTRB(0, 0, 0, 20),
              child: CommunityMenu()),
        ),
        SliverToBoxAdapter(
          child: CommunityBoard(),
        ),
        SliverToBoxAdapter(
          child: PopularPost(),
        ),
        SliverToBoxAdapter(
          child: HotPost(),
        ),
      ],
    );
  }
}
