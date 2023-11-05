import 'package:everyschool/page/signup/signup_page.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/page/login/login_form.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  TextEditingController emailAddress = TextEditingController();
  TextEditingController password = TextEditingController();
  final storage = FlutterSecureStorage();

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      child: Scaffold(
          appBar: AppBar(
              title: const Text(
                '로그인',
                style: TextStyle(
                    fontSize: 25,
                    color: Colors.black,
                    fontWeight: FontWeight.w700),
              ),
              elevation: 0.0,
              backgroundColor: Colors.grey[50],
              centerTitle: true,
              toolbarHeight: 65),
          body: Center(
              child: SingleChildScrollView(
            child: Form(
                child: Theme(
                    data: ThemeData(
                        primaryColor: Colors.grey,
                        inputDecorationTheme: const InputDecorationTheme(
                            labelStyle:
                                TextStyle(color: Colors.grey, fontSize: 16.0))),
                    child: Column(
                      children: [
                        Container(
                            padding: EdgeInsets.all(40.0),
                            child: Column(
                              children: [
                                Builder(builder: (context) {
                                  return LoginForm(
                                      emailAddress: emailAddress,
                                      password: password);
                                }),
                                Container(
                                  margin: EdgeInsets.fromLTRB(0, 20, 0, 0),
                                  child: Column(children: [
                                    Padding(
                                        padding: const EdgeInsets.fromLTRB(
                                            0, 10, 0, 10),
                                        child: Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            Padding(
                                              padding: EdgeInsets.symmetric(
                                                  horizontal: 10.0),
                                              child: Container(
                                                height: 1,
                                                width: 50,
                                                color: Colors.black,
                                              ),
                                            ),
                                            Text('소셜로그인'),
                                            Padding(
                                              padding: EdgeInsets.symmetric(
                                                  horizontal: 10.0),
                                              child: Container(
                                                height: 1,
                                                width: 50,
                                                color: Colors.black,
                                              ),
                                            ),
                                          ],
                                        )),
                                    Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        Padding(
                                          padding: const EdgeInsets.all(8.0),
                                          child: Card(
                                            shape: const CircleBorder(),
                                            clipBehavior: Clip.antiAlias,
                                            child: Ink.image(
                                              image: AssetImage(
                                                  'assets/images/login/kakaoLogo.png'),
                                              width: 60,
                                              height: 60,
                                              child: InkWell(
                                                borderRadius:
                                                    const BorderRadius.all(
                                                  Radius.circular(35.0),
                                                ),
                                                onTap: () {},
                                              ),
                                            ),
                                          ),
                                        ),
                                        Padding(
                                          padding: const EdgeInsets.all(8.0),
                                          child: Card(
                                            shape: const CircleBorder(),
                                            clipBehavior: Clip.antiAlias,
                                            child: Ink.image(
                                              image: AssetImage(
                                                  'assets/images/login/naverLogo.png'),
                                              width: 60,
                                              height: 60,
                                              child: InkWell(
                                                borderRadius:
                                                    const BorderRadius.all(
                                                  Radius.circular(35.0),
                                                ),
                                                onTap: () async {},
                                              ),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.fromLTRB(
                                          0, 20, 0, 0),
                                      child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.center,
                                        crossAxisAlignment:
                                            CrossAxisAlignment.center,
                                        children: [
                                          Text(
                                            '아직 회원이 아니신가요?  ',
                                          ),
                                          GestureDetector(
                                            onTap: () {
                                              Navigator.push(
                                                context,
                                                MaterialPageRoute(
                                                    builder: (BuildContext
                                                            context) =>
                                                        SignupPage()),
                                              );
                                            },
                                            child: Text(
                                              '회원가입',
                                              style: TextStyle(
                                                fontWeight: FontWeight.w700,
                                              ),
                                            ),
                                          )
                                        ],
                                      ),
                                    ),
                                    SizedBox(
                                      height: 40.0,
                                      child: Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            GestureDetector(
                                              child: Text('비밀번호 찾기'),
                                              // onTap: () {
                                              //   Navigator.push(
                                              //       context,
                                              //       MaterialPageRoute(
                                              //           builder: (BuildContext
                                              //                   context) =>
                                              //               FindPassword()));
                                              // },
                                            ),
                                          ]),
                                    ),
                                  ]),
                                )
                              ],
                            )),
                      ],
                    ))),
          ))),
    );
  }
}
