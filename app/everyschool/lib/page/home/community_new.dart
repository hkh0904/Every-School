import 'dart:convert';

import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class CommunityNew extends StatefulWidget {
  const CommunityNew({super.key});

  @override
  State<CommunityNew> createState() => _CommunityNewState();
}

class _CommunityNewState extends State<CommunityNew> {
  final storage = FlutterSecureStorage();
  var userType;

  getPost() async {
    userType = await storage.read(key: 'usertype');
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
    var response = CommunityApi().getNewPostList(year, schoolId);
    return response;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getPost(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            if (userType != '1002') {
              return Container(
                margin: EdgeInsets.fromLTRB(20, 30, 20, 10),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        '커뮤니티 최신글',
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.w700),
                      ),
                      Container(
                          height: 200,
                          margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
                          decoration: BoxDecoration(
                              color: Colors.grey[50],
                              borderRadius: BorderRadius.circular(8),
                              border: Border.all(
                                  color: Color(0xffD9D9D9), width: 1)),
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
                                                boardName: '자유게시판',
                                                boardId: snapshot.data[index]
                                                    ['boardId'])));
                                  },
                                  child: Container(
                                    height: 50,
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
                                        ]),
                                  ),
                                );
                              })),
                    ]),
              );
            } else {
              return SizedBox.shrink();
            }
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
