import 'package:flutter/material.dart';

class PostComments extends StatefulWidget {
  final List<dynamic> comments;

  // Constructor for receiving comments
  PostComments({Key? key, required this.comments}) : super(key: key);

  @override
  State<PostComments> createState() => _PostCommentsState();
}

class _PostCommentsState extends State<PostComments> {
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      // SingleChildScrollView 추가
      child: Column(
        children: [
          ListView.builder(
            itemCount: widget.comments.length,
            itemBuilder: (context, index) {
              var comment = widget.comments[index];
              return Column(
                children: [
                  _buildComment(comment, false), // for main comments
                  ..._buildReComments(comment['reComment']), // for recomments
                ],
              );
            },
            shrinkWrap: true,
            physics:
                NeverScrollableScrollPhysics(), // ListView.builder의 스크롤 비활성화
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

  List<Widget> _buildReComments(List<dynamic> reComments) {
    return reComments.asMap().entries.map((entry) {
      int idx = entry.key;
      var reComment = entry.value;

      return Container(
        padding: EdgeInsets.fromLTRB(
          20,
          5,
          20,
          idx == reComments.length - 1 ? 10 : 5, // 마지막 요소인 경우 bottom margin 추가
        ),
        child: Column(
          children: [
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Icon(
                  Icons.subdirectory_arrow_right,
                  size: 30,
                ),
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
            ), // for recomments
          ],
        ),
      );
    }).toList();
  }
}
