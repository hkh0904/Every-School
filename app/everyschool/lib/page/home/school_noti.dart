import 'dart:convert';

import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class SchoolNoti extends StatefulWidget {
  const SchoolNoti({super.key});

  @override
  State<SchoolNoti> createState() => _SchoolNotiState();
}

class _SchoolNotiState extends State<SchoolNoti> {
  final storage = FlutterSecureStorage();

  getPost() async {
    var userType = await storage.read(key: 'usertype');
    var schoolId;

    if (userType == '1002') {
      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);

      schoolId = selectDescendant['school']['schoolId'];
    } else {
      var token = await storage.read(key: 'token') ?? "";
      final userinfo = await UserApi().getUserInfo(token);

      schoolId = userinfo['school']['schoolId'];
    }
    final year = context.read<UserStore>().year;
    var response = CommunityApi().getNewNoticeList(year, schoolId);
    return response;
  }

  String extractDate(String input) {
    // 문자열을 DateTime으로 파싱
    DateTime dateTime = DateTime.parse(input);

    // 날짜 부분만 가져와서 포맷팅
    String formattedDate = DateFormat('yyyy-MM-dd').format(dateTime);

    return formattedDate;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getPost(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Container(
              decoration: BoxDecoration(
                  color: Color(0xffEAF7FF),
                  borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(20),
                      topRight: Radius.circular(20))),
              child: Container(
                // padding: EdgeInsets.all(0),
                margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        '학교 공지사항',
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.w700),
                      ),
                      Container(
                          height: 200,
                          margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
                          decoration: BoxDecoration(
                            color: Colors.grey[50],
                            borderRadius: BorderRadius.circular(8),
                            boxShadow: [
                              BoxShadow(
                                color: Colors.black
                                    .withOpacity(0.1), // 그림자 색상과 불투명도
                                spreadRadius: 1, // 그림자 확산 정도
                                blurRadius: 3, // 그림자의 흐림 정도
                                offset: Offset(2, 3), // 그림자 위치 (가로, 세로)
                              ),
                            ],
                          ),
                          child: ListView.builder(
                              physics: NeverScrollableScrollPhysics(),
                              itemCount: snapshot.data.length,
                              itemBuilder: (context, index) {
                                return GestureDetector(
                                  onTap: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                            builder: (context) => PostDetail(
                                                boardName: '학사 공지',
                                                boardId: snapshot.data[index]
                                                    ['boardId'])));
                                  },
                                  child: Container(
                                    height: 49,
                                    padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                                    margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                                    decoration: BoxDecoration(
                                      border: Border(
                                        bottom: BorderSide(
                                          width:
                                              index == snapshot.data.length - 1
                                                  ? 0
                                                  : 1,
                                          color:
                                              index == snapshot.data.length - 1
                                                  ? Colors.transparent
                                                  : Color(0xffd9d9d9),
                                        ),
                                      ),
                                    ),
                                    child: Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        children: [
                                          SizedBox(
                                            width: MediaQuery.of(context)
                                                    .size
                                                    .width *
                                                0.6,
                                            child: Text(
                                              snapshot.data[index]['title']
                                                  as String,
                                              maxLines: 2,
                                              overflow: TextOverflow.ellipsis,
                                              style: TextStyle(fontSize: 15),
                                            ),
                                          ),
                                          Text(
                                            extractDate(snapshot.data[index]
                                                ['createdDate']),
                                            style: TextStyle(
                                                color: Color(0xff999999)),
                                          )
                                        ]),
                                  ),
                                );
                              })),
                    ]),
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
