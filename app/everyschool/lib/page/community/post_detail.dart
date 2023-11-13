import 'package:everyschool/page/community/post_detail_dialog.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/page/community/create_post.dart';
import 'package:everyschool/page/community/post_comments.dart';
import 'package:everyschool/api/community_api.dart';
import 'dart:convert';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:everyschool/store/user_store.dart';

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

  @override
  void initState() {
    super.initState();
    _loadPostDetail();
  }

  Future<void> _loadPostDetail() async {
    final userType = context.read<UserStore>().userInfo["userType"];
    late final schoolId;
    if (userType == 1002) {
      final storage = FlutterSecureStorage();
      final descendantInfo = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendantInfo);
      schoolId = selectDescendant["school"]["schoolId"];
    } else {
      schoolId = context.read<UserStore>().userInfo["school"]["schoolId"];
    }
    var response;
    try {
      if (widget.boardName == '자유게시판') {
        response =
            await communityApi.getPostDetail(schoolId, 'frees', widget.boardId);
      } else if (widget.boardName == '학사 공지') {
        response = await communityApi.getPostDetail(
            schoolId, 'notice', widget.boardId);
      } else if (widget.boardName == '가정통신문') {
        response = await communityApi.getPostDetail(
            schoolId, 'communications', widget.boardId);
      }

      if (response != null) {
        setState(() {
          postDetail = response;
        });
      }
    } catch (e) {
      print('데이터 로딩 중 오류 발생: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    if (postDetail.isEmpty) {
      return Center(child: CircularProgressIndicator());
    }
    return Scaffold(
      body: ListView(
        children: <Widget>[
          // Custom AppBar
          Container(
            padding: EdgeInsets.symmetric(horizontal: 10, vertical: 8),
            color: Colors.grey[50],
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                IconButton(
                  iconSize: 30,
                  icon: Icon(Icons.arrow_back, color: Color(0XFF15075F)),
                  onPressed: () {
                    Navigator.pop(context);
                  },
                ),
                Row(
                  children: [
                    IconButton(
                      iconSize: 30,
                      icon: Icon(Icons.restart_alt, color: Color(0XFF15075F)),
                      onPressed: () {
                        _loadPostDetail();
                      },
                      padding: EdgeInsets.all(0),
                      constraints: BoxConstraints(
                        minWidth: 10, // minWidth와 minHeight를 원하는 값으로 설정
                        minHeight: 10,
                      ),
                    ),
                    SizedBox(width: 8), // 원하는 간격을 추가 (예: 8픽셀)
                    IconButton(
                      iconSize: 30,
                      icon: Icon(Icons.more_vert, color: Color(0XFF15075F)),
                      onPressed: () {
                        // boardId와 title을 전달하여 PostDetailDialog를 호출
                        PostDetailDialog(
                                boardId: widget.boardId,
                                title: postDetail["title"])
                            .cardDetail(context);
                      },
                      padding: EdgeInsets.all(0),
                      constraints: BoxConstraints(
                        minWidth: 10,
                        minHeight: 10,
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Container(
            padding: EdgeInsets.fromLTRB(25, 10, 25, 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  // Row 위젯을 유지
                  children: [
                    Container(
                      width: 50,
                      height: 50,
                      decoration: BoxDecoration(
                        color: Colors.grey,
                        borderRadius: BorderRadius.all(Radius.circular(8)),
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
                          Text(
                            '익명',
                            style: TextStyle(
                              fontSize: 17,
                              fontWeight: FontWeight.w700,
                            ),
                          ),
                          SizedBox(
                            height: 5,
                          ),
                          Text(postDetail["createdDate"])
                        ],
                      ),
                    ),
                  ],
                ),
                SizedBox(height: 20),
                Text(
                  '${postDetail["title"]}',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
                ),
                SizedBox(height: 20),
                if (postDetail['imageUrls'] != null)
                  ...postDetail['imageUrls'].map((imageUrl) => Column(
                        children: [
                          Image.network(imageUrl),
                          SizedBox(height: 10),
                        ],
                      )),
                Text(
                  '${postDetail["content"]}',
                  style: TextStyle(
                    fontSize: 16,
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                Row(children: [
                  Image.asset(
                    'assets/images/community/comment.png',
                    width: 25,
                    height: 25,
                  ),
                  Text(
                    ' ${postDetail["commentCount"]}',
                    style: TextStyle(fontSize: 15, color: Color(0XFF32D9FE)),
                  ),
                ]),
              ],
            ),
          ),
          PostComments(comments: postDetail['comments']),
        ],
      ),
    );
  }
}
