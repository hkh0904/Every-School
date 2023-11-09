import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ChatList extends StatefulWidget {
  const ChatList({super.key, this.chatList});

  final chatList;

  @override
  State<ChatList> createState() => _ChatListState();
}

class _ChatListState extends State<ChatList> {
  final storage = FlutterSecureStorage();

  String formatText(String text) {
    if (text.length > 10) {
      text = '${text.substring(0, 10)}...';
    }
    // 이미 문자열이 30자를 초과하더라도, 10자가 넘으면 여전히 줄바꿈을 적용합니다.
    // if (text.length > 15) {
    //   int breakIndex = text.indexOf(' ', 15);
    //   if (breakIndex != -1) {
    //     text =
    //         '${text.substring(0, breakIndex)}\n${text.substring(breakIndex + 1)}';
    //   }
    // }
    return text;
  }

  String formatDateTime(String dateTimeStr) {
    // 공백과 점을 기준으로 문자열을 분리
    List<String> parts = dateTimeStr.split('T');
    List<String> dateParts = parts[0].split('-');
    List<String> timeParts = parts[1].split(':');

    // 년, 월, 일 부분은 그대로 사용하고 시간 부분을 분리
    String year = dateParts[0];
    String month = dateParts[1];
    String day = dateParts[2];
    String time = timeParts[0];
    String min = timeParts[1];

    // DateTime 객체로 변환
    DateTime postDateTime;
    try {
      postDateTime = DateTime.parse('$year-$month-$day $time:$min');
    } catch (e) {
      print('DateTime parsing error: $e');
      return dateTimeStr.substring(0, 10); // parsing에 실패하면 원래 문자열을 반환
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

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print('제발 쳇리스트 ${widget.chatList}');
  }

  @override
  Widget build(BuildContext context) {
    return widget.chatList == null
        ? Container()
        : Container(
            child: ListView.builder(
                itemCount: widget.chatList.length,
                itemBuilder: (context, index) {
                  return GestureDetector(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) =>
                              ChatRoom(roomInfo: widget.chatList[index]),
                        ),
                      );
                    },
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
                                      widget.chatList[index]['roomTitle']
                                          as String,
                                      style: TextStyle(
                                        fontSize: 15,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                  ),
                                  SizedBox(
                                    child: Text(
                                      // formatText(widget.chatList[index]
                                      //     ['lastMessage'] as String),
                                      '갈아 끼워야함',
                                      style: TextStyle(fontSize: 13),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          Text(formatDateTime(
                              widget.chatList[index]['updateTime'] as String)),
                        ],
                      ),
                    ),
                  );
                }));
  }
}
