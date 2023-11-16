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
        popPost = topPosts;
        print(popPost);
      });
    } catch (e) {
      print('커뮤니티 보드 에러: $e');
    }
  }

  String formatText(String content) {
    // 줄바꿈을 공백으로 대체
    String combinedText = content.replaceAll('\n', ' ');

    // 합친 문자열이 30자를 초과하면 30자로 제한
    if (combinedText.length > 45) {
      combinedText = combinedText.substring(0, 45);
    }

    // 20자 이상이면 줄바꿈을 적용
    if (combinedText.length > 20) {
      int breakIndex = combinedText.indexOf(' ', 20);
      if (breakIndex != -1) {
        combinedText =
            '${combinedText.substring(0, breakIndex)}\n${combinedText.substring(breakIndex + 1)}...';
        ;
      }
    }
    return combinedText;
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
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
            ),
            Container(
              height: 255,
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
                      height: 120,
                      padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: Column(
                        children: [
                          SizedBox(
                            height: 16,
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            crossAxisAlignment: CrossAxisAlignment.end,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  Container(
                                    width: 24,
                                    height: 24,
                                    decoration: BoxDecoration(
                                      color: Colors.grey,
                                      borderRadius:
                                          BorderRadius.all(Radius.circular(6)),
                                    ),
                                    child: Align(
                                      alignment: Alignment.center,
                                      child: Image.asset(
                                        'assets/images/community/user.png',
                                        width: 18,
                                        height: 18,
                                      ),
                                    ),
                                  ),
                                  Container(
                                    margin: EdgeInsets.only(left: 10),
                                    child: Text(
                                      '익명',
                                      style: TextStyle(
                                        fontSize: 15,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.end,
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
                                    fontSize: 18,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(
                            height: 5,
                          ),
                          Expanded(
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  formatText(
                                      popPost[index]['content'] as String),
                                  style: TextStyle(fontSize: 16),
                                ),
                              ],
                            ),
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
