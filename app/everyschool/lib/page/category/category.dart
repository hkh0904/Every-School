import 'package:everyschool/page/category/category_report_consult.dart';
import 'package:flutter/material.dart';

class Category extends StatefulWidget {
  const Category({super.key});

  @override
  State<Category> createState() => _CategoryState();
}

class _CategoryState extends State<Category> {
  int userNum = 2;
  var repncsltList = [
    ['신고하기', '신고내역'],
    ['상담신청', '상담내역'],
    ['상담내역', '신고하기', '신고내역', '받은 신고내역']
  ];

  var chattingList = [
    ['통화목록', '채팅목록'],
    ['통화목록', '채팅목록'],
    ['연락처', '전화내역', '메세지 내역']
  ];

  var communityList = [
    ['커뮤니티 메인', '학사일정', '학사공지', '급식메뉴', '가정통신문', '자유게시판', '인기게시판', 'HOT게시판'],
    ['커뮤니티 메인', '학사일정', '학사공지', '급식메뉴', '가정통신문'],
    ['커뮤니티 메인', '학사일정', '학사공지', '급식메뉴', '가정통신문']
  ];

  var mypageList = [
    ['작성한 글 보기', '작성한 댓글 보기', '스크랩한 글', '개인정보 수정', '비밀번호 변경'],
    ['스크랩한 글', '개인정보 수정', '비밀번호 변경'],
    ['공지사항 관리', '비밀번호 변경']
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(30, 0, 30, 10),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            '카테고리 전체보기',
            style: TextStyle(fontWeight: FontWeight.w900, fontSize: 24),
          ),
          SizedBox(
            height: 20,
          ),
          CategoryRepnCslt(
              userNum: userNum,
              categoryList: repncsltList,
              titleTxt: _titleTxt),
          SizedBox(
            height: 30,
          ),
          CategoryRepnCslt(
              userNum: userNum,
              categoryList: chattingList,
              titleTxt: _titleTxt2),
          SizedBox(
            height: 30,
          ),
          CategoryRepnCslt(
              userNum: userNum,
              categoryList: communityList,
              titleTxt: _titleTxt3),
          SizedBox(
            height: 30,
          ),
          CategoryRepnCslt(
              userNum: userNum,
              categoryList: communityList,
              titleTxt: _titleTxt4),
        ],
      ),
    );
  }

  Widget _titleTxt() {
    String titleTxt;
    if (userNum == 1) {
      titleTxt = '신고';
    } else if (userNum == 2) {
      titleTxt = '상담';
    } else {
      titleTxt = '상담 및 신고';
    }
    return Text(
      titleTxt,
      style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
    );
  }

  Widget _titleTxt2() {
    String titleTxt = '메신저';
    return Text(
      titleTxt,
      style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
    );
  }

  Widget _titleTxt3() {
    String titleTxt = '커뮤니티';
    return Text(
      titleTxt,
      style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
    );
  }

  Widget _titleTxt4() {
    String titleTxt = '마이페이지';
    return Text(
      titleTxt,
      style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
    );
  }
}
