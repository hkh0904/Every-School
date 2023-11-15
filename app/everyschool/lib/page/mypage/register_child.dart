import 'dart:async';
import 'package:flutter/material.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:everyschool/page/home/waiting_page.dart';

class RegisterChild extends StatefulWidget {
  const RegisterChild({super.key});

  @override
  State<RegisterChild> createState() => _RegisterChildState();
}

class _RegisterChildState extends State<RegisterChild> {
  final UserApi userApi = UserApi();
  late List<TextEditingController> controllers;
  late List<FocusNode> focusNodes;
  bool childs = false;

  @override
  void initState() {
    super.initState();
    _checkChild();
    controllers = List.generate(8, (_) => TextEditingController());
    focusNodes = List.generate(8, (_) => FocusNode());
  }

  @override
  void dispose() {
    controllers.forEach((controller) => controller.dispose());
    focusNodes.forEach((node) => node.dispose());
    super.dispose();
  }

  void _checkChild() async {
    final storage = FlutterSecureStorage();
    final descendantInfo = await storage.read(key: 'descendant') ?? "";
    if (descendantInfo != "") {
      childs = true;
    }
  }

  Future<void> showResultDialog(
      BuildContext context, String message, String button,
      {bool barrierDismissible = true}) async {
    return showDialog(
      barrierDismissible: barrierDismissible,
      context: context,
      builder: (context) {
        return AlertDialog(
          contentPadding: EdgeInsets.zero,
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              SizedBox(height: 40),
              Center(
                child: Text(
                  message,
                  style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
                ),
              ),
              SizedBox(height: 40),
              GestureDetector(
                onTap: () {
                  Navigator.of(context).pop(); // 다이얼로그 닫기
                  if (!barrierDismissible) {
                    if (childs) {
                      Navigator.of(context).pop(); // 추가적인 pop
                    } else {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => WaitingPage()),
                      );
                    }
                  }
                },
                child: Container(
                  height: 50,
                  color: Color(0xff15075f),
                  child: Center(
                    child: Text(
                      button,
                      style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.w700,
                          fontSize: 18),
                    ),
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Future<void> _registeChild() async {
    try {
      String registerCode =
          controllers.map((controller) => controller.text).join();
      var result = await userApi.registerChild(registerCode);
      if (result is String && result.startsWith('API Request Failed')) {
        throw Exception(result);
      }
      await showResultDialog(context, '자녀 등록 요청을 보냈습니다.', '확인',
          barrierDismissible: false);
    } catch (e) {
      print('error: $e');

      await showResultDialog(context, '요청에 실패했습니다.\n등록코드를 확인해주세요.', '닫기');
    }
  }

  void _handleFieldTap() {
    for (int i = 0; i < controllers.length; i++) {
      if (controllers[i].text.isEmpty) {
        focusNodes[i].requestFocus();
        break;
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        elevation: 0,
        centerTitle: true,
        leading: IconButton(
          iconSize: 30,
          icon: Icon(Icons.arrow_back, color: Color(0XFF15075F)),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        title: Text(
          '자녀 등록하기',
          style: TextStyle(
            color: Colors.black,
            fontSize: 24,
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
              '자녀 휴대폰에서\n받은 코드를 입력해주세요.',
              style: TextStyle(
                fontSize: 20,
                color: Colors.grey,
              ),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: List.generate(8, (index) {
                return GestureDetector(
                  onTap: () {
                    if (controllers[index].text.isEmpty) {
                      _handleFieldTap();
                    } else {
                      focusNodes[index].requestFocus();
                    }
                  },
                  child: Container(
                    width: 40,
                    height: 40,
                    child: TextField(
                      controller: controllers[index],
                      focusNode: focusNodes[index],
                      maxLength: 1,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(),
                        counterText: "",
                        contentPadding: EdgeInsets.symmetric(vertical: 8),
                      ),
                      textAlign: TextAlign.center,
                      onChanged: (value) {
                        if (value.isEmpty && index > 0) {
                          focusNodes[index - 1].requestFocus();
                        } else if (value.length == 1 && index < 7) {
                          focusNodes[index + 1].requestFocus();
                        }
                      },
                    ),
                  ),
                );
              }),
            ),
            SizedBox(height: 20),
            Text(
              '대소문자를 구분해 입력해주세요',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.w600,
                color: Colors.grey[600],
              ),
            ),
            SizedBox(height: 30),
            ElevatedButton(
              onPressed: _registeChild,
              style: ButtonStyle(
                minimumSize:
                    MaterialStateProperty.all(Size(double.infinity, 36)),
                backgroundColor: MaterialStateProperty.all(Color(0XFF15075F)),
                padding: MaterialStateProperty.all(EdgeInsets.all(10)),
              ),
              child: Text(
                '코드 등록하기',
                style: TextStyle(
                  fontSize: 30,
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
