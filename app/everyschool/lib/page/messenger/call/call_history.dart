import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
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
                                  padding:
                                      const EdgeInsets.fromLTRB(15, 0, 0, 0),
                                  child: Column(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Container(
                                        child: Text(
                                          snapshot.data[index]['senderName']
                                              as String,
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
                              Text('시간'),
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
