import 'package:flutter/material.dart';

class PopularPost extends StatefulWidget {
  const PopularPost({super.key});

  @override
  State<PopularPost> createState() => _PopularPostState();
}

class _PopularPostState extends State<PopularPost> {
  var popPost = [
    {
      'title': '선생님께 사과받고 싶습니다.',
      'content': '아니 진짜 살다살다 제가 잘못한건가요',
      'comments': 22
    },
    {
      'title': '계단에서 애정행각 그만해주세요',
      'content': '반으로 돌아가려는데 커플들 때문에 다른계단',
      'comments': 13
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20))),
      child: Container(
        // padding: EdgeInsets.all(0),
        margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(
            '오늘의 인기글',
            style: TextStyle(fontSize: 22, fontWeight: FontWeight.w800),
          ),
          Container(
              height: 200,
              margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
              decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.grey.shade400, // 테두리 색상
                  width: 1, // 테두리 두께
                ),
                borderRadius: BorderRadius.circular(8),
              ),
              child: ListView.builder(
                  physics: NeverScrollableScrollPhysics(),
                  itemCount: popPost.length,
                  itemBuilder: (context, index) {
                    return Container(
                      height: 38,
                      padding: EdgeInsets.fromLTRB(10, 10, 10, 0),
                      margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            SizedBox(
                              width: MediaQuery.of(context).size.width * 0.6,
                              child: Text(
                                (popPost[index]['title'] as String).length > 15
                                    ? '${(popPost[index]['title'] as String).substring(0, 15)}...'
                                    : popPost[index]['title'] as String,
                                style: TextStyle(fontSize: 17),
                              ),
                            ),
                            // Text(
                            //   popPost[index]['date'] as String,
                            //   style: TextStyle(color: Color(0xff999999)),
                            // ),
                            Image.asset('assets/images/community/new.png'),
                          ]),
                    );
                  })),
        ]),
      ),
    );
  }
}
