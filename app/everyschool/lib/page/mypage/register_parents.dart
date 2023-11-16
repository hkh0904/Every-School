import 'dart:async';
import 'package:flutter/material.dart';
import 'package:everyschool/api/user_api.dart';

class RegisterParents extends StatefulWidget {
  const RegisterParents({super.key});

  @override
  State<RegisterParents> createState() => _RegisterParentsState();
}

class _RegisterParentsState extends State<RegisterParents> {
  final UserApi userApi = UserApi();
  var registerCode = '';
  late Timer _timer;
  int _start = 180; // 3분을 초 단위로 계산
  bool _isTimeExpired = false;

  @override
  void initState() {
    super.initState();
    _loadRegisterCode();
    startTimer();
  }

  void startTimer() {
    const oneSec = Duration(seconds: 1);
    _timer = Timer.periodic(
      oneSec,
      (Timer timer) {
        if (_start == 0) {
          setState(() {
            timer.cancel();
            _isTimeExpired = true;
          });
        } else {
          setState(() {
            _start--;
          });
        }
      },
    );
  }

  void restartTimer() {
    _timer.cancel(); // 현재 타이머를 취소
    _start = 180; // 시간을 다시 180초로 설정
    _isTimeExpired = false; // 시간 만료 상태를 초기화
    startTimer(); // 타이머를 다시 시작
  }

  void reloadRegisterCode() async {
    await _loadRegisterCode(); // 코드를 다시 로드
    restartTimer(); // 타이머를 재시작
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  Future<void> _loadRegisterCode() async {
    var response;
    try {
      response = await userApi.registerParents();
      setState(() {
        registerCode = response;
      });
    } catch (e) {
      print('error: $e');
    }
  }

  String formatTime(int seconds) {
    int minute = seconds ~/ 60;
    int second = seconds % 60;
    return '${minute.toString().padLeft(2, '0')}:${second.toString().padLeft(2, '0')}';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        elevation: 0,
        centerTitle: true,
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          '학부모 등록하기',
          style: TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.w700,
          ),
        ),
      ),
      body: Container(
        padding: EdgeInsets.all(20),
        width: double.infinity,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            SizedBox(height: 70),
            Text(
              '부모님 휴대폰에서\n아래 코드를 입력해주세요.',
              style: TextStyle(
                fontSize: 20,
                color: Colors.grey,
              ),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
            Stack(
              clipBehavior: Clip.none, // Stack의 자식들이 경계를 넘어갈 수 있도록 설정
              children: <Widget>[
                Container(
                  width: double.infinity, // 박스의 너비를 최대로 설정
                  padding: EdgeInsets.symmetric(
                      vertical: 40, horizontal: 20), // 내부 여백 조정
                  decoration: BoxDecoration(
                    border: Border.all(color: Colors.grey),
                    borderRadius: BorderRadius.circular(10),
                  ),
                  child: Column(
                    children: <Widget>[
                      SizedBox(height: 20),
                      Text(
                        registerCode,
                        style: TextStyle(
                          fontSize: 30,
                          fontWeight: FontWeight.w800,
                          letterSpacing: 2,
                        ),
                      ),
                      SizedBox(height: 10),
                      Text(
                        _isTimeExpired
                            ? "등록시간 만료"
                            : '남은 시간 : ${formatTime(_start)}',
                        style: TextStyle(
                          fontSize: 25,
                          fontWeight: FontWeight.w600,
                          color: _isTimeExpired ? Colors.red : Colors.grey[600],
                        ),
                      ),
                      SizedBox(height: 20),
                    ],
                  ),
                ),
                Positioned(
                  top: -15, // 상단으로 조금 이동시켜 겹치게 합니다
                  left: 20,
                  right: 20,
                  child: Container(
                    padding: EdgeInsets.symmetric(horizontal: 40),
                    child: Container(
                      width: 200, // 하위 컨테이너의 너비를 조절
                      color: Theme.of(context).scaffoldBackgroundColor,
                      child: Text(
                        '학부모 등록 코드',
                        style: TextStyle(
                          fontSize: 23,
                          fontWeight: FontWeight.w700,
                        ),
                        textAlign: TextAlign.center,
                      ),
                    ),
                  ),
                ),
              ],
            ),
            SizedBox(height: 30),
            ElevatedButton(
              onPressed: reloadRegisterCode,
              style: ButtonStyle(
                minimumSize:
                    MaterialStateProperty.all(Size(double.infinity, 36)),
                backgroundColor:
                    MaterialStateProperty.all(Color(0XFF15075F)), // 버튼 색상 설정
                padding:
                    MaterialStateProperty.all(EdgeInsets.all(10)), // 내부 패딩 설정
              ),
              child: Text(
                '등록코드 재발급',
                style: TextStyle(
                  fontSize: 21,
                  fontWeight: FontWeight.w600,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
