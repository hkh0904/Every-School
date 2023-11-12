import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:provider/provider.dart';
import 'package:timezone/timezone.dart' as tz;


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
    late final schoolId;

    if (userType == 1002) {
      schoolId = context.read<UserStore>().userInfo["descendants"][0]["school"]
          ["schoolId"];
    } else {
      schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
    }
    var response;
    try {
      if (userType == 1001) {
        response = await communityApi.getBoardList(schoolId);
      } else if (userType == 1002 || userType == 1003) {
        response = await communityApi.getNoticeList(schoolId);
      }
      if (response != null && response['content'] != null) {
        setState(() {
          boardList = response['content'];
        });
      }
    } catch (e) {
      print('데이터 로딩 중 오류 발생: $e');
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
            boardList.length < 5 ? boardList.length * 47.0 : 235.0;

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
                    String pageTitle = userType == 1001 ? '자유 게시판' : '학사 공지';
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) =>
                            PostlistPage(pageTitle: pageTitle),
                      ),
                    );
                  },
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      Text(
                        userType == 1001 ? '자유 게시판' : '학사 공지',
                        style: TextStyle(
                            fontSize: 22, fontWeight: FontWeight.w800),
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
                        height: 45,
                        padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
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
                                  builder: (context) =>
                                      PostDetail(boardId: boardId),
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
                                          15
                                      ? '${(boardList[index]['title'] as String).substring(0, 15)}...'
                                      : boardList[index]['title'] as String,
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w600),
                                ),
                              ),
                              if (isWithinAnHour)
                                Image.asset('assets/images/community/new.png')
                              else
                                Text(
                                  displayTime,
                                  style: TextStyle(
                                      fontSize: 17, color: Color(0xff999999)),
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
