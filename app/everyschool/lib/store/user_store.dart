// 스토어로쓰세요
import 'package:flutter/foundation.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class UserStore extends ChangeNotifier {
  String year = '2023';
  Map<String, dynamic> userInfo = {};

  setUserInfo(data) {
    userInfo = data;

    notifyListeners();
  }

  setYear(data) {
    year = data;
    notifyListeners();
  }

  bool policycheck = false;
  changePolicyCheck() {
    policycheck = !policycheck;
    notifyListeners();
  }

  disposePolicyCheck() {
    policycheck = false;
  }
}
