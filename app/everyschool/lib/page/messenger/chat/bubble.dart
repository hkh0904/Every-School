library flutter_chat_bubble;

import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:everyschool/page/messenger/chat/chat_message_type.dart';
import 'package:everyschool/page/messenger/chat/formatter.dart';
import 'package:flutter/material.dart';
import 'package:flutter_chat_bubble/bubble_type.dart';
import 'package:flutter_chat_bubble/clippers/chat_bubble_clipper_1.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class Bubble extends StatefulWidget {
  const Bubble(
      {super.key, this.margin, required this.chat, required this.myKey});
  final EdgeInsetsGeometry? margin;
  final Chat chat;
  final String? myKey;

  @override
  State<Bubble> createState() => _BubbleState();
}

class _BubbleState extends State<Bubble> {
  final storage = FlutterSecureStorage();
  String? senderKey;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print('여기가 버블1');
    print('여기가 버블1');
    print('여기가 버블12');
    print('여기가 버블3');
    print('여기가 버블4');
    print('여기가 버블5');
    print('여기가 버블7');
    print(widget.myKey);
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: alignmentOnType,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        if (widget.chat.sender == ChatMessageType.received)
          const CircleAvatar(
            backgroundImage: AssetImage("assets/images/avatar_1.png"),
          ),
        Container(
          margin: widget.margin ?? EdgeInsets.zero,
          child: PhysicalShape(
            clipper: clipperOnType,
            elevation: 2,
            color: bgColorOnType,
            shadowColor: Colors.grey.shade200,
            child: Container(
              constraints: BoxConstraints(
                maxWidth: MediaQuery.of(context).size.width * 0.8,
              ),
              padding: paddingOnType,
              child: Column(
                crossAxisAlignment: crossAlignmentOnType,
                children: [
                  Text(
                    widget.chat.message,
                    style: TextStyle(color: textColorOnType),
                  ),
                  const SizedBox(
                    height: 8,
                  ),
                  Text(
                    Formatter.formatDateTime(widget.chat.time),
                    style: TextStyle(color: textColorOnType, fontSize: 12),
                  )
                ],
              ),
            ),
          ),
        ),
      ],
    );
  }

  Color get textColorOnType {
    if (widget.chat.sender == widget.myKey) {
      return Colors.white;
    } else {
      return const Color(0xFF0F0F0F);
    }
  }

  Color get bgColorOnType {
    if (widget.chat.sender == widget.myKey) {
      return const Color(0xFFE7E7ED);
    } else {
      return const Color(0xff15075F);
    }
  }

  CustomClipper<Path> get clipperOnType {
    if (widget.chat.sender == widget.myKey) {
      return ChatBubbleClipper1(type: BubbleType.sendBubble);
    } else {
      return ChatBubbleClipper1(type: BubbleType.receiverBubble);
    }
  }

  CrossAxisAlignment get crossAlignmentOnType {
    if (widget.chat.sender == widget.myKey) {
      return CrossAxisAlignment.end;
    } else {
      return CrossAxisAlignment.start;
    }
  }

  MainAxisAlignment get alignmentOnType {
    if (widget.chat.sender != widget.myKey) {
      return MainAxisAlignment.start;
    } else {
      return MainAxisAlignment.end;
    }
  }

  EdgeInsets get paddingOnType {
    if (widget.chat.sender == widget.myKey) {
      return const EdgeInsets.only(top: 10, bottom: 10, left: 10, right: 24);
    } else {
      return const EdgeInsets.only(
        top: 10,
        bottom: 10,
        left: 24,
        right: 10,
      );
    }
  }
}
