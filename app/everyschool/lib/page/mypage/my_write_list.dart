import 'package:flutter/material.dart';

class MyWriteList extends StatefulWidget {
  const MyWriteList({super.key});

  @override
  State<MyWriteList> createState() => _MyWriteListState();
}

class _MyWriteListState extends State<MyWriteList> {
  final List<dynamic> _postList = [
    {
      'title': '오늘의 식탁은 채움입니다.',
      'time': '2023.11.27 20:54',
      'isTapped': false,
      'comments': 0
    },
    {
      'title': '퇴근시간은 18시 입니다.',
      'time': '2023.10.31 20:54',
      'isTapped': false,
      'comments': 0
    },
    {
      'title': '내일 날씨는 흐림!!!!',
      'time': '2023.12.31 20:54',
      'isTapped': false,
      'comments': 4
    },
    {
      'title': '108조를 칭찬합니다. 모두들 너무 착하고 일을 열심히 해요.',
      'time': '2023.4.3 20:54',
      'isTapped': false,
      'comments': 4
    },
    {
      'title': '마지막으로 비웁시다.',
      'time': '2023.1.21 20:54',
      'isTapped': false,
      'comments': 4
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

  String formatDateTime(String dateTimeStr) {
    // 공백과 점을 기준으로 문자열을 분리
    List<String> parts = dateTimeStr.split(' ');
    List<String> dateParts = parts[0].split('.');

    // 년, 월, 일 부분은 그대로 사용하고 시간 부분을 분리
    String year = dateParts[0];
    String month = dateParts[1];
    String day = dateParts[2];
    String time = parts[1];

    // DateTime 객체로 변환
    DateTime postDateTime;
    try {
      postDateTime = DateTime.parse('$year-$month-$day $time');
    } catch (e) {
      print('DateTime parsing error: $e');
      return dateTimeStr; // parsing에 실패하면 원래 문자열을 반환
    }

    // 현재 날짜와 시간 가져오기
    DateTime now = DateTime.now();

    // 입력된 날짜가 오늘이면 시간만 반환, 그렇지 않으면 월-일 반환
    if (postDateTime.day == now.day &&
        postDateTime.month == now.month &&
        postDateTime.year == now.year) {
      return '${postDateTime.hour.toString().padLeft(2, '0')}:${postDateTime.minute.toString().padLeft(2, '0')}';
    } else {
      return '${postDateTime.month}-${postDateTime.day}';
    }
  }

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 2,
        backgroundColor: Colors.grey[50],
        leading: BackButton(color: Colors.black),
        centerTitle: true,
        title: Text(
          '내가 작성한 글',
          style: TextStyle(
              color: Colors.black, fontSize: 18, fontWeight: FontWeight.w700),
        ),
      ),
      body: Container(
        child: Column(
          children: List.generate(
            _postList.length,
            (index) {
              return GestureDetector(
                // onTap: () {
                //   Navigator.push(
                //     context,
                //     MaterialPageRoute(
                //         builder: (context) => perPagelist[userNum - 1][index]),
                //   );
                // },
                child: Container(
                  height: 100,
                  padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
                  decoration: BoxDecoration(
                    color: (_postList[index]['isTapped'] == true)
                        ? Colors.grey[200]
                        : Colors.white,
                    border: Border(
                      bottom: BorderSide(
                        color: Colors.grey.shade400,
                        width: 1.0,
                      ),
                    ),
                  ),
                  child: Row(
                    children: [
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Container(
                              margin: EdgeInsets.only(bottom: 5),
                              child: Text(
                                _postList[index]['title'] as String,
                                style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.w700,
                                ),
                              ),
                            ),
                            Text(formatDateTime(
                                _postList[index]['time'] as String)),
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
      ),
    );
  }
}
