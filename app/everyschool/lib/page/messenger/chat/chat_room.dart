import 'package:everyschool/api/base_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/messenger/chat/bubble.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:everyschool/page/messenger/chat/chat_message_type.dart';
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
  // 여기는 네비게이션 버튼 받아오는거
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
  Map<String, dynamic>? createRoomInfo = {};

  createChatroom() async {
    print('이거가 내가 확인해야할 정보');
    print(widget.userInfo);
    final storage = FlutterSecureStorage();
    token = await storage.read(key: 'token') ?? "";
    print(context.read<UserStore>().userInfo);
    userKey = widget.userInfo?['userKey'];
    userName = widget.userInfo?['name'];
    userType = widget.userInfo['userType'];

    mytype = context.read<UserStore>().userInfo['userType'];
    myclassId =
        context.read<UserStore>().userInfo['schoolClass']['schoolClassId'];

    print('$userKey $myclassId');
    // userKey = await myInfo[] ?? "";
    // userType = await storage.read(key: 'usertype') ?? "";
    final result = await MessengerApi()
        .createChatRoom(token, userKey, userType, userName, mytype, myclassId);

    setState(() {
      createRoomInfo = result;
      //   roomTitle = result['roomTitle'];
      //   userName = result['userName'];
    });
    stompClient.activate();
  }

  void sendMessage() async {
    final myKey = await storage.read(key: 'userkey');
    final filter = await MessengerApi().chatFilter(
        token,
        widget.roomInfo == null
            ? createRoomInfo!['roomId']
            : widget.roomInfo['roomId'],
        myKey,
        context.read<ChatController>().textEditingController.text);
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

      print('메시지 보내기2');
      context.read<ChatController>().addNewMessage(Chat(
            message: context.read<ChatController>().textEditingController.text,
            type: ChatMessageType.sent,
            time: DateTime.now(),
          ));
      context.read<ChatController>().onFieldSubmitted();
      print('메시지 보내기3');
      setState(() {});
    } else {
      print('문제가 있어 보내지 않았습니다');
    }
  }

  void onConnectCallback(StompFrame connectFrame) {
    // client is connected and ready
    print('connected');
    print('여기 룸 아이디 ${widget.roomInfo?['roomId']}');
    stompClient.subscribe(
      destination: widget.roomInfo == null
          ? '/sub/${createRoomInfo!['roomId']}}'
          : '/sub/${widget.roomInfo?['roomId']}',
      headers: {'Authorization': 'Bearer $token'},
      callback: (frame) {
        print('여기가 바디야');
        print(frame.body);
        // context.read<ChatController>().addNewMessage(Chat(
        //       message: json.decode(frame.body!)['message'],
        //       type: ChatMessageType.received,
        //       time: DateTime.now(),
        //     ));
        setState(() {});
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
    super.initState();
    if (widget.userInfo['userType'] == 'T') {
      position = '선생님';
    } else if (widget.userInfo['userType'] == 'S') {
      position = '학생';
    } else {
      position = '학부모님';
    }

    // print(widget.roomId);
    print(widget.roomInfo);
    widget.roomInfo == null ? createChatroom() : null;
    print('시작');
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
    return Scaffold(
      resizeToAvoidBottomInset: true,
      appBar: AppBar(
        leading: BackButton(color: Colors.black),
        title: Text(
          widget.roomInfo == null
              ? '${widget.userInfo!['name']} $position'
              : '${widget.roomInfo?['userName']}',
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
                // FocusScope.of(context).unfocus();
              },
              child: Align(
                alignment: Alignment.topCenter,
                child: Selector<ChatController, List<Chat>>(
                  selector: (context, controller) =>
                      controller.chatList.reversed.toList(),
                  builder: (context, chatList, child) {
                    return ListView.separated(
                      shrinkWrap: true,
                      reverse: true,
                      padding: const EdgeInsets.only(top: 12, bottom: 20) +
                          const EdgeInsets.symmetric(horizontal: 12),
                      separatorBuilder: (_, __) => const SizedBox(
                        height: 12,
                      ),
                      controller:
                          context.read<ChatController>().scrollController,
                      itemCount: chatList.length,
                      itemBuilder: (context, index) {
                        return Bubble(chat: chatList[index]);
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
                    onChanged: context.read<ChatController>().onFieldChanged,
                    controller:
                        context.read<ChatController>().textEditingController,
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
}

/// Bottom Fixed Filed
