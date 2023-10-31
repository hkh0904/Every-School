import 'package:everyschool/api/stomp_client.dart';
import 'package:everyschool/page/chat/chat_list_student.dart';
import 'package:everyschool/page/chat/chat_list_teacher.dart';
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp.dart';

class ChatPage extends StatefulWidget {
  const ChatPage({super.key});

  @override
  State<ChatPage> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  @override
  void initState() {
    // 소켓 통신 시작
    SocketHandler().stompClient.activate();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('채팅목록')),
      body: Container(
          child: TextButton(
        child: Text('하하'),
        onPressed: () {
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => const ChatListStudent()));
        },
      )),
    );
  }
}
