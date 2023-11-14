import 'dart:async';
import 'dart:math';

import 'package:agora_rtc_engine/agora_rtc_engine.dart';
import 'package:dio/dio.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
import 'package:everyschool/page/messenger/call/call_modal.dart';
import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/call/get_call.dart';
import 'package:everyschool/page/messenger/call/get_call_success.dart';
import 'package:everyschool/page/messenger/call/modal_call_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';

class CallButton extends StatefulWidget {
  const CallButton({super.key, this.userInfo});
  final userInfo;

  @override
  State<CallButton> createState() => _CallButtonState();
}

class _CallButtonState extends State<CallButton> {
  final Dio dio = Dio();
  final storage = FlutterSecureStorage();

  String randomString = '';
  int tokenRole = 1; // use 1 for Host/Broadcaster, 2 for Subscriber/Audience
  String serverUrl =
      "https://agora-token-server-gst8.onrender.com"; // The base URL to your token server, for example "https://agora-token-service-production-92ff.up.railway.app"
  int tokenExpireTime = 6000; // Expire time in Seconds.
  bool isTokenExpiring = false; // Set to true when the token is about to expire
  String? channelName; // To access the TextField
  int uid = 1;

  bool peopleGetCall = false;

  var startDateTime = [];
  var endDateTime = [];

  int? timerDurationInSeconds = 60;
  Timer? timer;

  String? chatroomtoken;

  void startTimer() {
    timer = Timer(Duration(seconds: timerDurationInSeconds as int), () {
      if (remoteUid == null) {
        leave();
      }
    });
  }

  void cancelTimer() {
    if (timer != null && timer!.isActive) {
      timer!.cancel();
    }
  }

  getChannelName(length) {
    const String charset =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    final Random random = Random();
    final StringBuffer buffer = StringBuffer();

    for (int i = 0; i < length; i++) {
      final int randomIndex = random.nextInt(charset.length);
      buffer.write(charset[randomIndex]);
    }
    var newChannelName = buffer.toString();
    setState(() {
      channelName = newChannelName;
    });
  }

  Future<void> fetchToken(int uid, String channelName, int tokenRole,
      String serverUrl, int tokenExpireTime, bool isTokenExpiring) async {
    final channelId = channelName;
    // Prepare the Url
    String url =
        '$serverUrl/rtc/$channelName/${tokenRole.toString()}/uid/${uid.toString()}?expiry=${tokenExpireTime.toString()}';

    // Send the request
    final response = await dio.get(url);

    if (response.statusCode == 200) {
      // If the server returns an OK response, then parse the JSON.

      String newToken = response.data['rtcToken'];
      debugPrint('Token Received: $newToken');
      // Use the token to join a channel or renew an expiring token
      setToken(newToken, channelId, isTokenExpiring, uid);
      chatroomtoken = newToken;
    } else {
      // If the server did not return an OK response,
      // then throw an exception.
      throw Exception(
          'Failed to fetch a token. Make sure that your server URL is valid');
    }
  }

  void setToken(String newToken, String channelId, isTokenExpiring, uid) async {
    final reNewToken = newToken;
    ChannelMediaOptions options = const ChannelMediaOptions(
      clientRoleType: ClientRoleType.clientRoleBroadcaster,
      channelProfile: ChannelProfileType.channelProfileCommunication,
    );

    if (isTokenExpiring) {
      // Renew the token
      agoraEngine.renewToken(reNewToken);
      isTokenExpiring = false;
      showMessage("Token renewed");
    } else {
      // Join a channel.
      showMessage("Token received, joining a channel...");
      _navigateToModalCallPage();

      print('여기는 $reNewToken, $channelId, $uid');
      print('채널은 $channelId');
      await agoraEngine.joinChannel(
        token: reNewToken,
        channelId: channelId,
        options: options,
        uid: uid,
      );
      setState(() {
        isJoined = true;
      });
    }
  }

  void _navigateToModalCallPage() {
    print('내정보 ${widget.userInfo}');
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => GetCall(
            leave: leave,
            startDateTime: startDateTime,
            userInfo: widget.userInfo),
      ),
    );
  }

  List<int> datetimeToCustomList() {
    DateTime now = DateTime.now();
    return [
      now.year,
      now.month,
      now.day,
      now.hour,
      now.minute,
      now.second,
      now.microsecond
    ];
  }

  String? sid;
  String? resourceId;

  startRecording() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);
    final myInfo = await context.read<UserStore>().userInfo;
    final userKey = await storage.read(key: 'userKey') ?? "";
    var recordingDetail = await CallingApi().callRecordingStart(
        token, channelName, uid, chatroomtoken, userKey, contact['userKey']);
    sid = recordingDetail['sid'];
    resourceId = recordingDetail['resourceId'];
  }

  stopRecording() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);

    String sender = "O";

    endDateTime = datetimeToCustomList();
    await CallingApi().callRecordingStop(token, channelName, uid, resourceId,
        sid, contact['userKey'], sender, startDateTime, endDateTime);
  }

  Future<void> setupVoiceSDKEngine() async {
    // retrieve or request microphone permission
    await [Permission.microphone].request();
    print('마이크권한');

    //create an instance of the Agora engine
    agoraEngine = createAgoraRtcEngine();
    await agoraEngine.initialize(RtcEngineContext(appId: agoraConfig.appId));
    await agoraEngine.enableLocalAudio(true);
    print('아고라엔진시작');

    // Register the event handler
    agoraEngine.registerEventHandler(
      RtcEngineEventHandler(
        onJoinChannelSuccess: (RtcConnection connection, int elapsed) {
          showMessage(
              "Local user uid:${connection.localUid} joined the channel");
          print('아니 들어감:${connection.localUid} joined the channel');
          setState(() {
            isJoined = true;
            startDateTime = datetimeToCustomList();
          });
          startTimer();
        },
        onUserJoined: (RtcConnection connection, int remoteUid, int elapsed) {
          showMessage("Remote user uid:$remoteUid joined the channel");
          print('상대방전화받았음');
          print('아니 왜이래');
          setState(() {
            this.remoteUid = remoteUid;
            peopleGetCall = true;
          });
          Navigator.pushReplacement(
            context,
            PageRouteBuilder(
              pageBuilder: (BuildContext context, Animation<double> animation1,
                      Animation<double> animation2) =>
                  GetCallSuccess(
                      leave: leave, userInfo: widget.userInfo) //변경 필요
              ,
              transitionDuration: Duration.zero,
              reverseTransitionDuration: Duration.zero,
            ),
          );
          startRecording();
        },
        onError: (ErrorCodeType rtcError, String error) {
          print("Error code: ${rtcError.toString()}");
          print("Error description: ${rtcError.value()} 고요 $error");
        },
        onUserOffline: (RtcConnection connection, int remoteUid,
            UserOfflineReasonType reason) async {
          print('전화끊은 유저가 $remoteUid');
          showMessage("Remote user uid:$remoteUid left the channel");
          print('전화끊음');
          leave();
          setState(() {
            this.remoteUid = null;
          });
        },
      ),
    );
  }

  missedCallCheck() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);
    final myInfo = await context.read<UserStore>().userInfo;
    setState(() {
      endDateTime = datetimeToCustomList();
    });
    CallingApi().missedCall(
        token, contact['userKey'], myInfo['name'], startDateTime, endDateTime);
  }

  void leave() async {
    await stopRecording();
    setState(() {
      isJoined = false;
      remoteUid = null;
    });
    agoraEngine.leaveChannel();
    Navigator.of(context).pop();
    if (peopleGetCall == false) {
      missedCallCheck();
    }
    cancelTimer();
  }

  @override
  void dispose() async {
    print('아고라 엔진 종료');
    super.dispose();
    await agoraEngine.leaveChannel();
    await agoraEngine.release();
  }

  int? remoteUid;
  bool isJoined = false;
  late RtcEngine agoraEngine;

  final GlobalKey<ScaffoldMessengerState> scaffoldMessengerKey =
      GlobalKey<ScaffoldMessengerState>();

  showMessage(String message) {
    scaffoldMessengerKey.currentState?.showSnackBar(SnackBar(
      content: Text(message),
    ));
  }

  @override
  void initState() {
    super.initState();
    getChannelName(16);
    setupVoiceSDKEngine();
  }

  @override
  Widget build(BuildContext context) {
    if (channelName != null) {
      return IconButton(
        onPressed: () {
          showDialog(
            context: context,
            builder: (BuildContext context) {
              return CallModal(
                join: fetchToken,
                uid: uid,
                channelName: channelName,
                tokenRole: tokenRole,
                serverUrl: serverUrl,
                tokenExpireTime: tokenExpireTime,
                isTokenExpiring: isTokenExpiring,
                getUserKey: null,
              );
            },
          );
        },
        icon: Icon(Icons.call),
      );
    } else {
      return Container(); // 또는 다른 로딩 상태를 표시하는 위젯
    }
  }
}
