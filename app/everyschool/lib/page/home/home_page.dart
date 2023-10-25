import 'package:everyschool/page/home/home_body.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        elevation: 0,
        centerTitle: true,
        title: Image.asset('assets/images/home/title.png', height: 50),
        actions: const [
          Padding(
            padding: EdgeInsets.fromLTRB(5, 0, 0, 0),
            child: Icon(Icons.notifications_none),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(5, 0, 10, 0),
            child: Icon(Icons.settings),
          )
        ],
        actionsIconTheme: IconThemeData(
          color: Colors.black,
        ),
      ),
      body: HomeBody(),
    );
  }
}
