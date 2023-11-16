import 'package:flutter/material.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:provider/provider.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:timezone/timezone.dart' as tz;
import 'dart:convert';
import 'package:everyschool/page/community/post_detail.dart';

class PostList extends StatefulWidget {
  final String pageTitle;
  final bool needRefresh;
  const PostList({Key? key, required this.pageTitle, this.needRefresh = false})
      : super(key: key);

  @override
  State<PostList> createState() => _PostListState();
}

class _PostListState extends State<PostList> {
  final CommunityApi communityApi = CommunityApi();
  List<dynamic> postList = [];
  int page = 1;

  Future<void> _loadPostData([int requestedPage = 1]) async {
    // 사용자 타입, 학년, 학교 ID를 가져옴
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
      if (widget.pageTitle == '자유게시판') {
        response = await communityApi.getBoardList(
            schoolYear, schoolId, requestedPage);
      } else if (widget.pageTitle == '학사 공지') {
        response = await communityApi.getNoticeList(
            schoolYear, schoolId, requestedPage);
      } else if (widget.pageTitle == '가정통신문') {
        response = await communityApi.getHomeNoticeList(
            schoolYear, schoolId, requestedPage);
      }

      if (response != null &&
          response['content'] != null &&
          response['content'].isNotEmpty) {
        setState(() {
          page = requestedPage;
          postList = response['content'];
          print(postList);
        });
      }
    } catch (e) {
      print('데이터 로딩 중 오류 발생: $e');
    }
  }

  @override
  void initState() {
    super.initState();
    _loadPostData();
    if (widget.needRefresh) {
      _loadPostData();
    }
  }

  void nextPage() {
    _loadPostData(page + 1);
  }

  void previousPage() {
    if (page > 1) {
      _loadPostData(page - 1);
    }
  }

  String formatText(String text) {
    if (text.length > 45) {
      text = '${text.substring(0, 45)}...';
    }
    // 이미 문자열이 30자를 초과하더라도, 10자가 넘으면 여전히 줄바꿈을 적용합니다.
    if (text.length > 20) {
      int breakIndex = text.indexOf(' ', 20);
      if (breakIndex != -1) {
        text =
            '${text.substring(0, breakIndex)}\n${text.substring(breakIndex + 1)}';
      }
    }
    return text;
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

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: postList.length + 1, // '이전', '다음' 버튼을 위해 +1
      itemBuilder: (context, index) {
        if (index == postList.length) {
          // 리스트의 마지막 항목에서 '이전', '다음' 버튼 표시
          return Column(
            children: [
              SizedBox(height: 3),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  IconButton(
                    iconSize: 18,
                    icon: Icon(
                      Icons.arrow_back_ios_rounded,
                    ),
                    onPressed: previousPage,
                  ),
                  Text(
                    page.toString(),
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  IconButton(
                    iconSize: 18,
                    icon: Icon(
                      Icons.arrow_forward_ios_rounded,
                    ),
                    onPressed: nextPage,
                  ),
                ],
              ),
              SizedBox(height: 3)
            ],
          );
        }
        int? boardId = postList[index]['boardId'] as int?;
        return GestureDetector(
          onTapDown: (TapDownDetails details) {
            setState(() {
              postList[index]['isTapped'] = true;
            });
          },
          onTapCancel: () {
            setState(() {
              postList[index]['isTapped'] = false;
            });
          },
          onTapUp: (TapUpDetails details) {
            setState(() {
              postList[index]['isTapped'] = false;
            });
            if (boardId != null) {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) =>
                      PostDetail(boardName: widget.pageTitle, boardId: boardId),
                ),
              ).then((check) {
                if (check == 'refresh') {
                  _loadPostData();
                }
              });
            }
          },
          child: Container(
            padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
            decoration: BoxDecoration(
              color: (postList[index]['isTapped'] == true)
                  ? Colors.grey[200]
                  : Colors.white,
              border: Border(
                bottom: BorderSide(
                  color: Colors.grey.shade400,
                  width: 1.0,
                ),
              ),
            ),
            child: Column(
              children: [
                Row(
                  children: [
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Container(
                            margin: EdgeInsets.only(bottom: 5),
                            child: Text(
                              (postList[index]['title'] as String).length > 22
                                  ? '${(postList[index]['title'] as String).substring(0, 22)}...'
                                  : postList[index]['title'] as String,
                              style: TextStyle(
                                fontSize: 18,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ),
                          SizedBox(height: 3),
                          SizedBox(
                            height: 38,
                            child: Text(
                              formatText(postList[index]['content'] as String),
                              style: TextStyle(fontSize: 16),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
                SizedBox(height: 5),
                Row(
                  children: [
                    (postList[index]['commentCount'] as int) != 0
                        ? Row(
                            children: [
                              Icon(
                                Icons.comment_outlined,
                                color: Colors.cyan[400],
                                size: 22,
                              ),
                              Text(
                                ' ${postList[index]['commentCount'].toString()}',
                                style: TextStyle(
                                    fontSize: 16, color: Colors.cyan[400]),
                              ),
                              Text(
                                ' ',
                                style: TextStyle(
                                    color: Colors.grey,
                                    fontSize: 18,
                                    fontWeight: FontWeight.w600),
                              ),
                            ],
                          )
                        : Container(),
                    Row(
                      children: [
                        if (postList[index]['inMyScrap'] == true)
                          Icon(
                            Icons.favorite,
                            color: Color.fromARGB(255, 255, 108, 152),
                            size: 25,
                          ),
                        if (postList[index]['inMyScrap'] == false)
                          Icon(
                            Icons.favorite_border,
                            color: Color.fromARGB(255, 255, 108, 152),
                            size: 25,
                          ),
                        SizedBox(width: 2),
                        Text(
                          '${postList[index]['scrapCount'].toString()}',
                          style: TextStyle(
                              fontSize: 16,
                              color: Color.fromARGB(255, 255, 108, 152)),
                        ),
                        Text(
                          '   ',
                          style: TextStyle(
                              color: Colors.grey,
                              fontSize: 18,
                              fontWeight: FontWeight.w600),
                        ),
                      ],
                    ),
                    Text(formatDateTime(
                        postList[index]['createdDate'] as String)),
                  ],
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}
