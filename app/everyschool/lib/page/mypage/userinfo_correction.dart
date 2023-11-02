import 'package:flutter/material.dart';

class UserInfoCorrection extends StatefulWidget {
  const UserInfoCorrection({super.key});

  @override
  State<UserInfoCorrection> createState() => _UserInfoCorrectionState();
}

class _UserInfoCorrectionState extends State<UserInfoCorrection> {
  var userInfo = {
    'id': 'abcd',
    'name': '뭐라는거야',
    'school': '그러게요',
    '학년': 3,
    '반': 1
  };

  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 2,
        backgroundColor: Colors.grey[50],
        leading: BackButton(color: Colors.black),
        centerTitle: true,
        title: Text(
          '개인정보 수정',
          style: TextStyle(
              color: Colors.black, fontSize: 18, fontWeight: FontWeight.w700),
        ),
      ),
      body: Container(
        margin: EdgeInsets.fromLTRB(30, 30, 30, 10),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text('아이디', style: unabledTitleTextStyle),
          SizedBox(
            height: 5,
          ),
          Container(
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.fromLTRB(0, 5, 0, 10),
            decoration: BoxDecoration(
                border: Border(
                    bottom: BorderSide(width: 0.5, color: Color(0xff868E96)))),
            margin: EdgeInsets.fromLTRB(0, 5, 0, 0),
            child: Text(
              userInfo['id'] as String,
              style: unabledTextStyle,
            ),
          ),
          SizedBox(
            height: 30,
          ),
          Text('이름', style: unabledTitleTextStyle),
          Container(
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.fromLTRB(0, 5, 0, 10),
            decoration: BoxDecoration(
                border: Border(
                    bottom: BorderSide(width: 0.5, color: Color(0xff868E96)))),
            margin: EdgeInsets.fromLTRB(0, 5, 0, 0),
            child: Text(
              userInfo['name'] as String,
              style: unabledTextStyle,
            ),
          ),
          SizedBox(
            height: 5,
          ),
          SizedBox(
            height: 20,
          ),
          Text('생년월일', style: myTextStyle),
          SizedBox(
            height: 20,
          ),
          Text('학교', style: myTextStyle),
          SizedBox(
            height: 20,
          ),
          Text('학년/반', style: myTextStyle),
          SizedBox(
            height: 10,
          ),
          Text('자녀 정보 수정', style: myTextStyle),
          SizedBox(
            height: 20,
          ),
        ]),
      ),
    );
  }
}
