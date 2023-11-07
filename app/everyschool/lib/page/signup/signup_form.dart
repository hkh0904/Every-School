import 'package:everyschool/page/signup/add_info_form.dart';
import 'package:flutter/material.dart';

class SignupForm extends StatefulWidget {
  const SignupForm(
      {super.key,
      this.emailAddress,
      this.password,
      this.passwordCheck,
      this.usertype});

  final emailAddress;
  final password;
  final passwordCheck;
  final usertype;

  @override
  State<SignupForm> createState() => _SignupFormState();
}

class _SignupFormState extends State<SignupForm> {
  bool emailCheck = false;
  bool emailCodeCheck = false;
  bool passwordCheck = false;
  bool samepasswordCheck = false;
  bool emailCheckButton = false;

  String? emailError;
  String? passwordError;
  String? samepasswordError;
  String emailMessage = '이메일 형식으로 입력해주세요';
  String passwordMessage = '특수문자, 숫자, 영어가 필수로 1개씩 있어야 합니다.';
  String samepasswordMessage = '비밀번호와 다릅니다.';

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      child: Column(
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              cursorColor: Color(0xff15075F),
              onChanged: (value) {
                if (!RegExp(
                        r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$")
                    .hasMatch(value)) {
                  setState(() {
                    emailError = emailMessage;
                    emailCheck = false;
                  });
                } else {
                  setState(() {
                    emailError = null; // 에러 없음
                    emailCheck = true;
                  });
                }
              },
              controller: widget.emailAddress,
              enabled: emailCheckButton ? false : true,
              autofocus: true,
              decoration: InputDecoration(
                contentPadding:
                    EdgeInsets.symmetric(vertical: 17.0, horizontal: 10.0),
                focusedBorder: OutlineInputBorder(
                    borderSide:
                        BorderSide(width: 1.5, color: Color(0xff15075F))),
                prefixIconColor: Color(0xff15075F),
                prefixIcon: Icon(
                  Icons.alternate_email_rounded,
                ),
                suffixIcon: Icon(
                  RegExp(r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$")
                          .hasMatch(widget.emailAddress.text)
                      ? Icons.check_circle_rounded
                      : Icons.priority_high_rounded,
                  color:
                      RegExp(r"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$")
                              .hasMatch(widget.emailAddress.text)
                          ? Color(0xff15075F)
                          : Colors.red,
                ),
                border: OutlineInputBorder(borderSide: BorderSide()),
                labelText: '이메일',
                errorText: emailError,
                errorStyle: TextStyle(height: 1),
                focusColor: Color(0xff15075F),
              ),
              keyboardType: TextInputType.emailAddress,
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: TextField(
              cursorColor: Color(0xff15075F),

              onChanged: (value) {
                if (!RegExp(
                        r"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$")
                    .hasMatch(value)) {
                  setState(() {
                    passwordError = passwordMessage;
                    passwordCheck = false;
                    if (widget.password.text == widget.passwordCheck.text) {
                      setState(() {
                        samepasswordError = null;
                        samepasswordCheck = true;
                      });
                    } else {
                      samepasswordError = samepasswordMessage;
                      samepasswordCheck = false;
                    }
                  });
                } else {
                  setState(() {
                    passwordError = null; // 에러 없음
                    passwordCheck = true;
                  });
                  if (widget.password.text == widget.passwordCheck.text) {
                    setState(() {
                      samepasswordError = null;
                      samepasswordCheck = true;
                    });
                  } else {
                    samepasswordError = samepasswordMessage;
                    samepasswordCheck = false;
                  }
                }
              },

              controller: widget.password,
              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 17.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1.5, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(Icons.vpn_key_outlined),
                  border: OutlineInputBorder(),
                  suffixIcon: Icon(
                      RegExp(r"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$")
                              .hasMatch(widget.password.text)
                          ? Icons.check_circle_rounded
                          : Icons.priority_high_rounded,
                      color:
                          RegExp(r"^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$")
                                  .hasMatch(widget.password.text)
                              ? Color(0xff15075F)
                              : Colors.red),
                  labelText: '비밀번호',
                  errorText: passwordError,
                  errorStyle: TextStyle(height: 1),
                  focusColor: Color(0xff15075F)),
              keyboardType: TextInputType.visiblePassword,
              obscureText: true, // 비밀번호 안보이도록 하는 것/ 비밀번호 안보이도록 하는 것
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(0, 0, 0, 50),
            child: TextField(
              cursorColor: Color(0xff15075F),

              onChanged: (value) {
                if (widget.password.text != widget.passwordCheck.text) {
                  setState(() {
                    samepasswordError = samepasswordMessage;
                    samepasswordCheck = false;
                  });
                } else {
                  setState(() {
                    samepasswordError = null; // 에러 없음
                    samepasswordCheck = true;
                  });
                }
              },
              controller: widget.passwordCheck,

              decoration: InputDecoration(
                  contentPadding:
                      EdgeInsets.symmetric(vertical: 17.0, horizontal: 10.0),
                  focusedBorder: OutlineInputBorder(
                      borderSide:
                          BorderSide(width: 1.5, color: Color(0xff15075F))),
                  prefixIconColor: Color(0xff15075F),
                  prefixIcon: Icon(Icons.key),
                  suffixIcon: Icon(
                    (widget.password.text == widget.passwordCheck.text &&
                            widget.passwordCheck.text != '')
                        ? Icons.check_circle_rounded
                        : Icons.priority_high_rounded,
                    color: (widget.password.text == widget.passwordCheck.text &&
                            widget.passwordCheck.text != '')
                        ? Color(0xff15075F)
                        : Colors.red,
                  ),
                  border: OutlineInputBorder(),
                  labelText: '비밀번호 확인',
                  errorText: samepasswordError,
                  errorStyle: TextStyle(height: 1),
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
                      onPressed:
                          emailCheck && passwordCheck && samepasswordCheck
                              ? () {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (BuildContext context) =>
                                              AddInfoForm(
                                                  email: widget.emailAddress,
                                                  password: widget.password,
                                                  type: widget.usertype)));
                                }
                              : null,
                      style: ButtonStyle(
                          backgroundColor:
                              emailCheck && passwordCheck && samepasswordCheck
                                  ? MaterialStatePropertyAll(Color(0xff15075F))
                                  : MaterialStatePropertyAll(Colors.grey)),
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
