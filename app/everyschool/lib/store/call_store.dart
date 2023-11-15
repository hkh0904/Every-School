// 스토어로쓰세요
import 'package:flutter/foundation.dart';

class CallStore extends ChangeNotifier {
  bool receiver = true;
  bool sender = true;

  setReceiver() {
    receiver = !receiver;
    notifyListeners();
  }

  setSender() {
    sender = !sender;
    notifyListeners();
  }
}
