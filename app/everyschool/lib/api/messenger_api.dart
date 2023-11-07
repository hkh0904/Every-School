import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';

class MessengerApi {
  Dio dio = Dio();
  SocketApi socketApi = SocketApi();

  //채팅방 만들기
  Future<dynamic> createChatRoom(token) async {
    try {
      final response = await dio.post('${socketApi.httpURL}/v1/chat-rooms',
          data: {
            "opponentUserKey": '099ced50-cb18-4ebe-a445-fee91677d8ed',
            "loginUserType": "M",
            "schoolClassId": 1,
            "relation": "정인재 선생님"
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data);
      print('채팅방 생성 실행');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //채팅방 목록 조회
  Future<dynamic> getChatList(token) async {
    try {
      final response = await dio.get('${socketApi.httpURL}/v1/chat-rooms',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data['data']);
      print('채팅목록 불러오기 성공 실행');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //채팅룸 내역 조회
  Future<dynamic> getChatListItem(token, chatRoomId) async {
    try {
      final response = await dio.get(
          '${socketApi.httpURL}/v1/chat-rooms/$chatRoomId',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data);

      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //채팅 전송 전 필터링
  Future<dynamic> chatFilter(
    token,
  ) async {
    try {
      final response = await dio.post('${socketApi.httpURL}/v1/filters/chat',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data);
      print('채팅 필터링');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
