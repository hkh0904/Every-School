import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

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
            "opponentUserKey": userKey,
            //상대방 이름 직급
            "opponentUserName": userName,
            //상대방 유저 타입
            "opponentUserType": userType,
            //아래 두개는 내 정보
            "loginUserType": mytype,
            "schoolClassId": myclassId,
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));

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
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  //채팅룸 내역 조회
  Future<dynamic> getChatListItem(token, chatRoomId, idx) async {
    try {
      final response = await dio.get(
          '${socketApi.httpURL}/v1/chat-rooms/$chatRoomId',
          queryParameters: {'idx': idx},
          options: Options(headers: {'Authorization': 'Bearer $token'}));

      return response.data['data'];
    } on DioException catch (e) {
      return e.response?.data;
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
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}

class CallingApi {
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();
  final storage = FlutterSecureStorage();

  // 전화걸때
  Future<dynamic> callOthers(token, userKey, senderName, cname) async {
    var myUserKey = await storage.read(key: 'userKey') ?? "";
    try {
      final response =
          await dio.post('${serverApi.serverURL}/call-service/v1/calls/calling',
              data: {
                "otherUserKey": userKey,
                "myUserKey": myUserKey,
                "senderName": senderName,
                "cname": cname
              },
              options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> missedCall(
      token, userKey, senderName, startDateTime, endDateTime) async {
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/calling/miss',
          data: {
            "otherUserKey": userKey,
            "senderName": senderName,
            "startDateTime": startDateTime,
            "endDateTime": endDateTime
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> cancelCall(
      token, userKey, senderName, startDateTime, endDateTime) async {
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/calling/cancel',
          data: {
            "otherUserKey": userKey,
            "senderName": senderName,
            "startDateTime": startDateTime,
            "endDateTime": endDateTime
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> deniedCall(userKey, senderName, startDateTime) async {
    String? token = await storage.read(key: 'token');
    DateTime dateTime = DateTime.parse(startDateTime);
    var startTime = [
      dateTime.year,
      dateTime.month,
      dateTime.day,
      dateTime.hour,
      dateTime.minute,
      dateTime.second,
      dateTime.millisecond
    ];

    DateTime endTime = DateTime.now();
    var endTimeList = [
      endTime.year,
      endTime.month,
      endTime.day,
      endTime.hour,
      endTime.minute,
      endTime.second,
      endTime.millisecond
    ];

    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/calling/denied',
          data: {
            "otherUserKey": userKey,
            "senderName": senderName,
            "startDateTime": startTime,
            "endDateTime": endTimeList
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callRecordingStart(
      token, cname, uid, chatroomtoken, userKey, otherUserKey) async {
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/record/start',
          data: {
            "cname": cname,
            "uid": uid,
            "token": chatroomtoken,
            "userKey": userKey,
            "otherUserKey": otherUserKey
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callRecordingStop(token, cname, uid, resourceId, sid,
      otherUserKey, sender, startDateTime, endDateTime) async {
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/record/sender-stop1',
          data: {
            "cname": cname,
            "uid": uid,
            "resourceId": resourceId,
            "sid": sid,
            "otherUserKey": otherUserKey,
            "sender": sender,
            "startDateTime": startDateTime,
            "endDateTime": endDateTime,
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callReceiverStopRecording(token, cname, uid, resourceId, sid,
      otherUserKey, sender, startDateTime, endDateTime) async {
    print(
        '녹음종료 토큰 $token 유아이디$uid 채널이름 $cname 리소스아이디 $resourceId 에스아이디 $sid 발신자 $sender 다른사람유저키 $otherUserKey 시작시간 $startDateTime 끝나는시간 $endDateTime');

    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/record/sender-stop2',
          data: {
            "cname": cname,
            "uid": uid,
            "resourceId": resourceId,
            "sid": sid,
            "otherUserKey": otherUserKey,
            "sender": sender,
            "startDateTime": startDateTime,
            "endDateTime": endDateTime,
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callReceiverStop(token, otherUserKey) async {
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/record/receiver_stop/$otherUserKey',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getCallList() async {
    var userKey = await storage.read(key: 'userKey');
    var token = await storage.read(key: 'token');

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/call-service/v1/calls/$userKey',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getCallDetail(userCallId) async {
    var token = await storage.read(key: 'token');

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/call-service/v1/calls/detail/$userCallId',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> muteTimeSet(startTime, endTime, isActivate) async {
    var token = await storage.read(key: 'token');

    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/do-not-disturbs/',
          data: {
            "startTime": startTime,
            "endTime": endTime,
            "isActivate": isActivate
          },
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data;
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> muteTimeInquiry() async {
    var token = await storage.read(key: 'token');

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/call-service/v1/do-not-disturbs/',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
