import 'package:everyschool/api/messenger_api.dart';
import 'package:everyschool/page/messenger/call/modal_call_page.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class CallModal extends StatefulWidget {
  const CallModal(
      {super.key,
      this.join,
      this.uid,
      this.channelName,
      this.tokenRole,
      this.serverUrl,
      this.tokenExpireTime,
      this.isTokenExpiring,
      this.getUserKey});
  final join;
  final uid;
  final channelName;
  final tokenRole;
  final serverUrl;
  final tokenExpireTime;
  final isTokenExpiring;
  final getUserKey;

  @override
  State<CallModal> createState() => _CallModalState();
}

class _CallModalState extends State<CallModal> {
  final storage = FlutterSecureStorage();

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print('유저아이디 ${widget.uid}');
    print('채널이름 ${widget.channelName}');
    print('토큰롤 ${widget.tokenRole}');
    print('서버주소 ${widget.serverUrl}');
    print('토큰삭제시점 ${widget.tokenExpireTime}');
    print('삭제됐니? ${widget.isTokenExpiring}');
  }

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      contentPadding: EdgeInsets.zero,
      content: Container(
        width: MediaQuery.of(context).size.width * 0.6,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            SizedBox(
              height: 20,
            ),
            Text(
              '통화하기',
              style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
            ),
            SizedBox(
              height: 20,
            ),
            Text('모든 전화 통화는 녹음됩니다.'),
            Text('연결하시겠습니까?'),
            SizedBox(
              height: 20,
            ),
            Row(
              children: [
                Expanded(
                  child: GestureDetector(
                    onTap: () async {
                      final token = await storage.read(key: 'token') ?? "";

                      await widget.join(
                          widget.uid,
                          widget.channelName,
                          widget.tokenRole,
                          widget.serverUrl,
                          widget.tokenExpireTime,
                          widget.isTokenExpiring);

                      print('여기나갈거야');
                      // Navigator.of(context).pop();

                      if (widget.getUserKey == null) {
                        final contact =
                            await MessengerApi().getTeacherConnect(token);
                        final myInfo = await context.read<UserStore>().userInfo;
                        print('콘텍트 ${contact['userKey']}');
                        print('전화걸때 ${myInfo['name']}');
                        CallingApi().callOthers(token, contact['userKey'],
                            myInfo['name'], widget.channelName);
                      } else {
                        final myInfo = await context.read<UserStore>().userInfo;
                        CallingApi().callOthers(token, widget.getUserKey,
                            myInfo['name'], widget.channelName);
                      }
                    },
                    child: Container(
                      color: Color(0xff15075F),
                      height: 50,
                      child: Center(
                          child: Text(
                        '연결하기',
                        style: TextStyle(
                            color: Colors.white, fontWeight: FontWeight.w600),
                      )),
                    ),
                  ),
                ),
                Expanded(
                    child: GestureDetector(
                  onTap: () {
                    Navigator.of(context).pop();
                  },
                  child: Container(
                    decoration: BoxDecoration(
                        border:
                            Border(top: BorderSide(color: Color(0xffd9d9d9)))),
                    height: 50,
                    child: Center(child: Text('취소')),
                  ),
                ))
              ],
            )
          ],
        ),
      ),
    );
  }
}
