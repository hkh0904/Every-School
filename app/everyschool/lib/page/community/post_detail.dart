import 'package:everyschool/page/community/post_detail_dialog.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/page/community/create_post.dart';
import 'package:everyschool/page/community/post_comments.dart';

class PostDetail extends StatefulWidget {
  final int boardId;

  const PostDetail({Key? key, required this.boardId}) : super(key: key);

  @override
  State<PostDetail> createState() => _PostDetailState();
}

class _PostDetailState extends State<PostDetail> {
  Map<String, dynamic> postData = {
    "code": 200,
    "status": "OK",
    "message": "SUCCESS",
    "data": {
      "boardId": 2,
      "title": "1학년 2반 차은우 잘생김",
      "content": "ㅇㅈ?",
      "userName": "익명",
      "createDate": "2023.10.10 14:20",
      "uploadFiles": [],
      "comments": [
        {
          "userNumber": 1,
          "content": "ㅋㅋㅋㅋㅋㅋㅋㅋㅇㅈㅇㅈ",
          "createdDate": "10/10 14:31",
          "reComment": [
            {
              "userNumber": 2,
              "content": "차은우랑 결혼할거임",
              "createdDate": "10/10 14:41",
              "reComment": []
            },
            {
              "userNumber": 1,
              "content": "차은우는 뭔 죄",
              "createdDate": "10/10 14:51",
              "reComment": []
            },
            {
              "userNumber": 0,
              "content": "69세 차은우랑 결혼하신다함",
              "createdDate": "10/10 14:55",
              "reComment": []
            }
          ]
        },
        {
          "userNumber": 2,
          "content": "왜 안나오냐",
          "createdDate": "10/10 14:31",
          "reComment": []
        },
        {
          "userNumber": 3,
          "content": "마진 테스트",
          "createdDate": "10/10 14:31",
          "reComment": []
        },
      ]
    }
  };

  String formatDateTime(String dateTimeStr) {
    // 공백과 점을 기준으로 문자열을 분리
    List<String> parts = dateTimeStr.split(' ');
    List<String> dateParts = parts[0].split('.');

    // 년, 월, 일 부분은 그대로 사용하고 시간 부분을 분리
    String year = dateParts[0];
    String month = dateParts[1];
    String day = dateParts[2];
    String time = parts[1];

    // DateTime 객체로 변환
    DateTime postDateTime;
    try {
      postDateTime = DateTime.parse('$year-$month-$day $time');
    } catch (e) {
      print('DateTime parsing error: $e');
      return dateTimeStr; // parsing에 실패하면 원래 문자열을 반환
    }

    return '${postDateTime.month}/${postDateTime.day} ${postDateTime.hour.toString().padLeft(2, '0')}:${postDateTime.minute.toString().padLeft(2, '0')}';
  }

  int calculateTotalComments(List<dynamic> comments) {
    int totalComments = comments.length;

    for (var comment in comments) {
      totalComments += (comment['reComment'] as List).length;
    }

    return totalComments;
  }

  @override
  Widget build(BuildContext context) {
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
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => CreatePost()),
                        );
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
                                title: postData["data"]["title"])
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
                          Text(formatDateTime(postData["data"]?["createDate"]))
                        ],
                      ),
                    ),
                  ],
                ),
                SizedBox(height: 20),
                Text(
                  '${postData["data"]?["title"]}',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
                ),
                SizedBox(height: 10),
                Text(
                  '${postData["data"]?["content"]}',
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
                    ' ${calculateTotalComments(postData["data"]["comments"])} / 게시글 번호 :  ${widget.boardId}',
                    style: TextStyle(fontSize: 15, color: Color(0XFF32D9FE)),
                  ),
                ]),
              ],
            ),
          ),
          PostComments(comments: postData['data']['comments']),
        ],
      ),
    );
  }
}
