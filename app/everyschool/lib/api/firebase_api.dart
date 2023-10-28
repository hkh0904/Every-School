import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
// import 'package:flutter_local_notifications/flutter_local_notifications.dart';

class FirebaseApi {
  // 토큰 얻어오기
  getMyDeviceToken() async {
    final firebaseMessaging = FirebaseMessaging.instance;
    await firebaseMessaging.requestPermission();
    final fcmToken = await firebaseMessaging.getToken();
    print('토큰은 $fcmToken');

    FirebaseMessaging.instance.onTokenRefresh.listen((fcmToken) {
      // TODO: If necessary send token to application server.

      // Note: This callback is fired at each app startup and whenever a new
      // token is generated.
    }).onError((err) {
      // Error getting token.
    });
    return fcmToken.toString();
  }

// 백그라운드 메세지 수신시 알림 누르면
  Future<void> setupInteractedMessage(context) async {
    // 데이터 포함한 메세지 담은 부분
    RemoteMessage? initialMessage =
        await FirebaseMessaging.instance.getInitialMessage();

    // 조건에 따른 함수 여는 부분
    if (initialMessage != null) {
      _handleMessage(initialMessage, context);
    }

    // 스트림 구독
    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
      _handleMessage(message, context);
    });
  }

  // 여는 창 예시
  void _handleMessage(initialMessage, context) {
    Navigator.push(context,
        MaterialPageRoute(builder: (context) => const ConsultingListPage()));
  }
}
