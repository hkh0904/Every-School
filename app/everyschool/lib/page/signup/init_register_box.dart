import 'package:everyschool/page/signup/signup_page.dart';
import 'package:flutter/material.dart';

class InitRegisterBox extends StatefulWidget {
  const InitRegisterBox({super.key, this.child});
  final child;

  @override
  State<InitRegisterBox> createState() => _InitRegisterBoxState();
}

class _InitRegisterBoxState extends State<InitRegisterBox> {
  handleNavigator(userType) {
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => SignupPage(usertype: userType)));
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        handleNavigator(widget.child['type']);
      },
      child: Container(
        height: 95,
        margin: EdgeInsets.fromLTRB(0, 0, 0, 30),
        decoration: BoxDecoration(
          color: Colors.grey[50],
          borderRadius: BorderRadius.circular(8),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.2), // 그림자 색상과 불투명도
              spreadRadius: 2, // 그림자 확산 정도
              blurRadius: 5, // 그림자의 흐림 정도
              offset: Offset(1, 3), // 그림자 위치 (가로, 세로)
            ),
          ],
        ),
        child: Padding(
          padding: const EdgeInsets.fromLTRB(20, 0, 20, 0),
          child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
            Align(
                alignment: Alignment.bottomCenter,
                child: Image.asset(widget.child['image'], height: 75)),
            Padding(
              padding: const EdgeInsets.fromLTRB(50, 0, 80, 0),
              child: Text(widget.child['value'],
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.w700)),
            ),
          ]),
        ),
      ),
    );
  }
}
