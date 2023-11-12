import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/call_detail.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:provider/provider.dart';

class CallHistory extends StatefulWidget {
  const CallHistory({super.key});

  @override
  State<CallHistory> createState() => _CallHistoryState();
}

class _CallHistoryState extends State<CallHistory> {
  callList() async {
    var response = await CallingApi().getCallList();

    return response;
  }

  setCallIcon(senderName, receiverName, receiveCall) async {
    final myInfo = await context.read<UserStore>().userInfo;
    if (myInfo['name'] == senderName && receiveCall == 'Y') {
      return Icon(Icons.phone_forwarded);
    } else if (myInfo['name'] == receiverName && receiveCall == 'Y') {
      return Icon(Icons.phone_callback_sharp);
    } else if (receiveCall == 'M') {
      return Icon(Icons.phone_missed);
    } else {
      return Icon(Icons.call_end);
    }
  }

  setCallText(senderName, receiverName, receiveCall) async {
    final myInfo = await context.read<UserStore>().userInfo;
    if (myInfo['name'] == senderName) {
      return Text(
        receiverName,
        style: TextStyle(fontSize: 15, fontWeight: FontWeight.w700),
      );
    } else if (myInfo['name'] == receiverName) {
      return Text(senderName,
          style: TextStyle(fontSize: 15, fontWeight: FontWeight.w700));
    }
  }

  String formatDateTime(DateTime dateTime) {
    // 오늘 날짜인지 확인
    bool isToday = DateTime.now().day == dateTime.day &&
        DateTime.now().month == dateTime.month &&
        DateTime.now().year == dateTime.year;

    if (isToday) {
      // 오늘인 경우 시간으로 포맷팅
      return DateFormat.jm().format(dateTime);
    } else {
      // 오늘이 아닌 경우 날짜로 포맷팅
      return DateFormat('yyyy.MM.dd').format(dateTime);
    }
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: callList(),
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        if (snapshot.hasData) {
          return snapshot.data.length == 0
              ? Center(child: Text('통화 기록이 없습니다.'))
              : Container(
                  child: ListView.builder(
                    itemCount: snapshot.data.length,
                    itemBuilder: (context, index) {
                      return GestureDetector(
                        onTap: () async {
                          var detail = await CallingApi().getCallDetail(
                              snapshot.data[index]['userCallId']);
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) =>
                                      CallDetail(detail: detail)));
                        },
                        child: Container(
                          padding: EdgeInsets.fromLTRB(20, 15, 20, 10),
                          decoration: BoxDecoration(
                            color: Colors.grey[50],
                            border: Border(
                              bottom: BorderSide(
                                color: Colors.grey.shade400,
                                width: 1.0,
                              ),
                            ),
                          ),
                          height: 80,
                          child: Row(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                children: [
                                  FutureBuilder(
                                      future: setCallIcon(
                                          snapshot.data[index]['senderName'],
                                          snapshot.data[index]['receiverName'],
                                          snapshot.data[index]
                                                  .containsKey('receiveCall')
                                              ? snapshot.data[index]
                                                  ['receiveCall']
                                              : 'Y'),
                                      builder: (BuildContext context,
                                          AsyncSnapshot snapshot) {
                                        if (snapshot.hasData) {
                                          return snapshot.data;
                                        } else if (snapshot.hasError) {
                                          return Padding(
                                            padding: const EdgeInsets.all(8.0),
                                            child: Text(
                                              'Error: ${snapshot.error}',
                                              style: TextStyle(fontSize: 15),
                                            ),
                                          );
                                        } else {
                                          return Container(
                                            height: 60,
                                          );
                                        }
                                      }),
                                  Padding(
                                    padding:
                                        const EdgeInsets.fromLTRB(15, 0, 10, 0),
                                    child: FutureBuilder(
                                        future: setCallText(
                                            snapshot.data[index]['senderName'],
                                            snapshot.data[index]
                                                ['receiverName'],
                                            snapshot.data[index]
                                                    .containsKey('receiveCall')
                                                ? snapshot.data[index]
                                                    ['receiveCall']
                                                : 'Y'),
                                        builder: (BuildContext context,
                                            AsyncSnapshot snapshot) {
                                          if (snapshot.hasData) {
                                            return snapshot.data;
                                          } else if (snapshot.hasError) {
                                            return Padding(
                                              padding:
                                                  const EdgeInsets.all(8.0),
                                              child: Text(
                                                'Error: ${snapshot.error}',
                                                style: TextStyle(fontSize: 15),
                                              ),
                                            );
                                          } else {
                                            return Container(
                                              height: 60,
                                            );
                                          }
                                        }),
                                  ),
                                  if (snapshot.data[index]['isBad'] == true)
                                    Tooltip(
                                      message: 'AI가 악성민원으로 판단한 통화입니다.',
                                      child: Container(
                                        child: Icon(
                                          Icons.error_outline,
                                          color: Colors.red,
                                        ),
                                      ),
                                      triggerMode: TooltipTriggerMode.tap,
                                    ),
                                ],
                              ),
                              Text(formatDateTime(DateTime.parse(snapshot
                                      .data[index]
                                      .containsKey('startDateTime')
                                  ? snapshot.data[index]['startDateTime']
                                  : snapshot.data[index]['endDateTime']))),
                            ],
                          ),
                        ),
                      );
                    },
                  ),
                );
        } else if (snapshot.hasError) {
          return Padding(
            padding: const EdgeInsets.all(8.0),
            child: Text(
              'Error: ${snapshot.error}',
              style: TextStyle(fontSize: 15),
            ),
          );
        } else {
          return Container(
            height: 800,
          );
        }
      },
    );
  }
}
