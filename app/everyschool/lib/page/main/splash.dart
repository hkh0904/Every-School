import 'package:everyschool/main.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:everyschool/page/messenger/call/answer_call.dart';
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

  @override
  void initState() {
    super.initState();
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.immersive);

    getToken();

    checkAndNavigationCallingPage();

    Future.delayed(Duration(seconds: 3), () {
      if (token != null && token!.isNotEmpty) {
        Navigator.of(context).pushReplacement(MaterialPageRoute(
          builder: (_) => const Main(),
        ));
      } else {
        Navigator.of(context).pushReplacement(MaterialPageRoute(
          builder: (_) => const LoginPage(),
        ));
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
