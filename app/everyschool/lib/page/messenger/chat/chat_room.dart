import 'package:everyschool/api/base_api.dart';
import 'package:everyschool/page/messenger/chat/bubble.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:everyschool/page/messenger/chat/chat_message_type.dart';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:provider/provider.dart';
import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';
import 'dart:convert';

final socketURL = SocketApi().socketURL;

class ChatRoom extends StatefulWidget {
  const ChatRoom({super.key});

  @override
  State<ChatRoom> createState() => _ChatRoomState();
}

class _ChatRoomState extends State<ChatRoom> {
  sendMessage() {
    print('메시지 보내기');
    stompClient.send(
        destination: '/pub/chat.sendMessage',
        body: json.encode({
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
    stompClient.subscribe(
      destination: '/sub/public',
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
          webSocketConnectHeaders: {'login': 'your', 'password': 'hhhh'},
          onConnect: onConnectCallback));

  @override
  void initState() {
    super.initState();
    //Important: If your server is running on localhost and you are testing your app on Android then replace http://localhost:3000 with http://10.0.2.2:3000
    // 소켓 통신 시작
    print('시작');
    stompClient.activate();
    print('하는 도중');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: true,
      appBar: AppBar(
        leading: BackButton(color: Colors.black),
        title: const Text(
          "여기 변경하기",
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
