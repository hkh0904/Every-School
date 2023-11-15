import 'package:everyschool/page/messenger/chat/chat.dart';
import 'package:flutter/material.dart';

class ChatController extends ChangeNotifier {
  List chatroomList = [];

  List<Chat> chatList = [];

  /* Controllers */
  late final ScrollController scrollController = ScrollController();
  late final TextEditingController textEditingController =
      TextEditingController();
  late final FocusNode focusNode = FocusNode();

  /* Intents */
  Future<void> onFieldSubmitted() async {
    if (!isTextFieldEnable) return;

    // 2. 스크롤 최적화 위치
    // 가장 위에 스크롤 된 상태에서 채팅을 입력했을 때 최근 submit한 채팅 메세지가 보이도록
    // 스크롤 위치를 가장 아래 부분으로 변경
    scrollController.animateTo(
      0,
      duration: const Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
    textEditingController.clear();
    notifyListeners();
  }

  //채팅방 목록 요청하는 트리거
  changechatroomList(roomList) {
    chatroomList = roomList;
    notifyListeners();
  }

  setChatList(message) {
    print('이거 할거든?');
    print(chatList);
    chatList = [...chatList, ...message];
    print(chatList);
    notifyListeners();
  }

  clearChatList() {
    chatList.clear();
  }

  void onFieldChanged(String term) {
    notifyListeners();
  }

  void addNewMessage(Chat message) {
    final kkk = chatList;

    chatList = [message, ...kkk];
    print(chatList);
    notifyListeners();
  }

  /* Getters */
  bool get isTextFieldEnable => textEditingController.text.isNotEmpty;

  bool isUpdated = false;

  setIsUpdated() {
    isUpdated = !isUpdated;
    notifyListeners();
  }
}
