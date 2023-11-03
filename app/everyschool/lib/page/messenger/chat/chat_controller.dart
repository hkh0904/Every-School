import 'package:everyschool/api/stomp_client.dart';
import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp.dart';

class ChatController extends ChangeNotifier {
  List<Chat> chatList = [];

  /* Controllers */
  late final ScrollController scrollController = ScrollController();
  late final TextEditingController textEditingController =
      TextEditingController();
  late final FocusNode focusNode = FocusNode();

  /* Intents */
  Future<void> onFieldSubmitted() async {
    print(1);
    if (!isTextFieldEnable) return;

    // 2. 스크롤 최적화 위치
    // 가장 위에 스크롤 된 상태에서 채팅을 입력했을 때 최근 submit한 채팅 메세지가 보이도록
    // 스크롤 위치를 가장 아래 부분으로 변경
    scrollController.animateTo(
      0,
      duration: const Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
    print(2);
    textEditingController.clear();
    print(3);

    notifyListeners();
    print(4);
  }

  void setChatList(message) {
    chatList = [message];
    notifyListeners();
  }

  void onFieldChanged(String term) {
    notifyListeners();
  }

  void addNewMessage(Chat message) {
    chatList.add(message);
    notifyListeners();
    print(chatList);
  }

  /* Getters */
  bool get isTextFieldEnable => textEditingController.text.isNotEmpty;
}
