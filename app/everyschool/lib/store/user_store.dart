// 스토어로쓰세요
import 'package:flutter/foundation.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class UserStore extends ChangeNotifier {
  Map<String, dynamic> userInfo = {};

  setUserInfo(data) {
    userInfo = data;

    notifyListeners();
  }
}
