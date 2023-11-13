import 'dart:async';
import 'dart:math';

import 'package:agora_rtc_engine/agora_rtc_engine.dart';
import 'package:dio/dio.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/call_modal.dart';
import 'package:everyschool/page/messenger/call/call_page.dart';
import 'package:everyschool/page/messenger/call/get_call.dart';
import 'package:everyschool/page/messenger/call/get_call_success.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';

class Connect extends StatefulWidget {
  const Connect({super.key, this.userConnect});

  final userConnect;

  @override
  State<Connect> createState() => _ConnectState();
}

class _ConnectState extends State<Connect> {
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

  var userInfo;

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
      _navigateToModalCallPage();
    }
  }

  void _navigateToModalCallPage() {
    print('갑니다');
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => GetCall(
            leave: leave, startDateTime: startDateTime, userInfo: userInfo),
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
    final myInfo = context.read<UserStore>().userInfo;
    final userKey = await storage.read(key: 'userKey') ?? "";
    var recordingDetail = await CallingApi().callRecordingStart(
        token, channelName, uid, chatroomtoken, userKey, contact['userKey']);
    sid = recordingDetail['sid'];
    resourceId = recordingDetail['resourceId'];
  }

  stopRecording() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);

    String sender = "T";

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
          setState(() {
            this.remoteUid = remoteUid;
            peopleGetCall = true;
          });
          Navigator.pushReplacement(
            context,
            PageRouteBuilder(
              pageBuilder: (BuildContext context, Animation<double> animation1,
                      Animation<double> animation2) =>
                  GetCallSuccess(leave: leave, userInfo: userInfo) //변경 필요
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
    final myInfo = context.read<UserStore>().userInfo;
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

  createRoom(index) async {
    print('실행');
    final token = await storage.read(key: 'token') ?? "";
    final userKey = widget.userConnect[index]['userKey'];
    final userName = widget.userConnect[index]['name'];
    final userType = widget.userConnect[index]['userType'];
    print(userKey);
    print(userName);
    print(userType);
    final mytype = await context.read<UserStore>().userInfo['userType'];
    final myclassId = await context.read<UserStore>().userInfo['schoolClass']
        ['schoolClassId'];

    final result = await MessengerApi()
        .createChatRoom(token, userKey, userType, userName, mytype, myclassId);
    print('함수');
    print(result);
    final newInfo = result;

    Navigator.push(context,
        MaterialPageRoute(builder: (context) => ChatRoom(roomInfo: newInfo)));
  }

  @override
  void initState() {
    super.initState();
    getChannelName(16);
    setupVoiceSDKEngine();
    print('유저정보 ${widget.userConnect}');
  }

  List<bool> showParentList = List<bool>.generate(50, (index) => false);

  @override
  Widget build(BuildContext context) {
    return widget.userConnect == null
        ? Center(
            child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('연락처가 없습니다'),
              Text('관리자에게 문의하세요'),
            ],
          ))
        : ListView.builder(
            itemCount: widget.userConnect.length,
            itemBuilder: (context, index) {
              return widget.userConnect[index]['parents'].length == 0
                  ? Container(
                      decoration: BoxDecoration(
                        border: Border(
                          bottom: BorderSide(
                            color: Color(0xffd9d9d9),
                            width: 1.0,
                          ),
                        ),
                      ),
                      child: ListTile(
                        title: SizedBox(
                          height: 55,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Padding(
                                padding: const EdgeInsets.fromLTRB(10, 0, 0, 0),
                                child: Text(widget.userConnect[index]['name'],
                                    style: TextStyle(fontSize: 18)),
                              ),
                              Row(
                                children: [
                                  if (channelName != null)
                                    GestureDetector(
                                      onTap: () {
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
                                            );
                                          },
                                        );
                                      },
                                      child: Padding(
                                        padding: const EdgeInsets.fromLTRB(
                                            10, 0, 15, 0),
                                        child: Icon(
                                          Icons.call,
                                          color:
                                              Color.fromARGB(255, 28, 175, 64),
                                          size: 30,
                                        ),
                                      ),
                                    ),
                                  GestureDetector(
                                    onTap: () {
                                      createRoom(index);
                                    },
                                    child: Padding(
                                      padding: const EdgeInsets.fromLTRB(
                                          0, 0, 10, 0),
                                      child: Image.asset(
                                        'assets/images/contact/chat_bubble.png',
                                        height: 28,
                                        width: 28,
                                      ),
                                    ),
                                  )
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                    )
                  : Column(
                      children: [
                        Container(
                          decoration: BoxDecoration(
                            border: Border(
                              bottom: BorderSide(
                                color: Color(0xffd9d9d9),
                                width: 1.0,
                              ),
                            ),
                          ),
                          child: ListTile(
                            onTap: () {
                              setState(() {
                                showParentList[index] = !showParentList[index];
                              });
                            },
                            title: SizedBox(
                              height: 55,
                              child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: [
                                  Row(
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.fromLTRB(
                                            10, 0, 15, 0),
                                        child: Text(
                                            widget.userConnect[index]['name'],
                                            style: TextStyle(fontSize: 18)),
                                      ),
                                      Container(
                                          padding:
                                              EdgeInsets.fromLTRB(5, 2, 10, 2),
                                          decoration: BoxDecoration(
                                            border: Border.all(
                                                color: Color.fromARGB(
                                                    255, 166, 221, 255)),
                                            borderRadius:
                                                BorderRadius.circular(100),
                                          ),
                                          child: Row(
                                            children: [
                                              Icon(Icons.check,
                                                  color: Color.fromARGB(
                                                      255, 166, 221, 255)),
                                              Text('부모님'),
                                            ],
                                          ))
                                    ],
                                  ),
                                  Row(
                                    children: [
                                      if (channelName != null)
                                        GestureDetector(
                                          onTap: () {
                                            userInfo =
                                                widget.userConnect[index];
                                            showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return CallModal(
                                                  join: fetchToken,
                                                  uid: uid,
                                                  channelName: channelName,
                                                  tokenRole: tokenRole,
                                                  serverUrl: serverUrl,
                                                  tokenExpireTime:
                                                      tokenExpireTime,
                                                  isTokenExpiring:
                                                      isTokenExpiring,
                                                  getUserKey:
                                                      widget.userConnect[index]
                                                          ['userKey'],
                                                );
                                              },
                                            );
                                          },
                                          child: Padding(
                                            padding: const EdgeInsets.fromLTRB(
                                                10, 0, 5, 0),
                                            child: Icon(
                                              Icons.call,
                                              color: Color.fromARGB(
                                                  255, 28, 175, 64),
                                              size: 30,
                                            ),
                                          ),
                                        ),
                                      GestureDetector(
                                        onTap: () {
                                          createRoom(index);
                                        },
                                        child: Padding(
                                          padding: const EdgeInsets.fromLTRB(
                                              5, 0, 10, 0),
                                          child: Image.asset(
                                            'assets/images/contact/chat_bubble.png',
                                            height: 28,
                                            width: 28,
                                          ),
                                        ),
                                      )
                                    ],
                                  )
                                ],
                              ),
                            ),
                          ),
                        ),
                        if (showParentList[index])
                          ...buildParentList(
                              widget.userConnect[index]['parents']),
                      ],
                    );
            },
          );
  }

  List<Widget> buildParentList(dynamic parents) {
    List<Map<String, dynamic>> typedParents =
        List<Map<String, dynamic>>.from(parents);

    return typedParents.map((parent) {
      String parentTypeText = '';
      if (parent['parentType'] == 'F') {
        parentTypeText = '아버님';
      } else if (parent['parentType'] == 'M') {
        parentTypeText = '어머님';
      }

      return Container(
        decoration: BoxDecoration(
          border: Border(
            bottom: BorderSide(
              color: Color(0xffd9d9d9),
              width: 1.0,
            ),
          ),
        ),
        child: ListTile(
          title: SizedBox(
            height: 55,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Padding(
                  padding: const EdgeInsets.fromLTRB(10, 0, 0, 0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(parent['name'], style: TextStyle(fontSize: 18)),
                      Text(parentTypeText, style: TextStyle(color: Colors.grey))
                    ],
                  ),
                ),
                Row(
                  children: [
                    if (channelName != null)
                      GestureDetector(
                        onTap: () {
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
                              );
                            },
                          );
                        },
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(10, 0, 15, 0),
                          child: Icon(
                            Icons.call,
                            color: Color.fromARGB(255, 28, 175, 64),
                            size: 30,
                          ),
                        ),
                      ),
                    GestureDetector(
                      onTap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    ChatRoom(roomInfo: parent)));
                      },
                      child: Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 10, 0),
                        child: Image.asset(
                          'assets/images/contact/chat_bubble.png',
                          height: 28,
                          width: 28,
                        ),
                      ),
                    )
                  ],
                )
              ],
            ),
          ),
        ),
      );
    }).toList();
  }
}
