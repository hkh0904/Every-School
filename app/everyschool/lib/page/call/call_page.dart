import 'dart:async';
import 'package:everyschool/page/call/call_list.dart';
import 'package:everyschool/page/call/get_call.dart';
import 'package:everyschool/page/call/get_call_success.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:agora_rtc_engine/agora_rtc_engine.dart';

const String appId = "f69df8611e874246bc2616951c3d1696";

class CallPage extends StatefulWidget {
  const CallPage({super.key});

  @override
  State<CallPage> createState() => _CallPageState();
}

class _CallPageState extends State<CallPage> {
  String channelName = "ok";
  String token =
      "007eJxTYJj7qvy79SdemzsHA3gWSMhK8Sy/15W58I3t7B/L/j7b/adIgSHNzDIlzcLM0DDVwtzEyMQsKdnIzNDM0tQw2TgFSJtVXnZMbQhkZJC8FcXACIUgPhNDfjYDAwCQ0yAA";
  int uid = 0;

  Future<void> setupVoiceSDKEngine() async {
    // retrieve or request microphone permission
    await [Permission.microphone].request();
    print('마이크권한');

    //create an instance of the Agora engine
    agoraEngine = createAgoraRtcEngine();
    await agoraEngine.initialize(const RtcEngineContext(appId: appId));
    print('아고라엔진시작');

    // Register the event handler
    agoraEngine.registerEventHandler(
      RtcEngineEventHandler(
        onJoinChannelSuccess: (RtcConnection connection, int elapsed) {
          showMessage(
              "Local user uid:${connection.localUid} joined the channel");
          setState(() {
            _isJoined = true;
          });
        },
        onUserJoined: (RtcConnection connection, int remoteUid, int elapsed) {
          showMessage("Remote user uid:$remoteUid joined the channel");
          print('상대방전화받았음');
          setState(() {
            _remoteUid = remoteUid;
          });
        },
        onError: (ErrorCodeType rtcError, String error) {
          print("Error code: ${rtcError.toString()}");
          print("Error description: ${rtcError.value()} 고요 $error");
        },
        onUserOffline: (RtcConnection connection, int remoteUid,
            UserOfflineReasonType reason) {
          showMessage("Remote user uid:$remoteUid left the channel");
          print('전화끊음');
          setState(() {
            _remoteUid = null;
          });
        },
      ),
    );
  }

  void join() async {
    print('들어오다...');
    // Set channel options including the client role and channel profile
    ChannelMediaOptions options = const ChannelMediaOptions(
      clientRoleType: ClientRoleType.clientRoleBroadcaster,
      channelProfile: ChannelProfileType.channelProfileCommunication,
    );

    await agoraEngine.joinChannel(
      token: token,
      channelId: channelName,
      options: options,
      uid: uid,
    );
    print('들어가다...');
  }

  void leave() {
    setState(() {
      _isJoined = false;
      _remoteUid = null;
    });
    agoraEngine.leaveChannel();
  }

// Clean up the resources when you leave
  @override
  void dispose() async {
    await agoraEngine.leaveChannel();
    super.dispose();
  }

  int? _remoteUid;
  bool _isJoined = false;
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
    setupVoiceSDKEngine();
  }

  @override
  Widget build(BuildContext context) {
    // return GetCall(leave: leave);
    if (!_isJoined) {
      return CallList(join: join, leave: leave);
    } else if (_remoteUid == null) {
      return GetCall(leave: leave);
    } else {
      return GetCallSuccess(leave: leave);
    }
  }

  Widget _status() {
    String statusText;

    if (!_isJoined)
      statusText = 'Join a channel';
    else if (_remoteUid == null)
      statusText = 'Waiting for a remote user to join...';
    else
      statusText = 'Connected to remote user, uid:$_remoteUid';

    return Text(
      statusText,
    );
  }
}
