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
  ChatRoom({
    super.key,
    this.roomInfo,
  });
  // 여기는 채팅 리스트에서 오는거
  final roomInfo;

  // 여기는 네비게이션 버튼이랑 연락처에서 받아오는거 받아오는거

  @override
  State<ChatRoom> createState() => _ChatRoomState();
}

class _ChatRoomState extends State<ChatRoom> {
  final storage = FlutterSecureStorage();
  var subscription;

  String? position;
  int? roomNumber;
  String? token;
  String? userKey;
  String? userType;
  String? userName;
  int? mytype;
  int? myclassId;
  String mykey = '';

  int? chatLastIdx;

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

    final response = await MessengerApi()
        .getChatListItem(token, widget.roomInfo['roomId'], chatLastIdx);
    if (response.length != 0) {
      final newList = List<Chat>.from(response.map((chat) => Chat(
          message: chat['content'],
          sender: chat['mine'] == true ? mykey : '',
          time: DateTime.parse(chat['sendTime']))));

      await context.read<ChatController>().setChatList(newList);
      chatLastIdx = response[response.length - 1]['chatId'];
      setState(() {});
    }
  }

  createChatroom() async {
    print('크리에이트 실행');
    print(widget.roomInfo['roomId']);
    print(
      widget.roomInfo['roomId'],
    );

    await getChat();

    stompClient.activate();

    return 0;
  }

  void sendMessage() async {
    print('보냄');
    print(widget.roomInfo['roomId']);
    final token = await storage.read(key: 'token') ?? "";
    final response = await MessengerApi().getChatList(token);

    await context
        .read<ChatController>()
        .changechatroomList(List<Map>.from(response));
    final myKey = await storage.read(key: 'userKey');
    final filter = await MessengerApi().chatFilter(
        token,
        widget.roomInfo['roomId'],
        myKey,
        context.read<ChatController>().textEditingController.text);
    print('필터링 보냄');
    if (filter['isBad'] == false) {
      print('소래만들었을때');
      stompClient.send(
          destination: '/pub/chat.send',
          body: json.encode({
            'chatRoomId': widget.roomInfo['roomId'],
            'senderUserKey': myKey,
            "message":
                context.read<ChatController>().textEditingController.text,
          }),
          headers: {});

      context.read<ChatController>().onFieldSubmitted();
    } else {
      print('문제가 있어 보내지 않았습니다');
    }
    setState(() {});
  }

  void onConnectCallback(StompFrame connectFrame) {
    print('받음');
    print(widget.roomInfo['roomId']);
    var subscription = stompClient.subscribe(
      destination: '/sub/${widget.roomInfo['roomId']}',
      headers: {'Authorization': 'Bearer $token'},
      callback: (frame) {
        print('메세지 보냈어');
        print(frame.body!);
        context.read<ChatController>().addNewMessage(Chat(
              message: json.decode(frame.body!)['message'],
              sender: json.decode(frame.body!)['senderUserKey'],
              time: DateTime.now(),
            ));
        setState(() {});
      },
    );

    print('저기2');
  }

  void _handleScrollToTop() {
    getChat();
    // 원하는 함수를 여기에 추가
  }

  late StompClient stompClient = StompClient(
      config: StompConfig(
          url: socketURL,
          webSocketConnectHeaders: {'Authorization': 'Bearer $token'},
          onConnect: onConnectCallback));

  _getChatList() async {
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
    await getChat();

    stompClient.activate();

    return 0;
  }

  getinit() async {
    getToken();
    getkey();

    // TODO: implement initState'
    getInit = widget.roomInfo == null ? createChatroom() : _getChatList();
  }

  Future<dynamic>? getInit;
  @override
  void initState() {
    getinit();
    super.initState();
  }

  disconnect(myKey) async {
    print(widget.roomInfo['roomId']);
    print(myKey);
    stompClient.send(
        destination: '/pub/chat.unsub.${widget.roomInfo['roomId']}',
        body: json.encode({
          'userKey': myKey,
        }),
        headers: {});

    stompClient.deactivate();
  }

  @override
  void dispose() {
    disconnect(mykey);
    // dispose 메서드에서 리소스나 이벤트를 해제합니다.
    // 메모리 누수를 방지하기 위해 이곳에서 구독 해제 등의 정리 작업을 수행합니다.
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getInit,
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return WillPopScope(
              onWillPop: () async {
                await Provider.of<ChatController>(context, listen: false)
                    .clearChatList();
                return true;
              },
              child: Scaffold(
                resizeToAvoidBottomInset: true,
                appBar: AppBar(
                  leading: BackButton(
                    color: Colors.black,
                    onPressed: () async {
                      await Provider.of<ChatController>(context, listen: false)
                          .clearChatList();
                      Navigator.pop(context);
                    },
                  ),
                  title: Text(
                    '${widget.roomInfo?['opponentUserName']} $position',
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
                              focusNode:
                                  context.read<ChatController>().focusNode,
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
            // return Container();
            return Scaffold(
              appBar: AppBar(
                leading: BackButton(color: Colors.black),
                title: Text(
                  '',
                  style: TextStyle(color: Colors.black),
                ),
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
