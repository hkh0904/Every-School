import 'package:flutter/material.dart';
import 'package:everyschool/page/community/post_list.dart';
import 'package:everyschool/page/community/create_post.dart';

class PostlistPage extends StatefulWidget {
  final String pageTitle;

  // 생성자에서 pageTitle 매개변수를 받습니다.
  const PostlistPage({Key? key, required this.pageTitle}) : super(key: key);

  @override
  State<PostlistPage> createState() => _PostlistPageState();
}

class _PostlistPageState extends State<PostlistPage> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context); // 이전 페이지로 돌아가기
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 1,
          centerTitle: true,
          leading: IconButton(
            iconSize: 30,
            icon: Icon(Icons.arrow_back, color: Color(0XFF15075F)),
            onPressed: () {
              Navigator.pop(context); // 이전 페이지로 돌아가기
            },
          ),
          title: Text(
            widget.pageTitle, // 여기에서 pageTitle을 사용합니다.
            style: TextStyle(
              color: Colors.black,
              fontSize: 24,
              fontWeight: FontWeight.w700,
            ),
          ),
          actions: [
            IconButton(
              iconSize: 30,
              icon: Icon(
                Icons.add,
                color: Color(0XFF15075F),
              ),
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => CreatePost()), // CreatePost 페이지로 이동
                );
              },
            ),
          ],
        ),
        body: PostList(pageTitle: widget.pageTitle), // PostList 위젯을 직접 호출
      ),
    );
  }
}
