import 'package:flutter/foundation.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class ChatStore extends ChangeNotifier {
  final List<Message> _messages = [];

  addNewMessage(Message message) {
    _messages.add(message);
    notifyListeners();
  }
}
