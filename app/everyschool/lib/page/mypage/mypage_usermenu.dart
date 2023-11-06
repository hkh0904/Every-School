import 'package:everyschool/page/mypage/my_like_post.dart';
import 'package:everyschool/page/mypage/my_write_list.dart';
import 'package:flutter/material.dart';

class MypageUsermenu extends StatefulWidget {
  const MypageUsermenu({super.key});

  @override
  State<MypageUsermenu> createState() => _MypageUsermenuState();
}

class _MypageUsermenuState extends State<MypageUsermenu> {
  int userNum = 1;
  var menulist = [
    ['작성한 댓글 보기', '작성한 글 보기'],
    ['상담 신청 목록', '가정통신문'],
    ['상담 목록', '신고 목록'],
  ];

  var imagelist = [
    ['consulting', 'search'],
    ['consulting', 'menu'],
    ['consulting', 'menu']
  ];

  var perPagelist = [
    [MyLikePost(), MyWriteList()],
    [MyLikePost(), MyLikePost()],
    [MyLikePost(), MyLikePost()],
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(0, 35, 0, 10),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: List.generate(
          menulist[userNum - 1].length,
          (index) {
            return GestureDetector(
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => perPagelist[userNum - 1][index]),
                );
              },
              child: Column(
                children: [
                  Image.asset(
                    'assets/images/mypage/${imagelist[userNum - 1][index]}.png',
                    width: 40,
                    height: 40,
                  ),
                  SizedBox(
                    height: 5,
                  ),
                  Text(menulist[userNum - 1][index],
                      style: TextStyle(fontWeight: FontWeight.w600)),
                ],
              ),
            );
          },
        ),
      ),
    );
  }
}
