import 'package:flutter/material.dart';
import 'package:everyschool/page/login/login_form.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  TextEditingController emailAddress = TextEditingController();
  TextEditingController password = TextEditingController();

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
                    child: Container(child: Builder(builder: (context) {
                      return LoginForm(
                          emailAddress: emailAddress, password: password);
                    })))),
          ))),
    );
  }
}
