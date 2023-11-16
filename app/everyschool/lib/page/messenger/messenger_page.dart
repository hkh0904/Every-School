import 'dart:convert';

import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/call_button.dart';
import 'package:everyschool/page/messenger/call/call_history.dart';

import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat_list.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:everyschool/page/messenger/chat/connect.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class MessengerPage extends StatefulWidget {
  const MessengerPage({super.key, this.indexNum});
  final indexNum;

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
    return userType == 1003
        ? ManagerTapBar(indexNum: widget.indexNum)
        : UserTapBar(indexNum: widget.indexNum);
  }
}

class ManagerTapBar extends StatefulWidget {
  ManagerTapBar({super.key, this.indexNum});
  final indexNum;

  @override
  State<ManagerTapBar> createState() => _ManagerTapBarState();
}

class _ManagerTapBarState extends State<ManagerTapBar> {
  final storage = FlutterSecureStorage();

  List? userConnect;

  List roomIdList = [];
  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";

    final response = await MessengerApi().getChatList(token);

    final contact = await MessengerApi().getUserConnect(token);
    if (response.runtimeType != Null) {
      await context
          .read<ChatController>()
          .changechatroomList(List<Map>.from(response));
    }
    setState(() {
      userConnect = contact;
    });

    return response;
  }

  Future<dynamic>? chatroomListFuture;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    chatroomListFuture = _getChatList();
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      initialIndex: widget.indexNum,
      child: Scaffold(
        body: SafeArea(
          child: NestedScrollView(
            headerSliverBuilder:
                (BuildContext context, bool innerBoxIsScrolled) {
              return [
                SliverAppBar(
                  elevation: 8,
                  title: Text(
                    '메신저',
                    style: TextStyle(
                        fontSize: 21,
                        fontWeight: FontWeight.bold,
                        color: Colors.black),
                  ),
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
                  pinned: true,
                ),
              ];
            },
            body: TabBarView(
              children: [
                FutureBuilder(
                    future: chatroomListFuture,
                    builder: (BuildContext context, AsyncSnapshot snapshot) {
                      if (snapshot.hasData) {
                        return ChatList(
                            chatroomList:
                                context.read<ChatController>().chatroomList);
                      } else if (snapshot.hasError) {
                        print(snapshot.error);

                        return Container(
                          height: 800,
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
  UserTapBar({super.key, this.indexNum});
  final indexNum;

  @override
  State<UserTapBar> createState() => _UserTapBarState();
}

class _UserTapBarState extends State<UserTapBar> {
  final storage = FlutterSecureStorage();

  Map<String, dynamic>? teacherConnect;
  List chatroomList = [];

  int? roomId = 0;

  TextStyle tapBarTextStyle = TextStyle(fontSize: 16, color: Colors.black);

  _getChatList() async {
    final token = await storage.read(key: 'token') ?? "";
    final response = await MessengerApi().getChatList(token);

    await context
        .read<ChatController>()
        .changechatroomList(List<Map>.from(response));

    setState(() {
      chatroomList = response;
    });
    print(response);
    return response;
  }

  createRoom() async {
    print('실행');
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);

    final userKey = contact['userKey'];
    final userName = contact['name'];
    final userType = contact['userType'];
    print(userKey);
    print(userName);
    print(userType);
    dynamic _myclassId;
    final mytype = await context.read<UserStore>().userInfo['userType'];
    if (mytype == 1002) {
      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);
      _myclassId = selectDescendant['schoolClass']['schoolClassId'];
      print(_myclassId);
    } else {
      final myInfo = await context.read<UserStore>().userInfo;
      _myclassId = myInfo['schoolClass']['schoolClassId'];
      print(_myclassId);
    }

    final result = await MessengerApi()
        .createChatRoom(token, userKey, userType, userName, mytype, _myclassId);
    print('함수');
    print(result);
    final newInfo = result;

    Navigator.push(context,
        MaterialPageRoute(builder: (context) => ChatRoom(roomInfo: newInfo)));
  }

  getTeacherInfo() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);
    print('왜안나와 $contact');
    return contact;
  }

  Future<dynamic>? chatroomListFuture;
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    chatroomListFuture = _getChatList();
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      initialIndex: widget.indexNum,
      child: Scaffold(
        body: SafeArea(
          child: NestedScrollView(
            headerSliverBuilder:
                (BuildContext context, bool innerBoxIsScrolled) {
              return [
                SliverAppBar(
                  title: Text(
                    '메신저',
                    style: TextStyle(
                        fontSize: 21,
                        fontWeight: FontWeight.bold,
                        color: Colors.black),
                  ),
                  backgroundColor: Colors.grey[50],
                  pinned: true,
                  actions: [
                    Center(
                        child: Text('담임선생님',
                            style: TextStyle(
                                color: Colors.black,
                                fontWeight: FontWeight.bold))),
                    FutureBuilder(
                        future: getTeacherInfo(),
                        builder:
                            (BuildContext context, AsyncSnapshot snapshot) {
                          if (snapshot.hasData) {
                            return CallButton(userInfo: snapshot.data);
                          } else if (snapshot.hasError) {
                            return Container(
                              height: 800,
                            );
                          } else {
                            return Container(
                              height: 800,
                            );
                          }
                        }),
                    IconButton(
                        onPressed: () {
                          createRoom();
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
                ),
              ];
            },
            body: TabBarView(
              children: [
                FutureBuilder(
                    future: chatroomListFuture,
                    builder: (BuildContext context, AsyncSnapshot snapshot) {
                      if (snapshot.hasData) {
                        return ChatList(chatroomList: chatroomList);
                      } else if (snapshot.hasError) {
                        return Container(
                          height: 800,
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
