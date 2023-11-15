import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/main.dart';
import 'package:everyschool/page/home/select_child.dart';
import 'package:everyschool/page/login/approve_waiting.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
import 'package:everyschool/page/mypage/add_child.dart';
import 'package:everyschool/page/mypage/register_child.dart';
import 'package:everyschool/page/mypage/select_school.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:uuid/uuid.dart';

class Splash extends StatefulWidget {
  const Splash({super.key});

  @override
  State<Splash> createState() => _SplashState();
}

class _SplashState extends State<Splash> {
  String? token;

  late final Uuid _uuid;
  String? _currentUuid;
  List? calls;
  final storage = FlutterSecureStorage();

  @override
  void initState() {
    super.initState();
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersive);

    getToken();

    checkAndNavigationCallingPage();

    Future.delayed(Duration(seconds: 3), () async {
      if (token != null && token!.length > 0) {
        var usertype = await storage.read(key: 'usertype');
        var userInfo = await UserApi().getUserRegisterInfo(token);
        print('유저정보 $userInfo');

        if (usertype == "1001") {
          if (userInfo['message'] == '학급 신청 후 이용바랍니다.') {
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                builder: (BuildContext context) => SelectSchool(),
              ),
            );
          } else if (userInfo['message'] == 'SUCCESS') {
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                builder: (BuildContext context) => Main(),
              ),
            );
          } else {
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                builder: (BuildContext context) => ApproveWaiting(),
              ),
            );
          }
        } else if (usertype == "1002") {
          if (userInfo['data']['descendants'].length > 0) {
            Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                builder: (BuildContext context) => Main(),
              ),
            );
          } else {
            if (userInfo['message'] == '자녀 등록이 필요합니다.') {
              Navigator.pushReplacement(
                context,
                MaterialPageRoute(
                  builder: (BuildContext context) => RegisterChild(),
                ),
              );
            } else {
              Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(
                    builder: (BuildContext context) => RegisterChild(),
                  ));
            }
          }
        } else {
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(
              builder: (BuildContext context) => Main(),
            ),
          );
        }
      } else {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (BuildContext context) => LoginPage(),
          ),
        );
      }
    });
  }

  Future<dynamic> getCurrentCall() async {
    //check current call from pushkit if possible
    // var calls = await FlutterCallkitIncoming.activeCalls();
    var calls = null;
    if (calls is List) {
      if (calls.isNotEmpty) {
        print('DATA: $calls');
        _currentUuid = calls[0]['id'];
        return calls[0];
      } else {
        _currentUuid = "";
        return null;
      }
    }
  }

  Future<void> checkAndNavigationCallingPage() async {
    var currentCall = await getCurrentCall();
    print('현재콜 $currentCall');
    print('현재콜 ${currentCall.runtimeType}');

    // if (currentCall.runtimeType != Null) {
    //   Navigator.pushReplacement(
    //       context, MaterialPageRoute(builder: (context) => const AnswerCall()));
    // }
  }

  void getToken() async {
    final storage = FlutterSecureStorage();
    // await storage.delete(key: 'token');
    // await storage.delete(key: 'userkey');
    // await storage.delete(key: 'usertype');
    // await storage.delete(key: 'userName');
    // await storage.delete(key: 'descendant');
    token = await storage.read(key: 'token') ?? "";
  }

  @override
  void dispose() {
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.manual,
        overlays: SystemUiOverlay.values);
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: Colors.grey[50],
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        margin: EdgeInsets.fromLTRB(0, 0, 0, 35),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset(
              'assets/images/splash/splash.png',
              width: 220,
            ),
          ],
        ),
      ),
      bottomNavigationBar: Container(
        margin: EdgeInsets.fromLTRB(0, 0, 0, 45),
        child: Text(
          'ⓒ everySCHOOL. SSAFY',
          style: TextStyle(
            fontWeight: FontWeight.w700,
          ),
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}
