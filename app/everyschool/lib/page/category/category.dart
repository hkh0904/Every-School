import 'package:everyschool/page/category/category_report_consult.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/page/report/report_page.dart';
import 'package:everyschool/page/report_consulting/consulting_list_teacher.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class Category extends StatefulWidget {
  const Category({super.key});

  @override
  State<Category> createState() => _CategoryState();
}

class _CategoryState extends State<Category> {
  int? userNum;

  final storage = FlutterSecureStorage();

  getuserType() async {
    var userType = await storage.read(key: 'usertype') ?? "";
    userNum = int.parse(userType.substring(userType.length - 1));
    return int.parse(userType.substring(userType.length - 1));
  }

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

  var repncsltListLink = [
    [ReportPage(), ReportListPage()],
    [ConsultingReservation(), ConsultingListPage()],
    [
      ReportConsultingPage(index: 0),
      ReportPage(),
      ReportConsultingPage(index: 1),
      ReportConsultingPage(index: 2)
    ]
  ];

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            Widget _titleTxt() {
              String titleTxt;
              if (snapshot.data == 1001) {
                titleTxt = '신고';
              } else if (snapshot.data == 1002) {
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
                      userNum: snapshot.data,
                      categoryList: repncsltList,
                      categoryListLink: repncsltListLink,
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
                      categoryList: mypageList,
                      titleTxt: _titleTxt4),
                ],
              ),
            );
          } else if (snapshot.hasError) {
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'Error: ${snapshot.error}',
                style: TextStyle(fontSize: 15),
              ),
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
