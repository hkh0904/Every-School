import 'package:flutter/material.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:timezone/timezone.dart' as tz;

class PostComments extends StatefulWidget {
  final int boardId;
  final int schoolId;
  final int schoolYear;
  final List<dynamic> comments;
  final VoidCallback onCommentAdded;

  PostComments(
      {Key? key,
      required this.boardId,
      required this.schoolId,
      required this.schoolYear,
      required this.comments,
      required this.onCommentAdded,})
      : super(key: key);

  @override
  State<PostComments> createState() => _PostCommentsState();
}

class _PostCommentsState extends State<PostComments> {
  final CommunityApi communityApi = CommunityApi();
  TextEditingController commentController = TextEditingController();
  TextEditingController recommentController = TextEditingController();
  int? activeCommentId; // 현재 활성화된 답글 입력 필드의 ID
  int? tempScrapCount;
  bool? tempMyScrap;

  // 답글 입력 필드를 토글하는 함수
  void toggleReplyField(int commentId) {
    setState(() {
      if (activeCommentId == commentId) {
        // 이미 활성화된 답글 입력 필드를 다시 누른 경우, 닫고 초기화
        activeCommentId = null;
        recommentController.clear();
      } else {
        // 다른 답글 입력 필드를 누른 경우, 새로운 답글 입력 필드를 활성화
        activeCommentId = commentId;
        recommentController.clear();
      }
    });
  }

  Future<void> _writeComment() async {
    var response;
    try {
      var formData = {'content': commentController.text};
      response = await communityApi.writeComment(
          widget.boardId, widget.schoolId, widget.schoolYear, formData);
      if (response != null) {
        widget.onCommentAdded();
        commentController.text = '';
      }
    } catch (e) {
      print(e);
    }
  }

  Future<void> _writeReComment(commentId) async {
    var response;
    try {
      var formData = {'content': recommentController.text};
      response = await communityApi.writeRecomment(widget.boardId,
          widget.schoolId, widget.schoolYear, commentId, formData);
      if (response != null) {
        widget.onCommentAdded();
        recommentController.text = '';
      }
    } catch (e) {
      print(e);
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

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
            child: TextField(
              controller: commentController,
              decoration: InputDecoration(
                border: OutlineInputBorder(borderSide: BorderSide(width: 1)),
                focusedBorder: OutlineInputBorder(
                  borderSide: BorderSide(color: Color(0XFF15075f), width: 1),
                ),
                hintText: '댓글 작성하기',
                contentPadding: EdgeInsets.symmetric(
                  vertical: 10,
                  horizontal: 10,
                ),
              ),
              onSubmitted: (value) {
                _writeComment();
              },
            ),
          ),
          widget.comments.isEmpty
              ? Padding(
                  padding: EdgeInsets.symmetric(vertical: 20),
                  child: Center(
                    child: Text(
                      '아직 작성된 댓글이 없습니다',
                      style: TextStyle(fontSize: 20, color: Colors.grey[500]),
                    ),
                  ),
                )
              : ListView.builder(
                  itemCount: widget.comments.length,
                  shrinkWrap: true,
                  physics: NeverScrollableScrollPhysics(),
                  itemBuilder: (context, index) {
                    var comment = widget.comments[index];
                    return Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        _buildComment(comment),
                        ..._buildReComments(comment['reComments']),
                      ],
                    );
                  },
                ),
          SizedBox(height: 20)
        ],
      ),
    );
  }

  Widget _buildComment(dynamic comment) {
    return Container(
      padding: EdgeInsets.fromLTRB(25, 10, 20, 10),
      decoration: BoxDecoration(),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Container(
                padding: EdgeInsets.all(5),
                decoration: BoxDecoration(
                  color: Colors.grey,
                  borderRadius: BorderRadius.all(Radius.circular(8)),
                ),
                child: Align(
                  alignment: Alignment.center,
                  child: Image.asset('assets/images/community/user.png',
                      width: 16, height: 16),
                ),
              ),
              SizedBox(width: 10), // 추가된 공간
              Text(
                '익명',
                style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w700,
                    color: Colors.black),
              ),
              Expanded(
                child: Container(), // 여기에 Expanded를 추가하여 공간을 만듭니다.
              ),
              if (comment['depth'] == 0)
                GestureDetector(
                  onTap: () => toggleReplyField(comment['commentId']),
                  child: Text(
                    '답글',
                    style: TextStyle(fontSize: 15, color: Colors.black),
                  ),
                ),
            ],
          ),
          SizedBox(height: 5),
          Text(comment['content'],
              style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
          SizedBox(height: 5),
          Text(formatDateTime(comment['createdDate']),
              style: TextStyle(color: Colors.grey)),
          if (activeCommentId == comment['commentId'])
            TextField(
              controller: recommentController,
              decoration: InputDecoration(
                border: UnderlineInputBorder(
                  borderSide: BorderSide(width: 2.5),
                ),
                focusedBorder: UnderlineInputBorder(
                  borderSide: BorderSide(color: Color(0XFF15075f), width: 2.5),
                ),
                hintText: '답글 작성하기',
                contentPadding:
                    EdgeInsets.symmetric(vertical: 10, horizontal: 15),
              ),
              onSubmitted: (value) {
                _writeReComment(comment['commentId']);
              },
            ),
        ],
      ),
    );
  }

  List<Widget> _buildReComments(List<dynamic>? reComments) {
    // reComments가 null이면 빈 리스트를 반환
    if (reComments == null) return [];
    return reComments.asMap().entries.map((entry) {
      int idx = entry.key;
      var reComment = entry.value;

      return Container(
        padding: EdgeInsets.fromLTRB(
            20, 5, 20, idx == reComments.length - 1 ? 10 : 5),
        child: Column(
          children: [
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Icon(Icons.subdirectory_arrow_right, size: 30),
                Expanded(
                  child: Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(15),
                      color: Color(0XFFF4F4F4),
                    ),
                    child: _buildComment(reComment),
                  ),
                ),
              ],
            ),
          ],
        ),
      );
    }).toList();
  }
}
