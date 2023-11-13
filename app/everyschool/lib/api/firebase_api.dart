import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/main.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/global_variable.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_callkit_incoming/entities/entities.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:uuid/uuid.dart';
// import 'package:flutter_local_notifications/flutter_local_notifications.dart';

final storage = FlutterSecureStorage();

@pragma('vm:entry-point')
Future<void> firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  if (message.data['type'] == 'call') {
    DateTime currentTime = DateTime.now();
    var userType = await storage.read(key: 'usertype');

    print(message.data);

    if (userType == "1003") {
      var time = await CallingApi().muteTimeInquiry();

      DateTime startTime = DateTime.parse(time['startTime']);
      DateTime endTime = DateTime.parse(time['endTime']);
      if (currentTime.isAfter(startTime) &&
          currentTime.isBefore(endTime) &&
          time['isActivate'] == true) {
        print('현재 시간이 방해 금지 시간에 속합니다.');
      } else {
        print('현재 시간이 방해 금지 시간에 속하지않습니다.');
        var name = message.notification!.title;
        var phoneNumber = message.notification!.body;
        var channelName = message.data['cname'];
        showCallkitIncoming('10', name as String, phoneNumber as String,
            channelName as String, message.data['senderUserKey']);
      }
    } else {
      var name = message.notification!.title;
      var phoneNumber = message.notification!.body;
      var channelName = message.data['cname'];
      showCallkitIncoming('10', name as String, phoneNumber as String,
          channelName as String, message.data['senderUserKey']);
    }
  } else if (message.data['type'] == 'cancel') {
    FlutterCallkitIncoming.endAllCalls();
  } else if (message.data['type'] == 'denied') {
    Navigator.pop(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext);
  }
}

Future<void> showCallkitIncoming(String uuid, String name, String phoneNumber,
    String channelName, String senderUserKey) async {
  String startTime = DateTime.now().toString();
  var userKey = await storage.read(key: 'userKey');
  final params = CallKitParams(
      id: uuid,
      nameCaller: name,
      appName: 'Callkit',
      // avatar: null,
      handle: phoneNumber,
      type: 0,
      duration: 30000,
      textAccept: '받기',
      textDecline: '거절하기',
      missedCallNotification: const NotificationParams(
        showNotification: true,
        isShowCallback: true,
        subtitle: 'Missed call',
        callbackText: 'Call back',
      ),
      extra: <String, dynamic>{
        'userId': channelName,
        'startTime': startTime,
        'otherUserKey': senderUserKey
      },
      headers: <String, dynamic>{'apiKey': 'Abc@123!', 'platform': 'flutter'},
      android: const AndroidParams(
        isCustomNotification: true,
        isShowLogo: false,
        ringtonePath: 'system_ringtone_default',
        backgroundColor: '#0955fa',
        backgroundUrl: 'assets/test.png',
        actionColor: '#4CAF50',
      ));
  await FlutterCallkitIncoming.showCallkitIncoming(params);
}

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
  void foregroundMessage(RemoteMessage message) async {
    RemoteNotification? notification = message.notification;

    print('메세지!!!!! 노티피케이션 ${message.notification}');
    print('메세지!!!!! 데이터 ${message.data}');
    var userType = await storage.read(key: 'usertype');

    if (notification != null) {
      if (message.data['type'] == 'call') {
        DateTime currentTime = DateTime.now();
        var userType = await storage.read(key: 'usertype');

        if (userType == "1003") {
          var time = await CallingApi().muteTimeInquiry();

          DateTime startTime = DateTime.parse(time['startTime']);
          DateTime endTime = DateTime.parse(time['endTime']);
          if (currentTime.isAfter(startTime) &&
              currentTime.isBefore(endTime) &&
              time['isActivate'] == true) {
            print('현재 시간이 방해 금지 시간에 속합니다.');
          } else {
            print('현재 시간이 방해 금지 시간에 속하지않습니다.');
            var name = message.notification!.title;
            var phoneNumber = message.notification!.body;
            var channelName = message.data['cname'];
            showCallkitIncoming('10', name as String, phoneNumber as String,
                channelName as String, message.data['senderUserKey'] as String);
          }
        } else {
          var name = message.notification!.title;
          var phoneNumber = message.notification!.body;
          var channelName = message.data['cname'];
          showCallkitIncoming('10', name as String, phoneNumber as String,
              channelName as String, message.data['senderUserKey']);
        }
      } else if (message.data['type'] == 'cancel') {
        FlutterCallkitIncoming.endAllCalls();
      } else if (message.data['type'] == 'denied') {
        Navigator.pop(
            CandyGlobalVariable.naviagatorState.currentContext as BuildContext);
      }
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
        payload: message.data['id'],
      );
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
      if (message.data['type'] == 'call') {
        // var name = message.notification!.title;
        // var phoneNumber = message.notification!.body;
        // showCallkitIncoming('10', name as String, phoneNumber as String);
        // getIncomingCall();
      } else {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => const ConsultingListPage()));
      }
    }
  }

  Future<void> getIncomingCall(context) async {
    FlutterCallkitIncoming.onEvent.listen((event) {
      print('이벤트 $event');
      print('바디는 ${event!.body['nameCaller']}');
      String? channelName = event.body['extra']['userId'];
      switch (event.event) {
        case Event.actionCallIncoming:
          // TODO: received an incoming call
          print('전화옴');
          break;
        case Event.actionCallStart:
          // TODO: started an outgoing call
          // TODO: show screen calling in Flutter
          break;
        case Event.actionCallAccept:
          // TODO: accepted an incoming call
          // TODO: show screen calling in Flutter
          print('콜받음');
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => AnswerCall(
                      channelName: channelName,
                      name: event.body['nameCaller'])));
          break;
        case Event.actionCallDecline:
          // TODO: declined an incoming call
          print('안받음');
          CallingApi().deniedCall(event.body['extra']['otherUserKey'],
              event.body['nameCaller'], event.body['extra']['startTime']);
          break;
        case Event.actionCallEnded:
          // TODO: ended an incoming/outgoing call
          print('전화끊음');

          break;
        case Event.actionCallTimeout:
          // TODO: missed an incoming call
          print('전화옴');
          break;
        case Event.actionCallCallback:
          // TODO: only Android - click action `Call back` from missed call notification
          print('전화옴');
          break;
        case Event.actionCallToggleHold:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionCallToggleMute:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionCallToggleDmtf:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionCallToggleGroup:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionCallToggleAudioSession:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionDidUpdateDevicePushTokenVoip:
          // TODO: only iOS
          print('전화옴');
          break;
        case Event.actionCallCustom:
          // TODO: for custom action
          print('전화옴');
          break;
      }
    });
  }
}
