import 'package:flutter/material.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:provider/provider.dart';
import 'package:timezone/timezone.dart' as tz;
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CommunityBoard extends StatefulWidget {
  const CommunityBoard({super.key});

  @override
  State<CommunityBoard> createState() => _CommunityBoardState();
}

class _CommunityBoardState extends State<CommunityBoard> {
  final CommunityApi communityApi = CommunityApi();
  List<dynamic> boardList = [];

  @override
  void initState() {
    super.initState();
    _loadBoardData();
  }

  Future<void> _loadBoardData() async {
    final userType = context.read<UserStore>().userInfo["userType"];
    late final schoolYear;
    late final schoolId;
    if (userType == 1002) {
      final storage = FlutterSecureStorage();
      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);
      schoolYear = selectDescendant["schoolClass"]["schoolYear"];
      schoolId = selectDescendant["school"]["schoolId"];
    } else {
      schoolYear =
          context.read<UserStore>().userInfo["schoolClass"]["schoolYear"];
      schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
    }
    var response;
    try {
      if (userType == 1001) {
        response = await communityApi.getBoardList(schoolYear, schoolId, 1);
      } else if (userType == 1002 || userType == 1003) {
        response = await communityApi.getNoticeList(schoolYear, schoolId, 1);
      }
      if (response != null && response['content'] != null) {
        setState(() {
          boardList = response['content'];
        });
      }
    } catch (e) {
      print('커뮤니티 보드 에러: $e');
    }
  }

  String formatDateTime(String dateTimeStr) {
    tz.TZDateTime postDateTime;
    try {
      postDateTime = tz.TZDateTime.parse(tz.local, dateTimeStr);
    } catch (e) {
      print('DateTime parsing error: $e');
      return dateTimeStr;
    }

    tz.TZDateTime now = tz.TZDateTime.now(tz.local);
    Duration difference = now.difference(postDateTime);

    if (difference.inDays == 0) {
      if (difference.inHours == 0) {
        if (difference.inMinutes > 5) {
          return '${difference.inMinutes}분 전';
        } else {
          return '방금 전';
        }
      } else {
        return '${difference.inHours}시간 전';
      }
    } else {
      return '${postDateTime.month.toString().padLeft(2, '0')}/${postDateTime.day.toString().padLeft(2, '0')}';
    }
  }

  bool isWithinHour(String dateTimeStr) {
    tz.TZDateTime postDateTime;
    try {
      postDateTime = tz.TZDateTime.parse(tz.local, dateTimeStr);
    } catch (e) {
      print('DateTime parsing error: $e');
      return false; // 오류 발생 시 false 반환
    }

    tz.TZDateTime now = tz.TZDateTime.now(tz.local);
    Duration difference = now.difference(postDateTime);

    return difference.inHours == 0 && difference.inMinutes < 60;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserStore>(
      builder: (context, userStore, child) {
        final userType = userStore.userInfo["userType"];
        double containerHeight =
            boardList.length < 5 ? boardList.length * 35.0 : 180.0;

        return Container(
          decoration: BoxDecoration(
              borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(20), topRight: Radius.circular(20))),
          child: Container(
            // padding: EdgeInsets.all(0),
            margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                GestureDetector(
                  onTap: () {
                    String pageTitle = userType == 1001 ? '자유게시판' : '학사 공지';
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) =>
                            PostlistPage(pageTitle: pageTitle),
                      ),
                    ).then((check) {
                      print(check);
                      if (check == 'refresh') {
                        _loadBoardData();
                      }
                    });
                  },
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      Text(
                        userType == 1001 ? '자유 게시판' : '학사 공지',
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.w700),
                      ),
                      Text('더보기',
                          style: TextStyle(fontSize: 15, color: Colors.grey)),
                    ],
                  ),
                ),
                Container(
                  height: containerHeight,
                  margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
                  decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.grey.shade400,
                      width: 1,
                    ),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: ListView.builder(
                    physics: NeverScrollableScrollPhysics(),
                    itemCount: boardList.length,
                    itemBuilder: (context, index) {
                      int? boardId = boardList[index]['boardId'] as int?;
                      String dateTimeString =
                          boardList[index]['createdDate'] as String;
                      String displayTime = formatDateTime(dateTimeString);

                      // 현재 시각과의 차이를 계산하여 1시간 이내인지 확인
                      bool isWithinAnHour = isWithinHour(dateTimeString);

                      return Container(
                        height: 35,
                        padding: EdgeInsets.fromLTRB(10, 5, 10, 0),
                        margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                        child: GestureDetector(
                          onTapDown: (TapDownDetails details) {
                            setState(() {
                              boardList[index]['isTapped'] = true;
                            });
                          },
                          onTapCancel: () {
                            setState(() {
                              boardList[index]['isTapped'] = false;
                            });
                          },
                          onTapUp: (TapUpDetails details) {
                            setState(() {
                              boardList[index]['isTapped'] = false;
                            });
                            if (boardId != null) {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => PostDetail(
                                      boardId: boardId,
                                      boardName:
                                          userType == 1001 ? '자유게시판' : '학사 공지'),
                                ),
                              ).then((_) {
                                // 사용자가 돌아왔을 때 게시글 목록을 새로고침
                                _loadBoardData();
                              });
                            }
                          },
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              SizedBox(
                                width: MediaQuery.of(context).size.width * 0.6,
                                child: Text(
                                  (boardList[index]['title'] as String).length >
                                          20
                                      ? '${(boardList[index]['title'] as String).substring(0, 20)}...'
                                      : boardList[index]['title'] as String,
                                  style: TextStyle(
                                      fontSize: 16,
                                      fontWeight: FontWeight.w600),
                                ),
                              ),
                              if (isWithinAnHour)
                                Image.asset(
                                  'assets/images/community/new.png',
                                  width: 25,
                                )
                              else
                                Text(
                                  displayTime,
                                  style: TextStyle(
                                      fontSize: 15, color: Color(0xff999999)),
                                ),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
