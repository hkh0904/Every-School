import 'package:flutter/material.dart';

class PostlistPage extends StatefulWidget {
  const PostlistPage({Key? key}) : super(key: key);

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
          elevation: 0,
          leading: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              BackButton(
                color: Colors.black,
                onPressed: () {
                  Navigator.pop(context); // 이전 페이지로 돌아가기
                },
              ),
              Container(
                margin: EdgeInsets.only(left: 20),
                child: Text(
                  '자유 게시판',
                  style: TextStyle(color: Colors.black, fontSize: 20),
                ),
              ),
            ],
          ),
        ),
        body: CustomScrollView(
          slivers: [
            SliverList(
              delegate: SliverChildListDelegate(
                [
                  Container(
                    color: Colors.grey[50],
                    width: MediaQuery.of(context).size.width,
                    child: Padding(
                      padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text('자녀 선택하기',
                              style: TextStyle(
                                  fontSize: 24, fontWeight: FontWeight.w700)),
                          Image.asset('assets/images/home/select_deco.png'),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
