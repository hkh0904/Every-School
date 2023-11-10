import 'package:everyschool/page/messenger/chat/chat_message_type.dart';

class Chat {
  final String message;
  final String sender;
  final DateTime time;

  Chat({required this.message, required this.sender, required this.time});

  factory Chat.sent({required message, required sender}) =>
      Chat(message: message, sender: sender, time: DateTime.now());
}
