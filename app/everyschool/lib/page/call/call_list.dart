import 'dart:math';

import 'package:flutter/material.dart';

class CallList extends StatefulWidget {
  const CallList({super.key, this.join, this.leave});
  final join;
  final leave;

  @override
  State<CallList> createState() => _CallListState();
}

class _CallListState extends State<CallList> {
  String generateRandomString(int length) {
    const String charset =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    final Random random = Random();
    final StringBuffer buffer = StringBuffer();

    for (int i = 0; i < length; i++) {
      final int randomIndex = random.nextInt(charset.length);
      buffer.write(charset[randomIndex]);
    }

    return buffer.toString();
  }

  String randomString = '';
  int tokenRole = 1; // use 1 for Host/Broadcaster, 2 for Subscriber/Audience
  String serverUrl =
      "https://agora-token-server-gst8.onrender.com"; // The base URL to your token server, for example "https://agora-token-service-production-92ff.up.railway.app"
  int tokenExpireTime = 60; // Expire time in Seconds.
  bool isTokenExpiring = false; // Set to true when the token is about to expire
  String? channelName; // To access the TextField
  int uid = 1;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    channelName = generateRandomString(16);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: SizedBox(
        height: MediaQuery.of(context).size.height,
        child: ListView(
          children: [
            Row(
              children: <Widget>[
                Expanded(
                  child: ElevatedButton(
                    child: const Text("Join"),
                    onPressed: () => {
                      widget.join(uid, channelName, tokenRole, serverUrl,
                          tokenExpireTime, isTokenExpiring)
                    },
                  ),
                ),
                const SizedBox(width: 10),
                Expanded(
                  child: ElevatedButton(
                    child: const Text("Leave"),
                    onPressed: () => {widget.leave()},
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    ));
  }
}
