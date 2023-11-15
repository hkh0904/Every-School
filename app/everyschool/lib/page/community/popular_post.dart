import 'package:everyschool/store/user_store.dart';
import 'package:everyschool/api/community_api.dart';
import 'package:everyschool/page/community/post_detail.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';

class PopularPost extends StatefulWidget {
  const PopularPost({super.key});

  @override
  State<PopularPost> createState() => _PopularPostState();
}

class _PopularPostState extends State<PopularPost> {
  final CommunityApi communityApi = CommunityApi();
  List<dynamic> popPost = [];

  @override
  void initState() {
    super.initState();
    _loadBoardData();
  }

  Future<void> _loadBoardData() async {
    late final schoolYear =
        context.read<UserStore>().userInfo["schoolClass"]["schoolYear"];
    late final schoolId =
        context.read<UserStore>().userInfo["school"]["schoolId"];

    List<dynamic> combinedPosts = []; // 여러 페이지의 데이터를 저장할 리스트

    try {
      for (int i = 1; i <= 3; i++) {
        // 1부터 3까지의 페이지를 순회
        var response = await communityApi.getBoardList(schoolYear, schoolId, i);
        if (response != null &&
            response['content'] != null &&
            response['content'].isNotEmpty) {
          combinedPosts
              .addAll(response['content']); // 각 페이지의 content를 combinedPosts에 추가
        }
      }
      var scoredPosts = combinedPosts.map((post) {
        int score =
            (post['commentCount'] as int) * 3 + (post['scrapCount'] as int) * 2;
        return {'post': post, 'score': score};
      }).toList();

      // 점수가 높은 순으로 정렬
      scoredPosts.sort((a, b) => b['score'].compareTo(a['score']));

      // 상위 2개의 게시물만 선택
      List<dynamic> topPosts =
          scoredPosts.take(2).map((e) => e['post']).toList();

      setState(() {
        popPost = topPosts; // 상위 2개의 게시물을 popPost에 저장
      });
    } catch (e) {
      print('커뮤니티 보드 에러: $e');
    }
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

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20))),
      child: Container(
        margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '오늘의 인기글 ✨',
              style: TextStyle(fontSize: 22, fontWeight: FontWeight.w800),
            ),
            Container(
              height: 280,
              margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade400,
                  width: 1,
                ),
                borderRadius: BorderRadius.circular(8),
              ),
              child: ListView.builder(
                physics: NeverScrollableScrollPhysics(),
                itemCount: popPost.length,
                itemBuilder: (context, index) {
                  return GestureDetector(
                    onTapDown: (TapDownDetails details) {
                      setState(() {
                        popPost[index]['isTapped'] = true;
                      });
                    },
                    onTapCancel: () {
                      setState(() {
                        popPost[index]['isTapped'] = false;
                      });
                    },
                    onTapUp: (TapUpDetails details) {
                      setState(() {
                        popPost[index]['isTapped'] = false;
                      });
                      if (popPost[index]['boardId'] != null) {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => PostDetail(
                                boardName: '자유게시판',
                                boardId: popPost[index]['boardId']),
                          ),
                        ).then((check) {
                          if (check == 'refresh') {
                            _loadBoardData();
                          }
                        });
                      }
                    },
                    child: Container(
                      height: 130,
                      padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: Column(
                        children: [
                          SizedBox(
                            height: 20,
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Container(
                                width: 30,
                                height: 30,
                                decoration: BoxDecoration(
                                  color: Colors.grey,
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(8)),
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
                          SizedBox(
                            height: 10,
                          ),
                          Row(
                            children: [
                              Expanded(
                                child: Text(
                                  (popPost[index]['title'] as String).length >
                                          15
                                      ? '${(popPost[index]['title'] as String).substring(0, 15)}...'
                                      : popPost[index]['title'] as String,
                                  style: TextStyle(
                                    fontSize: 21,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(
                            height: 5,
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Expanded(
                                child: Text(
                                  formatText(
                                      popPost[index]['content'] as String),
                                  style: TextStyle(fontSize: 18),
                                ),
                              ),
                              Row(
                                children: [
                                  Icon(
                                    Icons.comment_outlined,
                                    color: Colors.cyan[400],
                                    size: 22,
                                  ),
                                  Text(
                                    ' ${popPost[index]['commentCount'].toString()}',
                                    style: TextStyle(
                                        fontSize: 16, color: Colors.cyan[400]),
                                  ),
                                  SizedBox(width: 5),
                                  Icon(
                                    Icons.favorite,
                                    color: Color.fromARGB(255, 255, 108, 152),
                                    size: 22,
                                  ),
                                  SizedBox(width: 2),
                                  Text(
                                    '${popPost[index]['scrapCount'].toString()}',
                                    style: TextStyle(
                                        fontSize: 16,
                                        color:
                                            Color.fromARGB(255, 255, 108, 152)),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
