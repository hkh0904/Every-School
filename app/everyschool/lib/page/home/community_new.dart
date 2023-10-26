import 'package:flutter/material.dart';

class CommunityNew extends StatefulWidget {
  const CommunityNew({super.key});

  @override
  State<CommunityNew> createState() => _CommunityNewState();
}

class _CommunityNewState extends State<CommunityNew> {
  var notiList = [
    {'title': '짧은커뮤니티글제목', 'date': '2023.10.16'},
    {'title': '짧은커뮤니티글제목짧은커뮤니티글제목', 'date': '2023.10.16'},
    {'title': '긴커뮤니티글제목긴커뮤니티글제목긴커뮤니티글제목', 'date': '2023.10.16'},
    {'title': '짧은커뮤니티글제목', 'date': '2023.10.16'},
  ];
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(20, 30, 20, 10),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(
          '커뮤니티 최신글',
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
        ),
        Container(
            height: 200,
            margin: EdgeInsets.fromLTRB(0, 15, 0, 10),
            decoration: BoxDecoration(
                color: Colors.grey[50],
                borderRadius: BorderRadius.circular(8),
                border: Border.all(color: Color(0xffD9D9D9), width: 1)),
            child: ListView.builder(
                physics: NeverScrollableScrollPhysics(),
                itemCount: notiList.length,
                itemBuilder: (context, index) {
                  return Container(
                    height: 50,
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
    );
  }
}
