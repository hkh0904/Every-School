import 'package:everyschool/page/signup/add_info_form.dart';
import 'package:flutter/material.dart';

class SignupForm extends StatefulWidget {
  const SignupForm(
      {super.key, this.emailAddress, this.password, this.passwordCheck});

  final emailAddress;
  final password;
  final passwordCheck;

  @override
  State<SignupForm> createState() => _SignupFormState();
}

class _SignupFormState extends State<SignupForm> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      child: Column(
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              controller: widget.emailAddress,
              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(
                    Icons.alternate_email_rounded,
                  ),
                  border: OutlineInputBorder(borderSide: BorderSide()),
                  labelText: '이메일',
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.emailAddress,
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              controller: widget.password,
              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(Icons.vpn_key_outlined),
                  border: OutlineInputBorder(),
                  labelText: '비밀번호',
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.visiblePassword,
              obscureText: true, // 비밀번호 안보이도록 하는 것
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 50),
            child: TextField(
              controller: widget.passwordCheck,
              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(Icons.vpn_key_outlined),
                  border: OutlineInputBorder(),
                  labelText: '비밀번호 확인',
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.visiblePassword,
              obscureText: true, // 비밀번호 안보이도록 하는 것
            ),
          ),
          SizedBox(
            height: 60,
            child: Padding(
              padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
              child: ButtonTheme(
                  child: TextButton(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (BuildContext context) =>
                                    AddInfoForm()));
                      },
                      style: ButtonStyle(
                          backgroundColor:
                              MaterialStatePropertyAll(Color(0xff15075F))),
                      child: SizedBox(
                        height: 40,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: const [
                            Icon(
                              Icons.arrow_forward,
                              color: Colors.white,
                            ),
                            // Text(
                            //   '다음',
                            //   style: TextStyle(
                            //     color: Colors.white,
                            //     fontWeight: FontWeight.w700,
                            //   ),
                            // ),
                          ],
                        ),
                      ))),
            ),
          ),
        ],
      ),
    );
  }
}
