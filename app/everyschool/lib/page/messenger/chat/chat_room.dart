import 'package:everyschool/api/base_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/messenger/chat/bubble.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:everyschool/page/messenger/chat/chat_message_type.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';
import 'dart:convert';

final socketURL = SocketApi().wsURL;

class ChatRoom extends StatefulWidget {
  const ChatRoom({super.key});

  @override
  State<ChatRoom> createState() => _ChatRoomState();
}

class _ChatRoomState extends State<ChatRoom> {
  final storage = FlutterSecureStorage();
  String? token;
  int? roomId = 3;
  String? roomTitle;
  String? userName;

  createChatroom() async {
    final storage = FlutterSecureStorage();
    token = await storage.read(key: 'token') ?? "";

    // final result = await MessengerApi().createChatRoom(token);

    // setState(() {
    //   roomId = result['roomId'];
    //   roomTitle = result['roomTitle'];
    //   userName = result['userName'];
    // });
    stompClient.activate();
  }

  void sendMessage() {
    print('메시지 보내기');
    stompClient.send(
        destination: '/pub/chat.send',
        body: json.encode({
          'chatRoomId': 3,
          'senderUserKey': '68ab4b00-1729-4021-aa73-fa40a3ecc9e2',
          "message": context.read<ChatController>().textEditingController.text,
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
  }

  void onConnectCallback(StompFrame connectFrame) {
    // client is connected and ready
    print('connected');
    print('여기 룸 아이디 $roomId');
    stompClient.subscribe(
      destination: '/sub/3',
      headers: {'Authorization': 'Bearer $token'},
      callback: (frame) {
        print('여기가 바디야');
        print(frame.body);
        context.read<ChatController>().addNewMessage(Chat(
              message: json.decode(frame.body!)['message'],
              type: ChatMessageType.received,
              time: DateTime.now(),
            ));
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
    //Important: If your server is running on localhost and you are testing your app on Android then replace http://localhost:3000 with http://10.0.2.2:3000
    // 소켓 통신 시작
    createChatroom();
    print('시작');
    print('하는 도중');
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
        title: const Text(
          "담임선생님",
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
