import 'package:everyschool/home/homebody.dart';
import 'package:flutter/material.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        elevation: 0,
        centerTitle: true,
        title: Image.asset('assets/images/home/title.png',
          height: 50),
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
