import 'package:everyschool/page/category/category_report_consult.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/consulting/consulting_reservation_page.dart';
import 'package:everyschool/page/messenger/call/call_history.dart';
import 'package:everyschool/page/messenger/chat/connect.dart';
import 'package:everyschool/page/messenger/messenger_page.dart';
import 'package:everyschool/page/mypage/change_password.dart';
import 'package:everyschool/page/mypage/mute_time_set.dart';
import 'package:everyschool/page/mypage/my_comment_post.dart';
import 'package:everyschool/page/mypage/my_like_post.dart';
import 'package:everyschool/page/mypage/my_write_list.dart';
import 'package:everyschool/page/mypage/register_child.dart';
import 'package:everyschool/page/mypage/register_parents.dart';
import 'package:everyschool/page/mypage/userinfo_correction.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/page/report/report_page.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:everyschool/page/community/community_page.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/community/school_schedule.dart';

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
    ['연락처', '통화목록', '채팅목록']
  ];

  var communityList = [
    ['학사일정', '학사공지', '가정통신문', '자유게시판'],
    ['학사일정', '학사공지', '가정통신문'],
    ['학사일정', '학사공지', '가정통신문']
  ];

  var mypageList = [
    ['작성한 글 보기', '작성한 댓글 보기', '좋아요한 글', '개인정보 조회', '비밀번호 변경', '학부모 등록하기'],
    ['자녀 등록하기', '개인정보 조회', '비밀번호 변경'],
    ['개인정보 조회', '비밀번호 변경', '방해금지 설정']
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

  var messengerListLink = [
    [MessengerPage(indexNum: 1), MessengerPage(indexNum: 0)],
    [MessengerPage(indexNum: 1), MessengerPage(indexNum: 0)],
    [
      MessengerPage(indexNum: 2),
      MessengerPage(indexNum: 1),
      MessengerPage(indexNum: 0)
    ]
  ];

  var communityListLink = [
    [
      SchoolSchedule(),
      PostlistPage(pageTitle: '학사 공지'),
      PostlistPage(pageTitle: '가정통신문'),
      PostlistPage(pageTitle: '자유게시판'),
    ],
    [
      SchoolSchedule(),
      PostlistPage(pageTitle: '학사 공지'),
      PostlistPage(pageTitle: '가정통신문'),
    ],
    [
      SchoolSchedule(),
      PostlistPage(pageTitle: '학사 공지'),
      PostlistPage(pageTitle: '가정통신문'),
    ]
  ];

  var myPageListLink = [
    [
      MyWriteList(),
      MyCommentPost(),
      MyLikePost(),
      UserInfoCorrection(),
      ChangePassword(),
      RegisterParents()
    ],
    [RegisterChild(), UserInfoCorrection(), ChangePassword()],
    [UserInfoCorrection(), ChangePassword(), MuteTimeSet()]
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
                      categoryListLink: messengerListLink,
                      titleTxt: _titleTxt2),
                  SizedBox(
                    height: 30,
                  ),
                  CategoryRepnCslt(
                      userNum: userNum,
                      categoryList: communityList,
                      categoryListLink: communityListLink,
                      titleTxt: _titleTxt3),
                  SizedBox(
                    height: 30,
                  ),
                  CategoryRepnCslt(
                      userNum: userNum,
                      categoryList: mypageList,
                      categoryListLink: myPageListLink,
                      titleTxt: _titleTxt4),
                ],
              ),
            );
          } else if (snapshot.hasError) {
            return Container(
              height: 800,
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
