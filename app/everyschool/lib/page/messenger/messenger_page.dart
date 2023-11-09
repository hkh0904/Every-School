import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/call_button.dart';
import 'package:everyschool/page/messenger/call/call_history.dart';

import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/chat/chat_list.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:everyschool/page/messenger/chat/connect.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class MessengerPage extends StatefulWidget {
  const MessengerPage({super.key});

  @override
  State<MessengerPage> createState() => _MessengerPageState();
}

class _MessengerPageState extends State<MessengerPage> {
  final storage = FlutterSecureStorage();
  int? userType;

  @override
  void initState() {
    // TODO: implement initState

    userType = context.read<UserStore>().userInfo['userType'];
    print(userType);
    // userType = 1003;
  }

  @override
  Widget build(BuildContext context) {
    return userType == 1003 ? ManagerTapBar() : UserTapBar();
  }
}

class ManagerTapBar extends StatefulWidget {
  ManagerTapBar({super.key});

  @override
  State<ManagerTapBar> createState() => _ManagerTapBarState();
}

class _ManagerTapBarState extends State<ManagerTapBar> {
  final storage = FlutterSecureStorage();

  List? userConnect;
  List? chatList;
  List roomIdList = [];
  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";
    print(token);
    final response = await MessengerApi().getChatList(token);
    print('여기가 리스폰즈 $response');
    final contact = await MessengerApi().getUserConnect(token);
    print('여기가 zjsxorzxm $contact');

    setState(() {
      chatList = List<Map>.from(response);
      print('이게 챗 리스트');
      print(chatList);
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
                        print(snapshot.error);
                        print(chatList);
                        return Text(
                          '에러뜨니까 확인해 Error: ${snapshot.error}',
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

  Map<String, dynamic>? teacherConnect;
  List chatList = [];
  List roomIdList = [];
  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";
    final response = await MessengerApi().getChatList(token);
    final contact = await MessengerApi().getTeacherConnect(token);
    print('내정보${context.read<UserStore>().userInfo}');
    print('상대정보 $contact');
    print(contact.runtimeType);

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
                                      roomInfo: null,
                                      userInfo: teacherConnect)));
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
                        return Center(
                          child: Text(
                            'Error: ${snapshot.error}',
                            style: TextStyle(fontSize: 15),
                          ),
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
