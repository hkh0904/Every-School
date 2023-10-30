import 'package:everyschool/page/home/home_body.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, this.fcmToken});
  final fcmToken;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        body: Column(
          children: [
            SelectableText(widget.fcmToken),
            const HomeBody(),
          ],
        ),
      ),
    );
  }
}
