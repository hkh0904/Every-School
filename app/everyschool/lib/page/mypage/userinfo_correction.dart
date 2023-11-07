import 'package:flutter/material.dart';
import 'package:everyschool/page/mypage/select_school.dart';

class UserInfoCorrection extends StatefulWidget {
  const UserInfoCorrection({super.key});

  @override
  State<UserInfoCorrection> createState() => _UserInfoCorrectionState();
}

class _UserInfoCorrectionState extends State<UserInfoCorrection> {
  var userInfo = {
    'id': 'abcd',
    'name': '뭐라는거야',
    'birth': '2008.01.01',
    'school': '그러게요',
    'grade': 3,
    'class': 1
  };

  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);
  TextStyle enabledTitleTextStyle = TextStyle(fontSize: 18);

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
            height: 3,
          ),
          Text(
            '아이디는 수정이 불가능합니다.',
            style: TextStyle(color: Color(0xff868E96)),
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
            height: 3,
          ),
          Text(
            '이름은 수정이 불가능합니다.',
            style: TextStyle(color: Color(0xff868E96)),
          ),
          SizedBox(
            height: 30,
          ),
          Text('생년월일', style: unabledTitleTextStyle),
          Container(
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.fromLTRB(0, 5, 0, 10),
            decoration: BoxDecoration(
                border: Border(
                    bottom: BorderSide(width: 0.5, color: Color(0xff868E96)))),
            margin: EdgeInsets.fromLTRB(0, 5, 0, 0),
            child: Text(
              userInfo['birth'] as String,
              style: unabledTextStyle,
            ),
          ),
          SizedBox(
            height: 3,
          ),
          Text(
            '생년월일은 수정이 불가능합니다.',
            style: TextStyle(color: Color(0xff868E96)),
          ),
          SizedBox(
            height: 30,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.end,
            children: [
              Text('학교', style: myTextStyle),
              GestureDetector(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => SelectSchool()),
                  );
                },
                child: Text('학교 정보 설정'),
              )
            ],
          ),
          SizedBox(
            height: 3,
          ),
          Container(
            width: MediaQuery.of(context).size.width,
            padding: EdgeInsets.fromLTRB(10, 15, 10, 15),
            decoration: BoxDecoration(
                border: Border.all(color: Color(0xff868E96)),
                borderRadius: BorderRadius.circular(8)),
            margin: EdgeInsets.fromLTRB(0, 5, 0, 0),
            child: Text(
              userInfo['school'] as String,
              style: enabledTitleTextStyle,
            ),
          ),
          SizedBox(
            height: 30,
          ),
          Text('학년 / 반', style: myTextStyle),
          SizedBox(
            height: 3,
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  padding: EdgeInsets.fromLTRB(25, 15, 15, 15),
                  decoration: BoxDecoration(
                      border: Border.all(color: Color(0xff868E96)),
                      borderRadius: BorderRadius.circular(8)),
                  margin: EdgeInsets.fromLTRB(0, 5, 5, 0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '${userInfo['grade']}',
                        style: myTextStyle,
                      ),
                      Text(
                        '학년',
                        style: enabledTitleTextStyle,
                      ),
                    ],
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  padding: EdgeInsets.fromLTRB(25, 15, 15, 15),
                  decoration: BoxDecoration(
                      border: Border.all(color: Color(0xff868E96)),
                      borderRadius: BorderRadius.circular(8)),
                  margin: EdgeInsets.fromLTRB(5, 5, 0, 0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        '${userInfo['class']}',
                        style: myTextStyle,
                      ),
                      Text(
                        '반',
                        style: enabledTitleTextStyle,
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          SizedBox(
            height: 40,
          ),
          Center(
              child: Text(
            '회원탈퇴',
            style: TextStyle(color: Color(0xff868E96), fontSize: 16),
          ))
        ]),
      ),
    );
  }
}
