import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/call_button.dart';
import 'package:everyschool/page/messenger/call/call_history.dart';

import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/chat/chat_list.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:everyschool/page/messenger/chat/connect.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

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

class ManagerTapBar extends StatefulWidget {
  ManagerTapBar({super.key});

  @override
  State<ManagerTapBar> createState() => _ManagerTapBarState();
}

class _ManagerTapBarState extends State<ManagerTapBar> {
  final storage = FlutterSecureStorage();
  List<dynamic>? userConnect;
  List<dynamic> chatList = [];
  List roomIdList = [];
  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";
    print(token);
    final response = await MessengerApi().getChatList(token);
    final contact = await MessengerApi().getTeacherConnect(token);

    setState(() {
      chatList = response;
      userConnect = contact;
    });
    response.forEach((item) {
      if (item.containsKey("roomId")) {
        roomIdList.add(item["roomId"]);
      }
    });

    return response;
  }

  Future<dynamic>? chatListFuture;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    chatListFuture = _getChatList();
  }

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
            body: TabBarView(
              children: [
                FutureBuilder(
                    future: chatListFuture,
                    builder: (BuildContext context, AsyncSnapshot snapshot) {
                      if (snapshot.hasData) {
                        return ChatList(chatList: chatList);
                      } else if (snapshot.hasError) {
                        return Text(
                          'Error: ${snapshot.error}',
                          style: TextStyle(fontSize: 15),
                        );
                      } else {
                        return Container(
                          height: 800,
                        );
                      }
                    }),
                CallHistory(),
                Connect(
                  userConnect: userConnect,
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class UserTapBar extends StatefulWidget {
  UserTapBar({super.key});

  @override
  State<UserTapBar> createState() => _UserTapBarState();
}

class _UserTapBarState extends State<UserTapBar> {
  final storage = FlutterSecureStorage();

  Map<String, String>? teacherConnect;
  List<dynamic> chatList = [];
  List roomIdList = [];
  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";
    final response = await MessengerApi().getChatList(token);
    final contact = await MessengerApi().getTeacherConnect(token);
    setState(() {
      chatList = response;
      teacherConnect = contact;
    });
    response.forEach((item) {
      if (item.containsKey("roomId")) {
        roomIdList.add(item["roomId"]);
      }
    });

    return response;
  }

  Future<dynamic>? chatListFuture;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    chatListFuture = _getChatList();
  }

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
                    CallButton(),
                    IconButton(
                        onPressed: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (BuildContext context) => ChatRoom(
                                        chatRoomInfo: chatList,
                                        roomInfo: null,
                                      )));
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
            body: TabBarView(
              children: [
                FutureBuilder(
                    future: chatListFuture,
                    builder: (BuildContext context, AsyncSnapshot snapshot) {
                      if (snapshot.hasData) {
                        return ChatList(chatList: chatList);
                      } else if (snapshot.hasError) {
                        return Text(
                          'Error: ${snapshot.error}',
                          style: TextStyle(fontSize: 15),
                        );
                      } else {
                        return Container(
                          height: 800,
                        );
                      }
                    }),
                CallHistory(),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
