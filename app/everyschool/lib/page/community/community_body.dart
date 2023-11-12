import 'package:everyschool/page/community/community_menu.dart';
import 'package:everyschool/page/community/community_board.dart';
import 'package:everyschool/page/community/popular_post.dart';
import 'package:everyschool/page/community/hot_post.dart';
import 'package:everyschool/page/community/home_notice_board.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:everyschool/store/user_store.dart';

class CommunityBody extends StatefulWidget {
  const CommunityBody({super.key});

  @override
  State<CommunityBody> createState() => _CommunityBodyState();
}

class _CommunityBodyState extends State<CommunityBody> {
  @override
  Widget build(BuildContext context) {
    return Consumer<UserStore>(
      builder: (context, userStore, child) {
        final userType = userStore.userInfo["userType"];
        late final schoolName;
        if (userType == 1002) {
          schoolName = userStore.userInfo["descendants"][0]["school"]["name"];
        } else {
          schoolName = userStore.userInfo["school"]["name"];
        }
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
                    padding: const EdgeInsets.all(0),
                    alignment: Alignment.center,
                    splashRadius: 24.0,
                    onPressed: () {}),
                IconButton(
                    icon: const Icon(Icons.settings),
                    visualDensity:
                        const VisualDensity(horizontal: -2.0, vertical: -4.0),
                    padding: const EdgeInsets.all(0),
                    alignment: Alignment.center,
                    splashRadius: 24.0,
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
                  schoolName ?? '학교 이름 없음', // null 체크
                  style: TextStyle(fontSize: 22, fontWeight: FontWeight.w800),
                ),
              ),
            ),
            SliverToBoxAdapter(
              child: Container(
                  // height: 60,
                  margin: EdgeInsets.fromLTRB(0, 0, 0, 20),
                  child: CommunityMenu()),
            ),
            if (userType == 1001) ...[
              SliverToBoxAdapter(
                child: CommunityBoard(),
              ),
              SliverToBoxAdapter(
                child: PopularPost(),
              ),
              SliverToBoxAdapter(
                child: HotPost(),
              ),
            ] else if (userType == 1002 || userType == 1003) ...[
              SliverToBoxAdapter(
                child: CommunityBoard(),
              ),
              SliverToBoxAdapter(
                child: HomeNoticeBoard(),
              ),
            ],
          ],
        );
      },
    );
  }
}
