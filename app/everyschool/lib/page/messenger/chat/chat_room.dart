import 'package:everyschool/api/base_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/messenger/chat/bubble.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat.dart';

import 'package:everyschool/store/user_store.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';
import 'dart:convert';

final socketURL = SocketApi().wsURL;

class ChatRoom extends StatefulWidget {
  ChatRoom({super.key, this.roomInfo, this.userInfo});
  // 여기는 채팅 리스트에서 오는거
  final roomInfo;

  // 여기는 네비게이션 버튼이랑 연락처에서 받아오는거 받아오는거
  final userInfo;

  @override
  State<ChatRoom> createState() => _ChatRoomState();
}

class _ChatRoomState extends State<ChatRoom> {
  final storage = FlutterSecureStorage();
  String? position;
  int? roomNumber;
  String? token;
  String? userKey;
  String? userType;
  String? userName;
  int? mytype;
  int? myclassId;
  String mykey = '';
  Map<String, dynamic>? createRoomInfo = {};
  getkey() async {
    final kkk = await storage.read(key: 'userKey');
    setState(() {
      mykey = kkk!;
    });
  }

  getToken() async {
    token = await storage.read(key: 'token') ?? "";
  }

  getChat() async {
    token = await storage.read(key: 'token') ?? "";

    final response = await MessengerApi().getChatListItem(
      token,
      widget.roomInfo == null
          ? createRoomInfo!['roomId']
          : widget.roomInfo['roomId'],
    );
    print('이게 정말 채팅 내역');
    print(response);
    if (response.length != 0) {
      final newList = response
          .map((chat) => Chat(
              message: chat['content'],
              sender: chat['mine'] == true ? mykey : '',
              time: DateTime.parse(chat['sendTime'])))
          .toList();
      print(newList);

      context.read<ChatController>().setChatList(newList.cast<Chat>());
    }
  }

  createChatroom() async {
    print('이거 한번만해? 1');
    token = await storage.read(key: 'token') ?? "";
    print(token);
    userKey = widget.userInfo?['userKey'];
    userName = widget.userInfo?['name'];
    userType = widget.userInfo['userType'];

    mytype = await context.read<UserStore>().userInfo['userType'];
    myclassId = await context.read<UserStore>().userInfo['schoolClass']
        ['schoolClassId'];

    final result = await MessengerApi()
        .createChatRoom(token, userKey, userType, userName, mytype, myclassId);
    print('여기 리절트$result');
    createRoomInfo = result;
    if (result['opponentUserType'] == 'T') {
      position = '선생님';
    } else if (result['opponentUserType'] == 'S') {
      position = '학생';
    } else {
      position = '학부모님';
    }
    stompClient.activate();
    print('이거 한번만해? 2');

    return 0;
  }

  void sendMessage() async {
    final token = await storage.read(key: 'token') ?? "";
    final response = await MessengerApi().getChatList(token);

    await context
        .read<ChatController>()
        .changechatroomList(List<Map>.from(response));
    final myKey = await storage.read(key: 'userKey');
    final filter = await MessengerApi().chatFilter(
        token,
        widget.roomInfo == null
            ? createRoomInfo!['roomId']
            : widget.roomInfo['roomId'],
        myKey,
        context.read<ChatController>().textEditingController.text);
    print(filter);
    if (filter['isBad'] == false) {
      stompClient.send(
          destination: '/pub/chat.send',
          body: json.encode({
            'chatRoomId': widget.roomInfo == null
                ? createRoomInfo!['roomId']
                : widget.roomInfo['roomId'],
            'senderUserKey': myKey,
            "message":
                context.read<ChatController>().textEditingController.text,
          }),
          headers: {});

      context.read<ChatController>().onFieldSubmitted();
    } else {
      print('문제가 있어 보내지 않았습니다');
    }
  }

  void onConnectCallback(StompFrame connectFrame) {
    print('소켓연결');
    print('/sub/${createRoomInfo!['roomId']}');
    print('/sub/${widget.roomInfo?['roomId']}');

    stompClient.subscribe(
      destination: widget.roomInfo == null
          ? '/sub/${createRoomInfo!['roomId']}'
          : '/sub/${widget.roomInfo?['roomId']}',
      headers: {'Authorization': 'Bearer $token'},
      callback: (frame) async {
        print('메세지 보냈어');
        print(json.decode(frame.body!));
        print(json.decode(frame.body!)['senderUserKey']);

        Provider.of<ChatController>(context, listen: false).addNewMessage(Chat(
          message: json.decode(frame.body!)['message'],
          sender: json.decode(frame.body!)['senderUserKey'],
          time: DateTime.now(),
        ));
      },
    );
  }

  late StompClient stompClient = StompClient(
      config: StompConfig(
          url: socketURL,
          webSocketConnectHeaders: {'Authorization': 'Bearer $token'},
          onConnect: onConnectCallback));

  @override
  void initState() {
    context.read<ChatController>().clearChatList();

    getChat();
    getkey();
    getToken();
    super.initState();
    if (widget.roomInfo != null) {
      if (widget.roomInfo['opponentUserType'] == 'T') {
        setState(() {
          position = '선생님';
        });
      } else if (widget.roomInfo['opponentUserType'] == 'S') {
        setState(() {
          position = '학생';
        });
      } else {
        setState(() {
          position = '학부모님';
        });
      }
    }
  }

  getInitstate() async {
    stompClient.activate();
    return 0;
  }

  @override
  void dispose() {
    stompClient.deactivate();

    // dispose 메서드에서 리소스나 이벤트를 해제합니다.
    // 메모리 누수를 방지하기 위해 이곳에서 구독 해제 등의 정리 작업을 수행합니다.
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: widget.roomInfo == null ? createChatroom() : getInitstate(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
              resizeToAvoidBottomInset: true,
              appBar: AppBar(
                leading: BackButton(color: Colors.black),
                title: Text(
                  widget.roomInfo == null
                      ? '${createRoomInfo!['opponentUserName']} $position'
                      : '${widget.roomInfo?['opponentUserName']} $position',
                  style: TextStyle(color: Colors.black),
                ),
                centerTitle: true,
                backgroundColor: Colors.grey[50],
              ),
              body: Column(
                children: [
                  Expanded(
                    child: GestureDetector(
                      onTap: () {
                        context.read<ChatController>().focusNode.unfocus();
                      },
                      child: Align(
                        alignment: Alignment.topCenter,
                        child: Selector<ChatController, List<Chat>>(
                          selector: (context, controller) =>
                              controller.chatList.toList(),
                          builder: (context, chatList, child) {
                            return ListView.separated(
                              shrinkWrap: true,
                              reverse: true,
                              padding: const EdgeInsets.only(
                                      top: 12, bottom: 20) +
                                  const EdgeInsets.symmetric(horizontal: 12),
                              separatorBuilder: (_, __) => const SizedBox(
                                height: 12,
                              ),
                              controller: context
                                  .read<ChatController>()
                                  .scrollController,
                              itemCount: chatList.length,
                              itemBuilder: (context, index) {
                                return Bubble(
                                    chat: chatList[index], myKey: mykey);
                              },
                            );
                          },
                        ),
                      ),
                    ),
                  ),
                  SafeArea(
                    bottom: true,
                    child: Container(
                      constraints: const BoxConstraints(minHeight: 48),
                      width: double.infinity,
                      decoration: const BoxDecoration(
                        border: Border(
                          top: BorderSide(
                            color: Color(0xFFE5E5EA),
                          ),
                        ),
                      ),
                      child: Stack(
                        children: [
                          TextField(
                            focusNode: context.read<ChatController>().focusNode,
                            onChanged:
                                context.read<ChatController>().onFieldChanged,
                            controller: context
                                .read<ChatController>()
                                .textEditingController,
                            maxLines: null,
                            textAlignVertical: TextAlignVertical.top,
                            decoration: InputDecoration(
                              border: InputBorder.none,
                              contentPadding: const EdgeInsets.only(
                                right: 42,
                                left: 16,
                                top: 18,
                              ),
                              hintText: 'message',
                              enabledBorder: OutlineInputBorder(
                                borderSide: BorderSide.none,
                                borderRadius: BorderRadius.circular(8.0),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderSide: BorderSide.none,
                                borderRadius: BorderRadius.circular(8.0),
                              ),
                            ),
                          ),
                          // custom suffix btn
                          Positioned(
                            bottom: 0,
                            right: 0,
                            child: IconButton(
                              icon: Icon(Icons.send),
                              onPressed: sendMessage,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            );
          } else if (snapshot.hasError) {
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'Error: ${snapshot.error}',
                style: TextStyle(fontSize: 15),
              ),
            );
          } else {
            return Scaffold(
              appBar: AppBar(
                leading: BackButton(color: Colors.black),
                title: Text(''),
                centerTitle: true,
                backgroundColor: Colors.grey[50],
              ),
              body: Column(
                children: [
                  Spacer(),
                  SafeArea(
                    bottom: true,
                    child: Container(
                      constraints: const BoxConstraints(minHeight: 48),
                      width: double.infinity,
                      decoration: const BoxDecoration(
                        border: Border(
                          top: BorderSide(
                            color: Color(0xFFE5E5EA),
                          ),
                        ),
                      ),
                      child: Stack(
                        children: [
                          TextField(
                            focusNode: context.read<ChatController>().focusNode,
                            onChanged:
                                context.read<ChatController>().onFieldChanged,
                            controller: context
                                .read<ChatController>()
                                .textEditingController,
                            maxLines: null,
                            textAlignVertical: TextAlignVertical.top,
                            decoration: InputDecoration(
                              border: InputBorder.none,
                              contentPadding: const EdgeInsets.only(
                                right: 42,
                                left: 16,
                                top: 18,
                              ),
                              hintText: 'message',
                              enabledBorder: OutlineInputBorder(
                                borderSide: BorderSide.none,
                                borderRadius: BorderRadius.circular(8.0),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderSide: BorderSide.none,
                                borderRadius: BorderRadius.circular(8.0),
                              ),
                            ),
                          ),
                          // custom suffix btn
                          Positioned(
                            bottom: 0,
                            right: 0,
                            child: IconButton(
                              icon: Icon(Icons.send),
                              onPressed: sendMessage,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            );
          }
        });
  }
}
