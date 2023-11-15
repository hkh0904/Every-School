import 'package:flutter/material.dart';
import 'package:everyschool/page/community/post_list.dart';
import 'package:everyschool/page/community/create_post.dart';

class PostlistPage extends StatefulWidget {
  final String pageTitle;
  const PostlistPage({Key? key, required this.pageTitle}) : super(key: key);

  @override
  State<PostlistPage> createState() => _PostlistPageState();
}

class _PostlistPageState extends State<PostlistPage> {
  // 새로고침을 위한 플래그
  bool needRefresh = false;

  void refreshPostList() {
    setState(() {
      needRefresh = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context, 'refresh'); // 이전 페이지로 돌아가기
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 1,
          centerTitle: true,
          leading: IconButton(
            icon: Icon(Icons.arrow_back),
            onPressed: () {
              Navigator.pop(context, 'refresh'); // 이전 페이지로 돌아가기
            },
          ),
          title: Text(
            widget.pageTitle, // 여기에서 pageTitle을 사용합니다.
            style: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.w700,
            ),
          ),
          actions: [
            IconButton(
              iconSize: 25,
              icon: Icon(
                Icons.add,
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => CreatePost()), // CreatePost 페이지로 이동
                ).then((check) {
                  if (check == 'refresh') {
                    refreshPostList();
                  }
                });
              },
            ),
          ],
        ),
        body: PostList(
          key: UniqueKey(), // 새로고침을 위해 UniqueKey 사용
          pageTitle: widget.pageTitle,
          needRefresh: needRefresh,
        ),
      ),
    );
  }
}
