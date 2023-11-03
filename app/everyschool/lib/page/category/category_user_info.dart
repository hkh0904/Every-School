import 'package:everyschool/page/mypage/mypage.dart';
import 'package:flutter/material.dart';

class CategoryUserInfo extends StatefulWidget {
  const CategoryUserInfo({super.key});

  @override
  State<CategoryUserInfo> createState() => _CategoryUserInfoState();
}

class _CategoryUserInfoState extends State<CategoryUserInfo> {
  // 학생 학부모 선생님
  var userNum = 2;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        children: [
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.05,
          ),
          userImage(),
          SizedBox(
            height: 10,
          ),
          Text(
            '환영합니다. name님!',
            style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
          ),
          SizedBox(
            height: 15,
          ),
          GestureDetector(
            onTap: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const MyPage()));
            },
            child: Container(
                padding: EdgeInsets.fromLTRB(15, 10, 15, 10),
                decoration: BoxDecoration(
                    border: Border.all(color: Color(0xffd9d9d9), width: 0.5),
                    borderRadius: BorderRadius.circular(20),
                    gradient: LinearGradient(
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                      colors: [Color(0xff15075F), Color(0xff594AAA)],
                    )),
                child: Text(
                  '개인정보 수정',
                  style: TextStyle(
                      fontWeight: FontWeight.w600, color: Colors.white),
                )),
          ),
          SizedBox(
            height: 50,
          ),
        ],
      ),
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
