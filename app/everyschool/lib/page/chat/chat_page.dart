import 'package:everyschool/page/chat/chat_room.dart';
import 'package:flutter/material.dart';

class ChatPage extends StatefulWidget {
  const ChatPage({super.key});

  @override
  State<ChatPage> createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  List<String> roomNames = [
    '방이름1',
    '방이름2',
    '방이름3',
    '방이름4',
    '방이름5',
  ];

  void _navigateToChatRoom(String roomName) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => ChatRoom(roomName: roomName),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text('최근 대화')),
        body: ListView.builder(
          itemCount: roomNames.length,
          itemBuilder: (BuildContext context, int index) {
            return ListTile(
              title: Text(roomNames[index]),
              onTap: () {
                _navigateToChatRoom(roomNames[index]);
              },
            );
          },
        ));
  }
}
