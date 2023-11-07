import 'package:everyschool/page/messenger/call/call_button.dart';
import 'package:everyschool/page/messenger/call/call_history.dart';

import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/chat/chat_list.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:flutter/material.dart';

class MessengerPage extends StatefulWidget {
  const MessengerPage({super.key});

  @override
  State<MessengerPage> createState() => _MessengerPageState();
}

class _MessengerPageState extends State<MessengerPage> {
  @override
  void initState() {
    // TODO: implement initState
  }

  final userId = 2;

  @override
  Widget build(BuildContext context) {
    return userId == 1 ? ManagerTapBar() : UserTapBar();
  }
}

class ManagerTapBar extends StatelessWidget {
  ManagerTapBar({super.key});

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        body: SafeArea(
          child: NestedScrollView(
            headerSliverBuilder:
                (BuildContext context, bool innerBoxIsScrolled) {
              return [
                SliverAppBar(
                  elevation: 8,
                  title: Text('메신저', style: TextStyle(color: Colors.black)),
                  backgroundColor: Colors.grey[50],
                  bottom: TabBar(
                    indicatorColor: Color(0xff15075F),
                    tabs: <Widget>[
                      Tab(
                          child: Text(
                        '채팅',
                        style: tapBarTextStyle,
                      )),
                      Tab(
                          child: Text(
                        '통화',
                        style: tapBarTextStyle,
                      )),
                      Tab(
                          child: Text(
                        '연락처',
                        style: tapBarTextStyle,
                      ))
                    ],
                  ),
                  pinned: false,
                ),
              ];
            },
            body: const TabBarView(
              children: [ChatList(), CallHistory(), CallHistory()],
            ),
          ),
        ),
      ),
    );
  }
}

class UserTapBar extends StatelessWidget {
  UserTapBar({super.key});

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        body: SafeArea(
          child: NestedScrollView(
            headerSliverBuilder:
                (BuildContext context, bool innerBoxIsScrolled) {
              return [
                SliverAppBar(
                  title: Text('메신저', style: TextStyle(color: Colors.black)),
                  backgroundColor: Colors.grey[50],
                  actions: [
                    Center(
                        child: Text('담임선생님',
                            style: TextStyle(
                                color: Colors.black,
                                fontWeight: FontWeight.bold))),
                    // CallButton(),
                    IconButton(
                        onPressed: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (BuildContext context) =>
                                      const ChatRoom()));
                        },
                        icon: Icon(Icons.message_sharp))
                  ],
                  actionsIconTheme: const IconThemeData(
                    color: Colors.black,
                  ),
                  bottom: TabBar(
                    indicatorColor: Color(0xff15075F),
                    tabs: <Widget>[
                      Tab(
                          child: Text(
                        '채팅',
                        style: tapBarTextStyle,
                      )),
                      Tab(
                          child: Text(
                        '통화',
                        style: tapBarTextStyle,
                      )),
                    ],
                  ),
                  pinned: false,
                ),
              ];
            },
            body: const TabBarView(
              children: [
                ChatList(),
                CallHistory(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
