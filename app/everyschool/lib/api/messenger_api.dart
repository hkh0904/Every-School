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
    print('여기는 $token $userKey $userType $userName $mytype $myclassId');
    print('여기가 1번');
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
      print('여기가 2번');

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
  Future<dynamic> getChatListItem(token, chatRoomId, idx) async {
    try {
      final response = await dio.get(
          '${socketApi.httpURL}/v1/chat-rooms/$chatRoomId',
          queryParameters: {'idx': idx},
          options: Options(headers: {'Authorization': 'Bearer $token'}));

      print('채팅 룸 내역 조회 성공');
      print(response.data['data']);

      return response.data['data'];
    } on DioException catch (e) {
      print('채팅 룸 내역 조회 실패');

      print(e);
      print(e.response);
      print(e.response?.data);
      return e.response?.data;
    }
  }

  //채팅 전송 전 필터링
  Future<dynamic> chatFilter(token, chatRoomId, senderUserkey, message) async {
    print(chatRoomId);
    print(senderUserkey);
    print(message);

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

class CallingApi {
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();
  final storage = FlutterSecureStorage();

  // 전화걸때
  Future<dynamic> callOthers(token, userKey, senderName, cname) async {
    var myUserKey = await storage.read(key: 'userKey') ?? "";
    print('내 유저키 전화 $myUserKey');
    print('전화걸때 $userKey $senderName $cname');
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
      print('걸었어용 ${response.data}');
      print('전화걸음^^~');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> missedCall(
      token, userKey, senderName, startDateTime, endDateTime) async {
    print('부재중 $userKey $senderName $startDateTime $endDateTime');
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
      print('부재중 리스폰스 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> cancelCall(
      token, userKey, senderName, startDateTime, endDateTime) async {
    print('취소 $userKey $senderName $startDateTime $endDateTime');
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
      print('취소 리스폰스 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> deniedCall(userKey, senderName, startDateTime) async {
    String? token = await storage.read(key: 'token');
    print('거절 $userKey $senderName $startDateTime');
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
    print(startTime);

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
      print('거절 리스폰스 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callRecordingStart(
      token, cname, uid, chatroomtoken, userKey, otherUserKey) async {
    print(
        '녹음시작 토큰 $token 유저키$userKey 채널이름 $cname 유저아이디 $uid 챗룸토큰 $chatroomtoken 내 유저키 $userKey 다른사람유저키 $otherUserKey ');

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
      print('녹음시작 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callRecordingStop(token, cname, uid, resourceId, sid,
      otherUserKey, sender, startDateTime, endDateTime) async {
    print(
        '녹음종료 토큰 $token 유아이디$uid 채널이름 $cname 리소스아이디 $resourceId 에스아이디 $sid 발신자 $sender 다른사람유저키 $otherUserKey 시작시간 $startDateTime 끝나는시간 $endDateTime');

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
      print('녹음종료 ${response.data}');
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
      print('녹음종료 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> callReceiverStop(token, otherUserKey) async {
    print('수신자 종료 다른사람유저키 $otherUserKey');

    try {
      final response = await dio.post(
          '${serverApi.serverURL}/call-service/v1/calls/record/receiver_stop/$otherUserKey',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print('녹음종료 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getCallList() async {
    var userKey = await storage.read(key: 'userKey');
    var token = await storage.read(key: 'token');

    print(userKey);
    print(token);

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/call-service/v1/calls/$userKey',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print('겟콜리스트 ${response.data}');
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
      print('상세정보 ${response.data}');
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
      print('방해금지시간설정 ${response.data}');
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
      print('방해금지시간조회 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
