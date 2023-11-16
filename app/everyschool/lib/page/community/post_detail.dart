import 'package:flutter/material.dart';
import 'package:everyschool/page/community/post_comments.dart';
import 'package:everyschool/api/community_api.dart';
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:timezone/timezone.dart' as tz;

class PostDetail extends StatefulWidget {
  final String boardName;
  final int boardId;

  const PostDetail({Key? key, required this.boardName, required this.boardId})
      : super(key: key);

  @override
  State<PostDetail> createState() => _PostDetailState();
}

class _PostDetailState extends State<PostDetail> {
  final CommunityApi communityApi = CommunityApi();
  Map<String, dynamic> postDetail = {};
  int? schoolYear; // nullable 타입으로 변경
  int? schoolId; // nullable 타입으로 변경
  int? userType;
  String? schoolName;
  late dynamic tempScrapCount;
  late bool tempMyScrap;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _loadPostDetail();
    });
  }

  Future<void> _loadPostDetail() async {
    if (userType == null) {
      userType = context.read<UserStore>().userInfo["userType"];
    }

    if (schoolId == null || schoolYear == null || schoolName == null) {
      if (userType == 1002) {
        final storage = FlutterSecureStorage();
        final descendantInfo = await storage.read(key: 'descendant') ?? "";
        var selectDescendant = jsonDecode(descendantInfo);
        schoolId = selectDescendant["school"]["schoolId"];
        schoolName = selectDescendant["school"]["name"];
        schoolYear = selectDescendant["schoolClass"]["schoolYear"];
      } else {
        schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
        schoolYear =
            context.read<UserStore>().userInfo["schoolClass"]["schoolYear"];
        schoolName = context.read<UserStore>().userInfo["school"]["name"];
      }
    }

    var response;
    try {
      if (widget.boardName == '자유게시판') {
        response = await communityApi.getPostDetail(
            schoolYear, schoolId, 'free', widget.boardId);
      } else if (widget.boardName == '학사 공지') {
        response = await communityApi.getPostDetail(
            schoolYear, schoolId, 'notice', widget.boardId);
      } else if (widget.boardName == '가정통신문') {
        response = await communityApi.getPostDetail(
            schoolYear, schoolId, 'communication', widget.boardId);
      }

      if (response != null) {
        setState(() {
          postDetail = response;
          tempMyScrap = response['inMyScrap'];
          tempScrapCount = response['scrapCount'];
        });
      }
    } catch (e) {
      print('데이터 로딩 중 오류 발생: $e');
    }
  }

  Future<void> _toggleScrap(nowStatus) async {
    try {
      if (nowStatus == false) {
        await communityApi.scrapPost(widget.boardId, schoolId, schoolYear);
        setState(() {
          tempMyScrap = true;
          tempScrapCount += 1;
        });
      } else {
        await communityApi.unScrapPost(widget.boardId, schoolId, schoolYear);
        setState(() {
          tempMyScrap = false;
          tempScrapCount -= 1;
        });
      }
    } catch (e) {
      print('스크랩 에러 $e');
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

  void refreshComments() {
    _loadPostDetail();
  }

  @override
  Widget build(BuildContext context) {
    if (postDetail.isEmpty) {
      return Center(child: CircularProgressIndicator());
    }
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 0,
          leading: IconButton(
            icon: Icon(Icons.arrow_back),
            onPressed: () {
              Navigator.pop(context, 'refresh'); // 이전 페이지로 돌아가기
            },
          ),
        ),
        body: ListView(
          children: <Widget>[
            Container(
              padding: EdgeInsets.fromLTRB(25, 10, 25, 10),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Row(
                        // Row 위젯을 유지
                        children: [
                          Container(
                            width: 50,
                            height: 50,
                            decoration: BoxDecoration(
                              color: Colors.grey,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(8)),
                            ),
                            child: Align(
                              alignment: Alignment.center,
                              child: Image.asset(
                                'assets/images/community/user.png',
                                width: 40,
                                height: 40,
                              ),
                            ),
                          ),
                          Container(
                            margin: EdgeInsets.only(left: 10),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Row(
                                  children: [
                                    Text(
                                      widget.boardName != '자유게시판'
                                          ? schoolName as String
                                          : '익명',
                                      style: TextStyle(
                                        fontSize: 17,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                  ],
                                ),
                                SizedBox(
                                  height: 5,
                                ),
                                Text(formatDateTime(postDetail["createdDate"]))
                              ],
                            ),
                          ),
                        ],
                      ),
                      GestureDetector(
                        onTap: () => _toggleScrap(tempMyScrap),
                        child: Container(
                          padding: EdgeInsets.fromLTRB(10, 0, 0, 10),
                          child: SizedBox(
                            // width: textWidth + 35,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                if (tempMyScrap == true)
                                  Icon(
                                    Icons.favorite,
                                    color: Color.fromARGB(255, 255, 108, 152),
                                    size: 25,
                                  ),
                                if (tempMyScrap == false)
                                  Icon(
                                    Icons.favorite_border,
                                    color: Color.fromARGB(255, 255, 108, 152),
                                    size: 25,
                                  ),
                                Text(
                                  ' $tempScrapCount',
                                  style: TextStyle(
                                    fontSize: 23,
                                    fontWeight: FontWeight.w500,
                                    color: Color.fromARGB(255, 255, 108, 152),
                                  ),
                                  textAlign: TextAlign.center,
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                  SizedBox(height: 20),
                  Text(
                    '${postDetail["title"]}',
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
                  ),
                  SizedBox(height: 6),
                  if (postDetail['imageUrls'] != null &&
                      postDetail['imageUrls'].isNotEmpty) ...[
                    Text(
                      '첨부 이미지',
                      style: TextStyle(color: Colors.grey[400]),
                    ),
                    ...postDetail['imageUrls'].map((imageUrl) => Column(
                          children: [
                            Image.network(imageUrl),
                            SizedBox(height: 10),
                          ],
                        )),
                  ],
                  Text(
                    '${postDetail["content"]}',
                    style: TextStyle(
                      fontSize: 18,
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  if (widget.boardName == '자유게시판')
                    Row(children: [
                      // Icon(
                      //   Icons.comment_outlined,
                      //   color: Colors.cyan[400],
                      // ),
                      Text(
                        '댓글 ${postDetail["commentCount"]}',
                        style: TextStyle(fontSize: 15, color: Colors.cyan[400]),
                      ),
                    ]),
                ],
              ),
            ),
            if (widget.boardName == '자유게시판')
              if (schoolId != null && schoolYear != null)
                PostComments(
                  boardId: widget.boardId,
                  schoolId: schoolId!,
                  schoolYear: schoolYear!,
                  comments: postDetail['comments'],
                  onCommentAdded: refreshComments,
                ),
          ],
        ),
      ),
    );
  }
}
