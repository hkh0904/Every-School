class Chat {
  final String message;
  final String sender;
  final DateTime time;

  Chat({required this.message, required this.sender, required this.time});

  factory Chat.sent({required message, required sender, required time}) =>
      Chat(message: message, sender: sender, time: time);
}
