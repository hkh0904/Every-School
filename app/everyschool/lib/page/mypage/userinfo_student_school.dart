import 'package:everyschool/page/mypage/select_school.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class StudentSchoolInfo extends StatefulWidget {
  const StudentSchoolInfo({super.key, this.userInfo});

  final userInfo;
  @override
  State<StudentSchoolInfo> createState() => _StudentSchoolInfoState();
}

class _StudentSchoolInfoState extends State<StudentSchoolInfo> {
  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);
  TextStyle enabledTitleTextStyle = TextStyle(fontSize: 18);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
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
            widget.userInfo['school']['name'] as String,
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
                      '${widget.userInfo['schoolClass']['grade']}',
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
                      '${widget.userInfo['schoolClass']['classNum']}',
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
      ],
    );
  }
}
