import 'package:flutter/material.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:provider/provider.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:timezone/timezone.dart' as tz;
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class PostList extends StatefulWidget {
  final String pageTitle;
  const PostList({Key? key, required this.pageTitle}) : super(key: key);

  @override
  State<PostList> createState() => _PostListState();
}

class _PostListState extends State<PostList> {
  final CommunityApi communityApi = CommunityApi();
  List<dynamic> postList = [];

  Future<void> _loadPostData() async {
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
        response = await communityApi.getBoardList(schoolYear, schoolId);
      } else if (widget.pageTitle == '학사 공지') {
        response = await communityApi.getNoticeList(schoolYear, schoolId);
      } else if (widget.pageTitle == '가정통신문') {
        response = await communityApi.getHomeNoticeList(schoolYear, schoolId);
      }

      if (response != null && response['content'] != null) {
        setState(() {
          postList = response['content'];
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
  }

  String formatText(String text) {
    if (text.length > 35) {
      text = '${text.substring(0, 35)}...';
    }
    // 이미 문자열이 30자를 초과하더라도, 10자가 넘으면 여전히 줄바꿈을 적용합니다.
    if (text.length > 15) {
      int breakIndex = text.indexOf(' ', 15);
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
      itemCount: postList.length,
      itemBuilder: (context, index) {
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
              ).then((_) {
                // 사용자가 돌아왔을 때 게시글 목록을 새로고침
                _loadPostData();
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
                              postList[index]['title'] as String,
                              style: TextStyle(
                                fontSize: 19,
                                fontWeight: FontWeight.w700,
                              ),
                            ),
                          ),
                          SizedBox(
                            height: 35,
                            child: Text(
                              formatText(postList[index]['content'] as String),
                              style: TextStyle(fontSize: 15),
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
                              Image.asset(
                                'assets/images/community/comment.png',
                                width: 25,
                                height: 25,
                              ),
                              Text(
                                ' ${postList[index]['comments'].toString()}',
                                style: TextStyle(
                                    fontSize: 15, color: Color(0XFF32D9FE)),
                              ),
                              Text(
                                ' | ',
                                style:
                                    TextStyle(color: Colors.grey, fontSize: 18),
                              ),
                            ],
                          )
                        : Container(),
                    Text(formatDateTime(
                        postList[index]['createdDate'] as String)),
                    Text(
                      ' | ',
                      style: TextStyle(color: Colors.grey, fontSize: 18),
                    ),
                    Text('익명'),
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
