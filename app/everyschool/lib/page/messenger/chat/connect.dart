import 'package:everyschool/page/messenger/chat/chat_room.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Connect extends StatefulWidget {
  const Connect({super.key, this.userConnect});

  final userConnect;

  @override
  State<Connect> createState() => _ConnectState();
}

class _ConnectState extends State<Connect> {
  @override
  Widget build(BuildContext context) {
    return widget.userConnect == null
        ? Center(
            child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text('연락처가 없습니다'),
              Text('관리자에게 문의하세요'),
            ],
          ))
        : Container(
            child: ListView.builder(
                itemCount: widget.userConnect.length,
                itemBuilder: (context, index) {
                  return GestureDetector(
                    onTap: () {
                      // Navigator.push(
                      //   context,
                      //   MaterialPageRoute(
                      //     builder: (context) =>
                      //         ChatRoom(roomInfo: widget.chatList[index]),
                      //   ),
                      // );
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
                                      widget.userConnect[index]['name']
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
                          TextButton(
                            child: Text('연락하기'),
                            onPressed: () {
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => ChatRoom(
                                          roomInfo: null,
                                          userInfo:
                                              widget.userConnect[index])));
                            },
                          )
                        ],
                      ),
                    ),
                  );
                }));
  }
}
