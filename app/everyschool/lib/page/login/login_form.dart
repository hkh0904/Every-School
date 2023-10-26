import 'package:flutter/material.dart';

class LoginForm extends StatefulWidget {
  const LoginForm({super.key, this.emailAddress, this.password});

  final emailAddress;
  final password;

  @override
  State<LoginForm> createState() => _LoginFormState();
}

class _LoginFormState extends State<LoginForm> {
  @override
  Widget build(BuildContext context) {
    return const SizedBox(
      child: Column(
        children: [
          TextField(
            decoration: InputDecoration(
                contentPadding:
                    EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                focusedBorder: OutlineInputBorder(
                    borderSide: BorderSide(width: 1, color: Color(0xffA1CBA1))),
                prefixIconColor: Color(0xffA1CBA1),
                prefixIcon: Icon(
                  Icons.alternate_email_rounded,
                ),
                border: OutlineInputBorder(borderSide: BorderSide()),
                labelText: '이메일',
                focusColor: Color(0xffA1CBA1)),
            keyboardType: TextInputType.emailAddress,
          ),
          TextField(
            decoration: InputDecoration(
                contentPadding:
                    EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                focusedBorder: OutlineInputBorder(
                    borderSide: BorderSide(width: 1, color: Color(0xffA1CBA1))),
                prefixIconColor: Color(0xffA1CBA1),
                prefixIcon: Icon(Icons.vpn_key_outlined),
                border: OutlineInputBorder(),
                labelText: '비밀번호',
                focusColor: Color(0xffA1CBA1)),
            keyboardType: TextInputType.visiblePassword,
            obscureText: true, // 비밀번호 안보이도록 하는 것
          ),
        ],
      ),
    );
  }
}
