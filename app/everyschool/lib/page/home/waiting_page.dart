import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:provider/provider.dart';

class WaitingPage extends StatefulWidget {
  const WaitingPage({super.key});

  @override
  State<WaitingPage> createState() => _WaitingPageState();
}

class _WaitingPageState extends State<WaitingPage> {
  late int userType;
  final storage = FlutterSecureStorage();

  @override
  void initState() {
    super.initState();
    _checkUser();
  }

  Future<void> _checkUser() async {
    userType = context.read<UserStore>().userInfo["userType"];
    print('승인 대기 페이지 $userType');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
      color: Colors.grey[50],
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset('assets/images/home/title.png'),
            SizedBox(height: 20),
            Text(
              userType == 1001 ? '학급 등록 승인 대기 중 입니다.' : '자녀 등록 승인 대기 중 입니다.',
              style: TextStyle(
                  fontSize: 25,
                  fontWeight: FontWeight.w700,
                  color: Color(0XFF15075F)),
            ),
            SizedBox(height: 35),
            Text(
              '곧 담임선생님이 승인 이후',
              style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.w700,
                  color: Color(0XFF15075F)),
            ),
            SizedBox(height: 5),
            Text(
              '서비스를 이용하실 수 있습니다.😭',
              style: TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.w700,
                  color: Color(0XFF15075F)),
            ),
            SizedBox(height: 150),
          ],
        ),
      ),
    ));
  }
}
