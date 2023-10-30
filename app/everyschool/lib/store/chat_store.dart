import 'package:everyschool/page/chat/message_model.dart';
import 'package:flutter/foundation.dart';

class ChatStore extends ChangeNotifier {
  final List<Message> _messages = [];

  List<Message> get messages => _messages;

  addNewMessage(Message message) {
    _messages.add(message);
    notifyListeners();
  }
}
