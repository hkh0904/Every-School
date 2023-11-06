import 'package:flutter/material.dart';

class MypageUserInfo extends StatefulWidget {
  const MypageUserInfo({super.key});

  @override
  State<MypageUserInfo> createState() => _MypageUserInfoState();
}

class _MypageUserInfoState extends State<MypageUserInfo> {
  var userNum = 1;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: 330,
      decoration: BoxDecoration(
        image: DecorationImage(
            image: AssetImage('assets/images/mypage/top.png'),
            fit: BoxFit.cover),
      ),
      child: Column(children: [
        SizedBox(
          height: 100,
          child: Row(crossAxisAlignment: CrossAxisAlignment.center, children: [
            GestureDetector(
              onTap: () {
                Navigator.pop(context);
              },
              child: Padding(
                padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                child: Icon(Icons.arrow_back, size: 25),
              ),
            ),
            Text(
              '내정보',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
            )
          ]),
        ),
        SizedBox(
          height: MediaQuery.of(context).size.height * 0.07,
        ),
        userImage(),
        SizedBox(
          height: 5,
        ),
        Text('학교이름'),
        Text('유저정보'),
        Text(
          '유저이름',
          style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
        )
      ]),
    );
  }

  Widget userImage() {
    String imageTxt;
    if (userNum == 1) {
      imageTxt = 'assets/images/mypage/student.png';
    } else if (userNum == 2) {
      imageTxt = 'assets/images/mypage/parent.png';
    } else {
      imageTxt = 'assets/images/mypage/teacher.png';
    }
    return Container(
      decoration: BoxDecoration(
          color: Colors.grey[50],
          border: Border.all(color: Color(0xffd9d9d9)),
          borderRadius: BorderRadius.circular(100)),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(100),
        child: Image.asset(
          imageTxt,
          height: 100,
          width: 100,
        ),
      ),
    );
  }
}
