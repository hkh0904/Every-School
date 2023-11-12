import 'dart:convert';

import 'package:everyschool/main.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SelectChildBox extends StatefulWidget {
  const SelectChildBox({super.key, this.child});
  final child;

  @override
  State<SelectChildBox> createState() => _SelectChildBoxState();
}

class _SelectChildBoxState extends State<SelectChildBox> {
  @override
  Widget build(BuildContext context) {
    return Container(
      height: 95,
      margin: EdgeInsets.fromLTRB(0, 0, 0, 30),
      decoration: BoxDecoration(
        color: Colors.grey[50],
        borderRadius: BorderRadius.circular(8),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.2), // 그림자 색상과 불투명도
            spreadRadius: 2, // 그림자 확산 정도
            blurRadius: 5, // 그림자의 흐림 정도
            offset: Offset(1, 3), // 그림자 위치 (가로, 세로)
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(20, 0, 20, 0),
        child:
            Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(widget.child['school']['name'],
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700)),
              Text(
                  '${widget.child['schoolClass']['grade']}학년 ${widget.child['schoolClass']['classNum']}반',
                  style: TextStyle(fontSize: 14, color: Color(0xff4A5056))),
              Text(
                widget.child['name'],
                style: TextStyle(fontSize: 18),
              )
            ],
          ),
          GestureDetector(
            onTap: () async {
              final storage = FlutterSecureStorage();
              await storage.delete(key: 'descendant');
              await storage.write(
                  key: 'descendant', value: jsonEncode(widget.child));
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const Main()));
            },
            child: Text(
              '선택하기',
              style: TextStyle(color: Color(0xff449D87), fontSize: 16),
            ),
          )
        ]),
      ),
    );
  }
}
