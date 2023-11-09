import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';

class MessengerApi {
  Dio dio = Dio();
  SocketApi socketApi = SocketApi();
  ServerApi serverApi = ServerApi();

  //채팅방 만들기
  Future<dynamic> createChatRoom(
      token, userKey, userType, userName, mytype, myclassId) async {
    try {
      final response = await dio.post('${socketApi.httpURL}/v1/chat-rooms',
          data: {
            //상대방 키
            "userkey": userKey,
            //상대방 이름 직급
            "username": userName,
            //상대방 유저 타입
            "usertype": userType,
            //아래 두개는 내 정보
            "mytype": mytype,
            "myclassId": myclassId,
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
  Future<dynamic> chatFilter(token, chatRoomId, senderUserkey, message) async {
    try {
      final response = await dio.post('${socketApi.httpURL}/v1/filters/chat',
          data: {
            "chatRoomId": chatRoomId,
            "senderUserKey": senderUserkey,
            "message": message
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data);
      print('채팅 필터링');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //선생님의 연락처 조회
  Future<dynamic> getTeacherConnect(
    token,
  ) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service/v1/app/2023/schools/100000/students',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print('성생님 연락처');
      print(response.data);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //학생, 학부모들의 연락처 조회
  Future<dynamic> getUserConnect(
    token,
  ) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service/v1/app/2023/schools/100000/teachers',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print('학생들 연락처');
      print(response.data);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
