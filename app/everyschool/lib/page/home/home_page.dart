import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/home/home_body.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final storage = FlutterSecureStorage();
  String? token;

  void getToken() async {
    final storage = FlutterSecureStorage();
    token = await storage.read(key: 'token') ?? "";
    UserApi().getUserInfo(token);
  }

  @override
  void initState() {
    super.initState();
    getToken();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(body: const HomeBody()),
    );
  }
}
