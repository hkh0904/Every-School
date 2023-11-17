import 'package:everyschool/api/firebase_api.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/store/call_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';

import 'dart:math';

import 'package:agora_rtc_engine/agora_rtc_engine.dart';
import 'package:dio/dio.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
import 'package:everyschool/page/messenger/call/call_modal.dart';
import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/call/get_call.dart';
import 'package:everyschool/page/messenger/call/get_call_success.dart';
import 'package:everyschool/page/messenger/call/modal_call_page.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';

class AnswerCall extends StatefulWidget {
  const AnswerCall({super.key, this.channelName, this.name, this.otherUserKey});
  final channelName;
  final name;
  final otherUserKey;

  @override
  State<AnswerCall> createState() => _AnswerCallState();
}

class _AnswerCallState extends State<AnswerCall> {
  final Dio dio = Dio();

  String randomString = '';
  int tokenRole = 1; // use 1 for Host/Broadcaster, 2 for Subscriber/Audience
  String serverUrl =
      "https://agora-token-server-gst8.onrender.com"; // The base URL to your token server, for example "https://agora-token-service-production-92ff.up.railway.app"
  int tokenExpireTime = 600000; // Expire time in Seconds.
  bool isTokenExpiring =
      false; // Set to true when the token is about to expire // To access the TextField
  int uid = 52128942;

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

      if ((uid != null)) {
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
  }

  Future<void> setupVoiceSDKEngine() async {
    // retrieve or request microphone permission
    await [Permission.microphone].request();

    //create an instance of the Agora engine
    agoraEngine = createAgoraRtcEngine();
    await agoraEngine.initialize(RtcEngineContext(appId: agoraConfig.appId));
    await agoraEngine.enableLocalAudio(true);

    // Register the event handler
    agoraEngine.registerEventHandler(
      RtcEngineEventHandler(
        onJoinChannelSuccess: (RtcConnection connection, int elapsed) {
          showMessage(
              "Local user uid:${connection.localUid} joined the channel");
          // setState(() {
          //   isJoined = true;
          // });
        },
        onUserJoined: (RtcConnection connection, int remoteUid, int elapsed) {
          showMessage("Remote user uid:$remoteUid joined the channel");

          setState(() {
            this.remoteUid = remoteUid;
          });
        },
        onError: (ErrorCodeType rtcError, String error) {},
        onUserOffline: (RtcConnection connection, int remoteUid,
            UserOfflineReasonType reason) {
          showMessage("Remote user uid:$remoteUid left the channel");
          setState(() {
            this.remoteUid = null;
          });
          leave();
        },
      ),
    );
  }

  void leave() {
    setState(() {
      isJoined = false;
      remoteUid = null;
    });
    agoraEngine.leaveChannel();
    Navigator.of(context).pop();
  }

  @override
  void dispose() async {
    super.dispose();
    if (remoteUid != null) {
      await agoraEngine.leaveChannel();
    }
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
    setupVoiceSDKEngine();
    fetchToken(uid, widget.channelName, tokenRole, serverUrl, tokenExpireTime,
        isTokenExpiring);
  }

  bool canClick = true;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SizedBox(
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      // color: Colors.grey,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        // crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            '연결됨',
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          SizedBox(
            height: 20,
          ),
          Image.asset(
            'assets/images/contact/call.gif',
            height: 150,
            width: 150,
          ),
          SizedBox(
            height: 15,
          ),
          Text(
            widget.name,
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.25,
          ),
          if (remoteUid != null)
            Center(
              child: GestureDetector(
                onTap: () async {
                  final sender = await context.read<CallStore>().sender;
                  if (canClick && sender) {
                    setState(() {
                      canClick = false;
                    });
                    var storage = FlutterSecureStorage();
                    var token = await storage.read(key: 'token') ?? "";
                    await CallingApi()
                        .callReceiverStop(token, widget.otherUserKey);
                    leave();
                  }
                },
                child: Container(
                  height: 80,
                  width: 80,
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(100),
                    color: Color(0xffFF3B3B),
                  ),
                  child: Icon(
                    Icons.call_end,
                    color: Colors.white,
                    size: 35,
                  ),
                ),
              ),
            )
        ],
      ),
    ));
  }
}
