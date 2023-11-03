import 'package:flutter/material.dart';

class MyLikePost extends StatefulWidget {
  const MyLikePost({super.key});

  @override
  State<MyLikePost> createState() => _MyLikePostState();
}

class _MyLikePostState extends State<MyLikePost> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 2,
        backgroundColor: Colors.grey[50],
        leading: BackButton(color: Colors.black),
        centerTitle: true,
        title: Text(
          '내가 좋아요한 글',
          style: TextStyle(
              color: Colors.black, fontSize: 18, fontWeight: FontWeight.w700),
        ),
      ),
      body: Container(
          margin: EdgeInsets.fromLTRB(30, 30, 30, 10),
          child: Column(
            children: const [Text('하하')],
          )),
    );
  }
}
