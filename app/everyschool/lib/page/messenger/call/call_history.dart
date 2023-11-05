import 'package:flutter/material.dart';

class CallHistory extends StatefulWidget {
  const CallHistory({super.key});

  @override
  State<CallHistory> createState() => _CallHistoryState();
}

class _CallHistoryState extends State<CallHistory> {
  @override
  final callList = [
    {'name': '이지혁', 'time': '2023.03.02 15:13', 'last': '아름다운 하늘을 봐볼래?'},
    {'name': '가라마', 'time': '2023.03.13 08:13', 'last': '우리 헤어지자....'},
    {'name': '고등어', 'time': '2023.03.17 15:13', 'last': '그러지마 제발'},
    {'name': '가자미', 'time': '2023.03.19 17:13', 'last': '돈노와 돈노와 !!!!!'}
  ];

  @override
  Widget build(BuildContext context) {
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
        return '${postDateTime.year}.${postDateTime.month}.${postDateTime.day}';
      }
    }

    return Container(
        child: ListView.builder(
            itemCount: callList.length,
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
                      Icon(Icons.phone_forwarded),
                      Expanded(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(15, 0, 0, 0),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Container(
                                child: Text(
                                  callList[index]['name'] as String,
                                  style: TextStyle(
                                    fontSize: 15,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Text(formatDateTime(callList[index]['time'] as String)),
                    ],
                  ),
                ),
              );
            }));
  }
}
