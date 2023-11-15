import 'package:assets_audio_player/assets_audio_player.dart';
import 'package:everyschool/page/home/community_new.dart';
import 'package:everyschool/page/home/menu_buttons.dart';
import 'package:everyschool/page/home/school_noti.dart';
import 'package:everyschool/page/home/user_info.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class HomeBody extends StatefulWidget {
  const HomeBody({super.key});

  @override
  State<HomeBody> createState() => _HomeBodyState();
}

class _HomeBodyState extends State<HomeBody> {
  void logout() {
    Navigator.pushAndRemoveUntil(
        context,
        MaterialPageRoute(builder: (context) => const LoginPage()),
        (Route<dynamic> route) => false);
  }

  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        SliverAppBar(
          backgroundColor: Colors.grey[50],
          elevation: 0,
          centerTitle: true,
          title: Image.asset('assets/images/home/title.png', height: 50),
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
              height: 85,
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
