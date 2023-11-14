import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/postlist_page.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:provider/provider.dart';
import 'package:timezone/timezone.dart' as tz;
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class HomeNoticeBoard extends StatefulWidget {
  const HomeNoticeBoard({super.key});

  @override
  State<HomeNoticeBoard> createState() => _HomeNoticeBoardState();
}

class _HomeNoticeBoardState extends State<HomeNoticeBoard> {
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
      schoolId = selectDescendant["school"]["schoolId"];
      schoolYear = selectDescendant["schoolClass"]["schoolYear"];
    } else {
      schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
      schoolYear =
          context.read<UserStore>().userInfo["schoolClass"]["schoolYear"];
    }
    var response;
    try {
      response = await communityApi.getHomeNoticeList(schoolYear, schoolId);
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
      return '${postDateTime.year.toString().padLeft(2, '0')}.${postDateTime.month.toString().padLeft(2, '0')}.${postDateTime.day.toString().padLeft(2, '0')}';
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

    return difference.inHours == 0 && difference.inMinutes < 1800;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<UserStore>(
      builder: (context, userStore, child) {
        double containerHeight =
            boardList.length < 5 ? boardList.length * 70.0 : 210.0;

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
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) =>
                              PostlistPage(pageTitle: '가정통신문')),
                    );
                  },
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    crossAxisAlignment: CrossAxisAlignment.end,
                    children: [
                      Text(
                        '가정통신문',
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
                        height: 65,
                        padding: EdgeInsets.fromLTRB(10, 15, 10, 0),
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
                                      boardName: '가정통신문', boardId: boardId),
                                ),
                              );
                            }
                          },
                          child: Column(
                            children: [
                              Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                // crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Text(
                                        (boardList[index]['title'] as String)
                                                    .length >
                                                15
                                            ? '${(boardList[index]['title'] as String).substring(0, 15)}...'
                                            : boardList[index]['title']
                                                as String,
                                        style: TextStyle(
                                            fontSize: 20,
                                            fontWeight: FontWeight.w700),
                                      ),
                                      SizedBox(height: 4),
                                      Text(
                                        displayTime,
                                        style: TextStyle(
                                            color: Color(0xff999999),
                                            fontSize: 18),
                                      ),
                                    ],
                                  ),
                                  if (isWithinAnHour)
                                    Image.asset(
                                      'assets/images/community/new.png',
                                      height: 35,
                                    )
                                ],
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
