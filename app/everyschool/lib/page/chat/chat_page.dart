import 'package:everyschool/api/stomp_client.dart';
import 'package:everyschool/page/chat/chat_controller.dart';
import 'package:everyschool/page/chat/chat_room.dart';
import 'package:flutter/material.dart';

class ChatPage extends StatefulWidget {
  const ChatPage({super.key});

  @override
  State<ChatPage> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  @override
  void initState() {
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
              MaterialPageRoute(builder: (context) => const ChatRoom()));
        },
      )),
    );
  }
}
