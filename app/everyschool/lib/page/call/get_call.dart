import 'package:assets_audio_player/assets_audio_player.dart';
import 'package:flutter/material.dart';

final assetsAudioPlayer = AssetsAudioPlayer();

class GetCall extends StatefulWidget {
  const GetCall({super.key, this.leave});
  final leave;

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
  }

  @override
  void dispose() {
    super.dispose();
    assetsAudioPlayer.stop();
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
          ClipRRect(
            borderRadius: BorderRadius.circular(100),
            child: Image.asset(
              'assets/images/consulting/detail.png',
              height: 150,
              width: 150,
            ),
          ),
          SizedBox(
            height: 15,
          ),
          Text(
            '받는사람 정보',
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          Text(
            '유저설명',
            style: TextStyle(fontSize: 18),
          ),
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.25,
          ),
          Center(
            child: GestureDetector(
              onTap: widget.leave,
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
