import 'package:everyschool/page/mypage/userinfo_parent_child.dart';
import 'package:everyschool/page/mypage/userinfo_student_school.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/page/mypage/select_school.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class UserInfoCorrection extends StatefulWidget {
  const UserInfoCorrection({super.key});

  @override
  State<UserInfoCorrection> createState() => _UserInfoCorrectionState();
}

class _UserInfoCorrectionState extends State<UserInfoCorrection> {
  final storage = FlutterSecureStorage();
  Map<String, dynamic> userInfo = {};
  String? userType;

  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);
  TextStyle enabledTitleTextStyle = TextStyle(fontSize: 18);

  _getUserInfo() async {
    final info = await context.read<UserStore>().userInfo;
    final type = await storage.read(key: 'usertype') ?? "";
    setState(() {
      userInfo = info;
      userType = type;
    });
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    _getUserInfo();
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
          '개인정보 조회',
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
              userInfo['email'] as String,
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
          if (userType == '1002')
            ParentChildInfo(userInfo: userInfo)
          else if (userType == '1001')
            StudentSchoolInfo(userInfo: userInfo)
          else
            Container(),
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
