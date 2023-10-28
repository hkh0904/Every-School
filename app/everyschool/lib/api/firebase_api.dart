import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
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

// 포그라운드 메세지 처리
  void foregroundMessage(RemoteMessage message) {
    RemoteNotification? notification = message.notification;

    if (notification != null) {
      FlutterLocalNotificationsPlugin().show(
          notification.hashCode,
          notification.title,
          notification.body,
          const NotificationDetails(
            android: AndroidNotificationDetails(
              'high_importance_channel',
              'high_importance_notification',
              importance: Importance.max,
            ),
          ),
          // 메세지 전달은 이렇게하는거라는데...
          payload: message.data['id']);
    }
  }

  void initializeNotifications(context) async {
    final flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();
    await flutterLocalNotificationsPlugin
        .resolvePlatformSpecificImplementation<
            AndroidFlutterLocalNotificationsPlugin>()
        ?.createNotificationChannel(const AndroidNotificationChannel(
            'high_importance_channel', 'high_importance_notification',
            importance: Importance.max));

    await flutterLocalNotificationsPlugin.initialize(
        const InitializationSettings(
          android: AndroidInitializationSettings("@mipmap/ic_launcher"),
        ),
        // foreground일때 알림 눌렀을때(detail에 상담 payload값이 들어있음 details.payload 이렇게 받음)
        onDidReceiveNotificationResponse: (NotificationResponse details) async {
      Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ConsultingListPage()));
    });

    await FirebaseMessaging.instance
        .setForegroundNotificationPresentationOptions(
      alert: true,
      badge: true,
      sound: true,
    );

    RemoteMessage? message =
        await FirebaseMessaging.instance.getInitialMessage();
    if (message != null) {
      Navigator.push(context,
          MaterialPageRoute(builder: (context) => const ConsultingListPage()));
    }
  }
}
