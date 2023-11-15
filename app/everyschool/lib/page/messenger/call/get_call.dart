import 'package:assets_audio_player/assets_audio_player.dart';
import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

final assetsAudioPlayer = AssetsAudioPlayer();

class GetCall extends StatefulWidget {
  const GetCall({super.key, this.leave, this.startDateTime, this.userInfo});
  final leave;
  final startDateTime;
  final userInfo;

  @override
  State<GetCall> createState() => _GetCallState();
}

class _GetCallState extends State<GetCall> {
  @override
  void initState() {
    super.initState();
    assetsAudioPlayer.open(
      Audio("assets/audios/ringtone.mp3"),
      autoStart: true,
      loopMode: LoopMode.single,
    );
    print('유저정보 ${widget.userInfo}');
  }

  @override
  void dispose() {
    super.dispose();
    assetsAudioPlayer.stop();
  }

  final storage = FlutterSecureStorage();

  var endDateTime = [];

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

  checkCancelCall() async {
    final token = await storage.read(key: 'token') ?? "";
    final contact = await MessengerApi().getTeacherConnect(token);
    final myInfo = await context.read<UserStore>().userInfo;
    setState(() {
      endDateTime = datetimeToCustomList();
    });

    CallingApi().cancelCall(token, contact['userKey'], myInfo['name'],
        widget.startDateTime, endDateTime);
  }

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
            '연결중...',
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
          // Text(
          //   widget.userInfo['name'],
          //   style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          // ),
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.25,
          ),
          Center(
            child: GestureDetector(
              onTap: () async {
                await checkCancelCall();
                widget.leave();
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
