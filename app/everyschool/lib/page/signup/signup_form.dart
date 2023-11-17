import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/signup/add_info_form.dart';
import 'package:everyschool/page/signup/signuptimer.dart';
import 'package:flutter/material.dart';
import 'dart:convert';

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
  TextEditingController authNumber = TextEditingController();
  bool emailCheck = false;
  bool emailCodeCheck = false;
  bool passwordCheck = false;
  bool samepasswordCheck = false;
  bool emailCheckButton = false;

  bool validateloading = true;
  bool timeron = false;

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
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Expanded(
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
                      contentPadding: EdgeInsets.symmetric(
                          vertical: 17.0, horizontal: 10.0),
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
                SizedBox(
                    height: 53,
                    child: Padding(
                      padding: const EdgeInsets.fromLTRB(10, 0, 0, 0),
                      child: TextButton(
                        style: ButtonStyle(
                          minimumSize: MaterialStateProperty.all(Size(75, 0)),
                          backgroundColor: emailCheck && validateloading
                              ? MaterialStateProperty.all<Color>(
                                  Color(0xff15075F))
                              : MaterialStateProperty.all<Color>(Colors.grey),
                        ),
                        onPressed: emailCheck && validateloading
                            ? () async {
                                setState(() {
                                  validateloading = false;
                                  timeron = false;
                                  emailCheckButton = true;
                                });

                                Future.delayed(Duration(seconds: 7), () {
                                  // 이곳에 실행하고자 하는 코드를 작성
                                  setState(() {
                                    validateloading = true;
                                  });
                                });

                                final emailsend = await UserApi()
                                    .authEmail(widget.emailAddress.text);

                                if (emailsend.toString() == 'null') {
                                  showDialog(
                                      context: context,
                                      builder: ((context) {
                                        return AlertDialog(
                                          actions: <Widget>[
                                            TextButton(
                                                onPressed: () {
                                                  Navigator.of(context).pop();
                                                },
                                                child: Text('닫기'))
                                          ],
                                          content: SingleChildScrollView(
                                            child: Text('입력하신 이메일을\n확인하세요.'),
                                          ),
                                        );
                                      }));
                                  setState(() {
                                    timeron = true;
                                  });
                                  Future.delayed(Duration(seconds: 180), () {
                                    // 이곳에 실행하고자 하는 코드를 작성
                                    setState(() {
                                      timeron = false;
                                    });
                                  });
                                }
                              }
                            : null,
                        child: Text(
                          '인증 하기',
                          textAlign: TextAlign.center,
                          style: TextStyle(color: Colors.white),
                        ),
                      ),
                    )),
              ],
            ),
          ),
          emailCheckButton
              ? Padding(
                  padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Expanded(
                        child: Stack(
                          alignment: Alignment.center,
                          children: [
                            TextField(
                              controller: authNumber,
                              cursorColor: Color(0xff15075F),
                              enabled: emailCodeCheck ? false : true,
                              decoration: InputDecoration(
                                contentPadding: EdgeInsets.symmetric(
                                    vertical: 17.0, horizontal: 10.0),
                                focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(
                                        width: 1.5, color: Color(0xff15075F))),
                                border: OutlineInputBorder(
                                    borderSide: BorderSide()),
                                labelText: '인증 번호',
                                focusColor: Color(0xff15075F),
                              ),
                              keyboardType: TextInputType.emailAddress,
                            ),
                            Positioned(
                              right: BorderSide.strokeAlignCenter + 15,
                              child: timeron ? SignupTimer() : Text(''),
                            ),
                          ],
                        ),
                      ),
                      SizedBox(
                          height: 53,
                          child: Padding(
                            padding: const EdgeInsets.fromLTRB(10, 0, 0, 0),
                            child: TextButton(
                              style: ButtonStyle(
                                  minimumSize:
                                      MaterialStateProperty.all(Size(75, 0)),
                                  backgroundColor:
                                      MaterialStateProperty.all<Color>(
                                          Color(0xff15075F))),
                              onPressed: emailCodeCheck
                                  ? null
                                  : () async {
                                      final check = await UserApi()
                                          .checkAuthNumber(
                                              widget.emailAddress.text,
                                              authNumber.text);

                                      if (check['code'] == 200) {
                                        setState(() {
                                          emailCodeCheck = true;
                                          validateloading = false;
                                          timeron = false;
                                        });
                                      } else if (check['code'] == 400) {
                                        if (check['message'] ==
                                            '유효 시간이 만료되었습니다.') {
                                          showDialog(
                                              context: context,
                                              builder: ((context) {
                                                return AlertDialog(
                                                  actions: <Widget>[
                                                    TextButton(
                                                        onPressed: () {
                                                          Navigator.of(context)
                                                              .pop();
                                                        },
                                                        child: Text('닫기'))
                                                  ],
                                                  content:
                                                      SingleChildScrollView(
                                                    child: Text(
                                                        '${check['message']}'),
                                                  ),
                                                );
                                              }));
                                          setState(() {
                                            emailCodeCheck = false;
                                            validateloading = true;
                                            timeron = false;
                                            authNumber.text = '';
                                          });
                                        } else if (check['message'] ==
                                            "인증 번호가 일치하지 않습니다.") {
                                          showDialog(
                                              context: context,
                                              builder: ((context) {
                                                return AlertDialog(
                                                  actions: <Widget>[
                                                    TextButton(
                                                        onPressed: () {
                                                          Navigator.of(context)
                                                              .pop();
                                                        },
                                                        child: Text('닫기'))
                                                  ],
                                                  content:
                                                      SingleChildScrollView(
                                                    child: Text(
                                                        '${check['message']}'),
                                                  ),
                                                );
                                              }));
                                          setState(() {
                                            emailCodeCheck = false;
                                            validateloading = true;
                                            timeron = false;
                                            authNumber.text = '';
                                          });
                                        } else if (check['message'] ==
                                            "이미 사용 중인 이메일입니다.") {
                                          showDialog(
                                              context: context,
                                              builder: ((context) {
                                                return AlertDialog(
                                                  actions: <Widget>[
                                                    TextButton(
                                                        onPressed: () {
                                                          Navigator.of(context)
                                                              .pop();
                                                        },
                                                        child: Text('닫기'))
                                                  ],
                                                  content:
                                                      SingleChildScrollView(
                                                    child: Text(
                                                        '${check['message']}'),
                                                  ),
                                                );
                                              }));
                                          setState(() {
                                            emailCodeCheck = false;
                                            emailCheckButton = false;
                                            validateloading = true;
                                            timeron = false;
                                            authNumber.text = '';
                                            widget.emailAddress.text = '';
                                          });
                                        }
                                      }
                                    },
                              child: emailCodeCheck
                                  ? Icon(
                                      Icons.check,
                                      color: Colors.white,
                                    )
                                  : Text(
                                      '인증 확인',
                                      style: TextStyle(color: Colors.white),
                                    ),
                            ),
                          )),
                    ],
                  ),
                )
              : SizedBox(),
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
