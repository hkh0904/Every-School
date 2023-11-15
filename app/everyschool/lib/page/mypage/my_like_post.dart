import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class MyLikePost extends StatefulWidget {
  const MyLikePost({super.key});

  @override
  State<MyLikePost> createState() => _MyLikePostState();
}

class _MyLikePostState extends State<MyLikePost> {
  getScrap() async {
    final year = context.read<UserStore>().year;
    final schoolId = context.read<UserStore>().userInfo['school']['schoolId'];

    var response = await CommunityApi().getMyScrap(year, schoolId);
    return response;
  }

  String formatDateString(String dateString) {
    DateTime createdDate = DateTime.parse(dateString);

    return DateFormat('yyyy.MM.dd').format(createdDate);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getScrap(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Scaffold(
              appBar: AppBar(
                elevation: 2,
                backgroundColor: Colors.grey[50],
                leading: BackButton(color: Colors.black),
                centerTitle: true,
                title: Text(
                  '내가 좋아요 한 글',
                  style: TextStyle(
                      color: Colors.black,
                      fontSize: 18,
                      fontWeight: FontWeight.w700),
                ),
              ),
              body: Column(
                children: [
                  SizedBox(
                    height: 20,
                  ),
                  Padding(
                    padding: const EdgeInsets.fromLTRB(20, 5, 20, 5),
                    child: Align(
                        alignment: Alignment.centerRight,
                        child: Text('총 ${snapshot.data['content'].length}개')),
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Container(
                    decoration: BoxDecoration(
                      border: Border(
                        top: BorderSide(
                          color: Colors.grey.shade400,
                          width: 1.0,
                        ),
                      ),
                    ),
                    child: Column(
                      children: List.generate(
                        snapshot.data['content'].length,
                        (index) {
                          return GestureDetector(
                            onTap: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => PostDetail(
                                        boardName: '자유게시판',
                                        boardId: snapshot.data['content'][index]
                                            ['boardId'])),
                              );
                            },
                            child: Container(
                              height: 80,
                              padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
                              decoration: BoxDecoration(
                                border: Border(
                                  bottom: BorderSide(
                                    color: Colors.grey.shade400,
                                    width: 1.0,
                                  ),
                                ),
                              ),
                              child: Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: [
                                  Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Container(
                                        margin: EdgeInsets.only(bottom: 5),
                                        child: Text(
                                          snapshot.data['content'][index]
                                              ['title'] as String,
                                          style: TextStyle(
                                            fontSize: 16,
                                            fontWeight: FontWeight.w700,
                                          ),
                                        ),
                                      ),
                                      Text(formatDateString(
                                          snapshot.data['content'][index]
                                              ['createdDate']))
                                    ],
                                  ),
                                  Padding(
                                    padding:
                                        const EdgeInsets.fromLTRB(0, 0, 0, 5),
                                    child: Align(
                                      alignment: Alignment.bottomRight,
                                      child: Row(
                                        children: [
                                          Padding(
                                            padding:
                                                EdgeInsets.fromLTRB(0, 0, 5, 0),
                                            child: Icon(
                                              Icons.message_outlined,
                                              size: 16,
                                              color: const Color.fromARGB(
                                                  255, 109, 188, 253),
                                            ),
                                          ),
                                          Text(
                                              '${snapshot.data['content'][index]['commentCount']}')
                                        ],
                                      ),
                                    ),
                                  )
                                ],
                              ),
                            ),
                          );
                        },
                      ),
                    ),
                  ),
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
