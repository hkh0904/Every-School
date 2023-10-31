import 'package:everyschool/page/chat/chat_message_type.dart';

class Chat {
  final String message;
  final ChatMessageType type;
  final DateTime time;

  Chat({required this.message, required this.type, required this.time});

  factory Chat.sent({required message}) =>
      Chat(message: message, type: ChatMessageType.sent, time: DateTime.now());

  static List<Chat> generate() {
    return [
      Chat(
        message: "Definitely!",
        type: ChatMessageType.received,
        time: DateTime.now(),
      ),
    ];
  }

// static List<Chat> generate() {
  //   return [
  //     Chat(
  //       message: "Hello!",
  //       type: ChatMessageType.sent,
  //       time: DateTime.now().subtract(const Duration(minutes: 5)),
  //     ),
  //     Chat(
  //       message: "Nice to meet you!",
  //       type: ChatMessageType.received,
  //       time: DateTime.now().subtract(const Duration(minutes: 4)),
  //     ),
  //     Chat(
  //       message: "The weather is nice today.",
  //       type: ChatMessageType.sent,
  //       time: DateTime.now().subtract(const Duration(minutes: 3)),
  //     ),
  //     Chat(
  //       message: "Yes, it's a great day to go out.",
  //       type: ChatMessageType.received,
  //       time: DateTime.now().subtract(const Duration(minutes: 2)),
  //     ),
  //     Chat(
  //       message: "Have a nice day!",
  //       type: ChatMessageType.sent,
  //       time: DateTime.now().subtract(const Duration(minutes: 1)),
  //     ),
  //   ];
  // }
}
