import 'package:flutter/material.dart';
import 'package:everyschool/api/community_api.dart';

class PostComments extends StatefulWidget {
  final int boardId;
  final int schoolId;
  final int schoolYear;
  final List<dynamic> comments;
  final VoidCallback onCommentAdded;

  PostComments({
    Key? key,
    required this.boardId,
    required this.schoolId,
    required this.schoolYear,
    required this.comments,
    required this.onCommentAdded,
  }) : super(key: key);

  @override
  State<PostComments> createState() => _PostCommentsState();
}

class _PostCommentsState extends State<PostComments> {
  final CommunityApi communityApi = CommunityApi();
  TextEditingController commentController = TextEditingController();

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

  @override
  void initState() {
    super.initState();
    print(widget.comments);
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: [
          Text('댓글 작성하기'),
          Padding(
            padding: EdgeInsets.all(10.0),
            child: TextField(
              controller: commentController,
              decoration: InputDecoration(
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10), // 여기에서 테두리 둥근 정도를 조절
                ),
                focusedBorder: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10),
                  borderSide: BorderSide(color: Colors.purple), // 포커스 시 보라색 테두리
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
                  child: Text('아직 작성된 댓글이 없습니다'),
                )
              : ListView.builder(
                  itemCount: widget.comments.length,
                  itemBuilder: (context, index) {
                    var comment = widget.comments[index];
                    return Column(
                      children: [
                        _buildComment(comment, false), // for main comments
                        ..._buildReComments(
                            comment['reComment']), // for recomments
                      ],
                    );
                  },
                  shrinkWrap: true,
                  physics: NeverScrollableScrollPhysics(),
                ),
        ],
      ),
    );
  }

  Widget _buildComment(dynamic comment, bool isReComment) {
    return Container(
      padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
      decoration: BoxDecoration(
        border: Border(
          top: BorderSide(
            color: isReComment
                ? Colors.transparent
                : Colors.grey, // Remove top border for recomment
            width: 1.0,
          ),
        ),
      ),
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
                  child: Image.asset(
                    'assets/images/community/user.png',
                    width: 20,
                    height: 20,
                  ),
                ),
              ),
              Container(
                margin: EdgeInsets.only(left: 10),
                child: Text(
                  '익명',
                  style: TextStyle(
                    fontSize: 17,
                    fontWeight: FontWeight.w700,
                  ),
                ),
              ),
            ],
          ),
          SizedBox(height: 5),
          Text(
            comment['content'],
            style: TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
          ),
          SizedBox(height: 5),
          Text(
            comment['createdDate'],
            style: TextStyle(color: Colors.grey),
          )
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
          20,
          5,
          20,
          idx == reComments.length - 1 ? 10 : 5,
        ),
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
                    child: _buildComment(reComment, true),
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
