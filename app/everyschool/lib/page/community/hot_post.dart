import 'package:flutter/material.dart';

class HotPost extends StatefulWidget {
  const HotPost({super.key});

  @override
  State<HotPost> createState() => _HotPostState();
}

class _HotPostState extends State<HotPost> {
  var hotPost = [
    {
      'title': '선생님께 사과받고 싶습니다.',
      'content': '아니 진짜 살다살다 제가 잘못한건가요 아니 진짜 살다살다 제가 잘못한건가요',
      'comments': '22'
    },
    {
      'title': '계단에서 애정행각 그만해주세요',
      'content': '반으로 돌아가려는데 커플들 때문에 다른계단 아니 진짜 살다살다 제가 잘못한건가요',
      'comments': '13'
    },
  ];

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
              'HOT 게시글🔥🔥🔥',
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
                itemCount: hotPost.length,
                itemBuilder: (context, index) {
                  return Container(
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
                                (hotPost[index]['title'] as String).length > 15
                                    ? '${(hotPost[index]['title'] as String).substring(0, 15)}...'
                                    : hotPost[index]['title'] as String,
                                style: TextStyle(
                                  fontSize: 17,
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
                                formatText(hotPost[index]['content'] as String),
                                style: TextStyle(fontSize: 15),
                              ),
                            ),
                            Row(
                              children: [
                                Image.asset(
                                  'assets/images/community/comment.png',
                                  width: 25,
                                  height: 25,
                                ),
                                Text(
                                  hotPost[index]['comments'] as String,
                                  style: TextStyle(fontSize: 15),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ],
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
