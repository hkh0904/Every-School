import 'package:flutter/material.dart';

class SchoolNoti extends StatefulWidget {
  const SchoolNoti({super.key});

  @override
  State<SchoolNoti> createState() => _SchoolNotiState();
}

class _SchoolNotiState extends State<SchoolNoti> {
  var notiList = [
    {'title': '짧은공지제목', 'date': '2023.10.16'},
    {'title': '짧은공지제목짧은공지제목', 'date': '2023.10.16'},
    {'title': '긴공지제목긴공지제목긴공지제목', 'date': '2023.10.16'},
    {'title': '아주긴공지제목아주긴공지제목아주긴공지제목아주긴공지제목아주긴공지제목', 'date': '2023.10.16'},
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
          color: Color(0xffEAF7FF),
          borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20), topRight: Radius.circular(20))),
      child: Container(
        // padding: EdgeInsets.all(0),
        margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Text(
            '학교 공지사항',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
          ),
          Container(
              height: 200,
              margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
              decoration: BoxDecoration(
                color: Colors.grey[50],
                borderRadius: BorderRadius.circular(8),
                boxShadow: [
                  BoxShadow(
                    color: Colors.black.withOpacity(0.1), // 그림자 색상과 불투명도
                    spreadRadius: 1, // 그림자 확산 정도
                    blurRadius: 3, // 그림자의 흐림 정도
                    offset: Offset(2, 3), // 그림자 위치 (가로, 세로)
                  ),
                ],
              ),
              child: ListView.builder(
                  physics: NeverScrollableScrollPhysics(),
                  itemCount: notiList.length,
                  itemBuilder: (context, index) {
                    return Container(
                      height: 49,
                      padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      margin: EdgeInsets.fromLTRB(10, 0, 10, 0),
                      decoration: BoxDecoration(
                        border: Border(
                          bottom: BorderSide(
                            width: index == notiList.length - 1 ? 0 : 1,
                            color: index == notiList.length - 1
                                ? Colors.transparent
                                : Color(0xffd9d9d9),
                          ),
                        ),
                      ),
                      child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            SizedBox(
                              width: MediaQuery.of(context).size.width * 0.6,
                              child: Text(
                                notiList[index]['title'] as String,
                                maxLines: 2,
                                overflow: TextOverflow.ellipsis,
                                style: TextStyle(fontSize: 15),
                              ),
                            ),
                            Text(
                              notiList[index]['date'] as String,
                              style: TextStyle(color: Color(0xff999999)),
                            )
                          ]),
                    );
                  })),
        ]),
      ),
    );
  }
}
