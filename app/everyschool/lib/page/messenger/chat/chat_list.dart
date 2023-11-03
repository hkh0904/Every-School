import 'package:flutter/material.dart';

class ChatList extends StatefulWidget {
  const ChatList({super.key});

  @override
  State<ChatList> createState() => _ChatListState();
}

class _ChatListState extends State<ChatList> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  final chatList = [
    {'name': '이지혁', 'time': '2023.03.02 15:13', 'last': '아름다운 하늘을 봐볼래?'},
    {'name': '가라마', 'time': '2023.03.13 08:13', 'last': '우리 헤어지자....'},
    {'name': '고등어', 'time': '2023.03.17 15:13', 'last': '그러지마 제발'},
    {'name': '가자미', 'time': '2023.03.19 17:13', 'last': '돈노와 돈노와 !!!!!'}
  ];

  @override
  Widget build(BuildContext context) {
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
        return '${postDateTime.year}-${postDateTime.month}-${postDateTime.day}';
      }
    }

    return Container(
        child: ListView.builder(
            itemCount: chatList.length,
            itemBuilder: (context, index) {
              return GestureDetector(
                child: Container(
                  padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
                  decoration: BoxDecoration(
                    color: Colors.grey[50],
                    // border: Border(
                    //   bottom: BorderSide(
                    //     color: Colors.grey.shade400,
                    //     width: 1.0,
                    //   ),
                    // ),
                  ),
                  height: 80,
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Container(
                        decoration: BoxDecoration(
                            border: Border.all(color: Color(0xffd9d9d9)),
                            borderRadius: BorderRadius.circular(100)),
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(100),
                          child: Image.asset(
                            'assets/images/community/user.png',
                            width: 35,
                            height: 35,
                            color: Colors.black,
                          ),
                        ),
                      ),
                      Expanded(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(15, 0, 0, 0),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Container(
                                child: Text(
                                  chatList[index]['name'] as String,
                                  style: TextStyle(
                                    fontSize: 15,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ),
                              SizedBox(
                                child: Text(
                                  formatText(chatList[index]['last'] as String),
                                  style: TextStyle(fontSize: 13),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Text(formatDateTime(chatList[index]['time'] as String)),
                    ],
                  ),
                ),
              );
            }));
  }
}
