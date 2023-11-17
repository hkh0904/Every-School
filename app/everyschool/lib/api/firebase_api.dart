import 'dart:convert';

import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/main.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/global_variable.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:everyschool/page/report/report_detail.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:everyschool/store/call_store.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_callkit_incoming/entities/entities.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:uuid/uuid.dart';
// import 'package:flutter_local_notifications/flutter_local_notifications.dart';

final storage = FlutterSecureStorage();

@pragma('vm:entry-point')
Future<void> firebaseMessagingBackgroundHandler(RemoteMessage message) async {
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
      } else {
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
  } else if (message.data['type'] == 'sender') {
    Provider.of<CallStore>(
            CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
            listen: false)
        .setSender();
  } else if (message.data['type'] == 'receiver') {
    Provider.of<CallStore>(
            CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
            listen: false)
        .setReceiver();
  } else if (message.data['type'] == 'report') {
    Navigator.push(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
        MaterialPageRoute(
            builder: (context) =>
                ReportDetail(item: {'reportId': message.data['reportId']})));
  } else if (message.data['type'] == "consult") {
    Navigator.push(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
        MaterialPageRoute(
            builder: (context) => ReportConsultingPage(index: 0)));
  } else if (message.data['type'] == 'chat') {
    Navigator.push(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
        MaterialPageRoute(
            builder: (context) => ChatRoom(
                  roomInfo: {
                    "roomId": int.parse(message.data['chatRoomId']),
                    "opponentUserName": message.data['senderUserName'],
                    "opponentUserType": message.data['senderUserType'],
                    "opponentUserChildName":
                        message.data['senderUserChildName'],
                  },
                )));
  } else if (message.data['type'] == 'noti') {
    Navigator.push(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
        MaterialPageRoute(
            builder: (context) => PostDetail(
                boardName: '가정통신문',
                boardId: int.parse(message.data['boardId']))));
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
      if (message.data['type'] == 'report') {
        _handleMessage(message, context);
      } else if (message.data['type'] == 'consult') {
        _handleConsult(message, context);
      } else if (message.data['type'] == 'chat') {
        _handleChat(message, context);
      } else if (message.data['type'] == 'noti') {
        _handleNoti(message, context);
      }
    });
  }

  // 여는 창 예시
  void _handleMessage(initialMessage, context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => ReportDetail(item: {
                  'reportId': int.parse(initialMessage.data['reportId'])
                })));
  }

  void _handleConsult(initialMessage, context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => ReportConsultingPage(index: 0)));
  }

  void _handleChat(initialMessage, context) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => ChatRoom(
                  roomInfo: {
                    "roomId": int.parse(initialMessage.data['chatRoomId']),
                    "opponentUserName": initialMessage.data['senderUserName'],
                    "opponentUserType": initialMessage.data['senderUserType'],
                    "opponentUserChildName":
                        initialMessage.data['senderUserChildName'],
                  },
                )));
  }

  void _handleNoti(initialMessage, context) {
    Navigator.push(
        CandyGlobalVariable.naviagatorState.currentContext as BuildContext,
        MaterialPageRoute(
            builder: (context) => PostDetail(
                boardName: '학사 공지',
                boardId: int.parse(initialMessage.data['boardId']))));
  }

// 포그라운드 메세지 처리
  void foregroundMessage(RemoteMessage message) async {
    RemoteNotification? notification = message.notification;

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
          } else {
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
      } else if (message.data['type'] == 'sender') {
        Provider.of<CallStore>(
                CandyGlobalVariable.naviagatorState.currentContext
                    as BuildContext,
                listen: false)
            .setSender();
      } else if (message.data['type'] == 'receiver') {
        Provider.of<CallStore>(
                CandyGlobalVariable.naviagatorState.currentContext
                    as BuildContext,
                listen: false)
            .setReceiver();
      } else if (message.data['type'] == 'report') {
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
          payload: jsonEncode({
            'type': message.data['type'],
            'reportId': message.data['reportId'],
          }),
        );
      } else if (message.data['type'] == 'consult') {
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
            payload: jsonEncode({
              'type': message.data['type'],
            }));
      } else if (message.data['type'] == 'chat') {
        Provider.of<ChatController>(
                CandyGlobalVariable.naviagatorState.currentContext
                    as BuildContext,
                listen: false)
            .setIsUpdated();
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
            payload: jsonEncode({
              'type': message.data['type'],
              'roomId': message.data['chatRoomId'],
              'opponentUserName': message.data['senderUserName'],
              'opponentUserType': message.data['senderUserType'],
              'opponentUserChildName': message.data['senderUserChildName'],
            }));
      } else if (message.data['type'] == 'noti') {
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
            payload: jsonEncode({
              'type': message.data['type'],
              'boardName': message.data['boardName'],
              'boardId': message.data['boardId'],
            }));
      }
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
          android: AndroidInitializationSettings("@mipmap/launcher_icon"),
        ),
        // foreground일때 알림 눌렀을때(detail에 상담 payload값이 들어있음 details.payload 이렇게 받음)
        onDidReceiveNotificationResponse: (NotificationResponse details) async {
      if (details.payload != null) {
        var payloadMap = jsonDecode(details.payload as String);
        if (payloadMap['type'] == "report") {
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => ReportDetail(
                      item: {'reportId': int.parse(payloadMap['reportId'])})));
        } else if (payloadMap['type'] == "consult") {
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => ReportConsultingPage(index: 0)));
        } else if (payloadMap['type'] == 'chat') {
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => ChatRoom(
                        roomInfo: {
                          "roomId": int.parse(payloadMap['roomId']),
                          "opponentUserName": payloadMap['opponentUserName'],
                          "opponentUserType": payloadMap['opponentUserType'],
                          "opponentUserChildName":
                              payloadMap['opponentUserChildName'],
                        },
                      )));
        } else if (payloadMap['type'] == 'noti') {
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => PostDetail(
                      boardName: '학사 공지',
                      boardId: int.parse(payloadMap['boardId']))));
        }
      }
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
      } else if (message.data['type'] == 'report') {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => ReportDetail(
                    item: {'reportId': int.parse(message.data['reportId'])})));
      } else {}
    }
  }

  Future<void> getIncomingCall(context) async {
    FlutterCallkitIncoming.onEvent.listen((event) {
      String? channelName = event!.body['extra']['userId'];
      switch (event.event) {
        case Event.actionCallIncoming:
          // TODO: received an incoming call
          break;
        case Event.actionCallStart:
          // TODO: started an outgoing call
          // TODO: show screen calling in Flutter
          break;
        case Event.actionCallAccept:
          // TODO: accepted an incoming call
          // TODO: show screen calling in Flutter
          Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => AnswerCall(
                      channelName: channelName,
                      name: event.body['nameCaller'],
                      otherUserKey: event.body['extra']['otherUserKey'])));
          break;
        case Event.actionCallDecline:
          // TODO: declined an incoming call
          CallingApi().deniedCall(event.body['extra']['otherUserKey'],
              event.body['nameCaller'], event.body['extra']['startTime']);
          break;
        case Event.actionCallEnded:
          // TODO: ended an incoming/outgoing call

          break;
        case Event.actionCallTimeout:
          // TODO: missed an incoming call
          break;
        case Event.actionCallCallback:
          // TODO: only Android - click action `Call back` from missed call notification
          break;
        case Event.actionCallToggleHold:
          // TODO: only iOS
          break;
        case Event.actionCallToggleMute:
          // TODO: only iOS
          break;
        case Event.actionCallToggleDmtf:
          // TODO: only iOS
          break;
        case Event.actionCallToggleGroup:
          // TODO: only iOS
          break;
        case Event.actionCallToggleAudioSession:
          // TODO: only iOS
          break;
        case Event.actionDidUpdateDevicePushTokenVoip:
          // TODO: only iOS
          break;
        case Event.actionCallCustom:
          // TODO: for custom action
          break;
      }
    });
  }
}
