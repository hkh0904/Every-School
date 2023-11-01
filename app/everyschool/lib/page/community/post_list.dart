import 'package:flutter/material.dart';

class PostList extends StatefulWidget {
  const PostList({super.key});

  @override
  State<PostList> createState() => _PostListState();
}

class _PostListState extends State<PostList> {
  var postList = [
    {
      'title': '선생님께 사과받고 싶습니다.',
      'content': '아니 진짜 살다살다 제가 잘못한건가요 아니 진짜 살다살다 제가 잘못한건가요',
      'time': '2023-10-31-20:54',
      'comments': 22
    },
    {
      'title': '계단에서 애정행각 그만해주세요',
      'content': '반으로 돌아가려는데 커플들 때문에 다른계단 아니 진짜 살다살다 제가 잘못한건가요',
      'time': '2023-10-31-19:32',
      'comments': 13
    },
    {
      'title': '플러터 괄호지옥',
      'content': '페이지 만드는데 뭔놈의 괄호가 이렇게 많은지 나중에 위젯하나 추가하려하면 괄호 짝을 맞출수가 없어요...',
      'time': '2023-10-31-15:14',
      'comments': 4
    },
    {
      'title': '3-3반 OOO 너무 예뻐요',
      'content': '너무 예쁘세요!',
      'time': '2023-10-31-15:14',
      'comments': 0
    },
    {
      'title': '선생님께 사과받고 싶습니다.',
      'content': '아니 진짜 살다살다 제가 잘못한건가요 아니 진짜 살다살다 제가 잘못한건가요',
      'time': '2023-10-30-20:54',
      'comments': 22
    },
    {
      'title': '계단에서 애정행각 그만해주세요',
      'content': '반으로 돌아가려는데 커플들 때문에 다른계단 아니 진짜 살다살다 제가 잘못한건가요',
      'time': '2023-10-30-19:32',
      'comments': 13
    },
    {
      'title': '플러터 괄호지옥',
      'content': '페이지 만드는데 뭔놈의 괄호가 이렇게 많은지 나중에 위젯하나 추가하려하면 괄호 짝을 맞출수가 없어요...',
      'time': '2023-10-29-15:14',
      'comments': 4
    },
    {
      'title': '3-3반 OOO 너무 예뻐요',
      'content': '너무 예쁘세요!',
      'time': '2023-10-28-15:14',
      'comments': 0
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
    // -를 기준으로 문자열을 분리
    List<String> parts = dateTimeStr.split('-');

    // 년, 월, 일 부분은 그대로 사용하고 시간 부분을 분리
    String year = parts[0];
    String month = parts[1];
    String day = parts[2];
    String time = parts[3];

    // 시간 부분을 콜론(:)을 기준으로 분리하여 시간과 분을 구함
    List<String> timeParts = time.split(':');
    String hour = timeParts[0];
    String minute = timeParts[1];

    // YYYY-MM-DDTHH:mm 형식의 문자열로 재구성
    String formattedDateTimeStr = '$year-$month-${day}T$hour:$minute:00';

    // DateTime 객체로 변환
    DateTime postDateTime;
    try {
      postDateTime = DateTime.parse(formattedDateTimeStr);
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
      return '${postDateTime.month}/${postDateTime.day}';
    }
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: postList.length,
      itemBuilder: (context, index) {
        return Container(
          padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
          decoration: BoxDecoration(
            border: Border(
              bottom: BorderSide(
                color: Colors.grey.shade400,
                width: 1.0,
              ),
            ),
          ),
          child: Column(
            children: [
              // Row(
              //   mainAxisAlignment: MainAxisAlignment.start,
              //   children: [
              //     Container(
              //       width: 30,
              //       height: 30,
              //       decoration: BoxDecoration(
              //         color: Colors.grey,
              //         borderRadius: BorderRadius.all(Radius.circular(8)),
              //       ),
              //       child: Align(
              //         alignment: Alignment.center,
              //         child: Image.asset(
              //           'assets/images/community/user.png',
              //           width: 20,
              //           height: 20,
              //         ),
              //       ),
              //     ),
              //     Container(
              //       margin: EdgeInsets.only(left: 10),
              //       child: Text(
              //         '익명',
              //         style: TextStyle(
              //           fontSize: 17,
              //           fontWeight: FontWeight.w700,
              //         ),
              //       ),
              //     ),
              //   ],
              // ),
              // SizedBox(
              //   height: 10,
              // ),
              Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Container(
                          margin: EdgeInsets.only(bottom: 5),
                          child: Text(
                            postList[index]['title'] as String,
                            style: TextStyle(
                              fontSize: 19,
                              fontWeight: FontWeight.w700,
                            ),
                          ),
                        ),
                        Text(
                          formatText(postList[index]['content'] as String),
                          style: TextStyle(fontSize: 15),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              SizedBox(height: 5),
              Row(
                children: [
                  (postList[index]['comments'] as int) != 0
                      ? Row(
                          children: [
                            Image.asset(
                              'assets/images/community/comment.png',
                              width: 25,
                              height: 25,
                            ),
                            Text(
                              ' ${postList[index]['comments'].toString()}',
                              style: TextStyle(
                                  fontSize: 15, color: Color(0XFF32D9FE)),
                            ),
                            Text(
                              ' | ',
                              style:
                                  TextStyle(color: Colors.grey, fontSize: 18),
                            ),
                          ],
                        )
                      : Container(),
                  Text(formatDateTime(postList[index]['time'] as String)),
                  Text(
                    ' | ',
                    style: TextStyle(color: Colors.grey, fontSize: 18),
                  ),
                  Text('익명'),
                ],
              ),
            ],
          ),
        );
      },
    );
  }
}
