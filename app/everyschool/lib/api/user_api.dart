import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserApi {
  final storage = FlutterSecureStorage();
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getUserInfo(token) async {
    var userType = await storage.read(key: 'usertype');
    String lastAdd;
    if (userType == '1001') {
      lastAdd = '/v1/app/info/student';
    } else if (userType == '1002') {
      print('학부모조회');
      lastAdd = '/v1/app/info/parent';
    } else {
      lastAdd = '/v1/app/info/teacher';
    }
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service$lastAdd',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } on DioException catch (e) {
      print(e);
      return 0;
    }
  }

  Future<dynamic> getUserRegisterInfo(token) async {
    var userType = await storage.read(key: 'usertype');
    String lastAdd;
    if (userType == '1001') {
      lastAdd = '/v1/app/info/student';
    } else if (userType == '1002') {
      lastAdd = '/v1/app/info/parent';
    } else {
      lastAdd = '/v1/app/info/teacher';
    }
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service$lastAdd',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } on DioException catch (e) {
      print(e);
      return 0;
    }
  }

  Future<dynamic> login(id, password, deviceToken) async {
    try {
      final response = await dio
          .post('${serverApi.serverURL}/user-service/login', data: {
        'email': id.text,
        'password': password.text,
        'fcmToken': deviceToken
      });
      print('gkgkgk');
      print(response.headers);
      await storage.write(key: 'token', value: response.headers['token']?[0]);
      await storage.write(
          key: 'userKey', value: response.headers['userKey']?[0]);
      await storage.write(
          key: 'usertype', value: response.headers['usertype']?[0]);
      var userInfo = await getUserInfo(response.headers['token']?[0]);
      print(userInfo);

      if (userInfo['userType'] == 1002 && userInfo['descendants'].length != 0) {
        await storage.write(
            key: 'descendant', value: jsonEncode(userInfo['descendants'][0]));
      }
      return 1;
    } on DioException {
      return 0;
    }
  }

  Future<dynamic> parentSignUp(
      code, email, password, name, birth, gender) async {
    try {
      final response = await dio
          .post('${serverApi.serverURL}/user-service/join/parent', data: {
        'userCode': code,
        'email': email,
        'password': password,
        'name': name,
        'birth': birth,
        "parentType": gender
      });
      print('여기 부모 회원가입');
      return response.data;
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> studentSignUp(code, email, password, name, birth) async {
    try {
      final response = await dio
          .post('${serverApi.serverURL}/user-service/join/student', data: {
        'userCode': code,
        'email': email,
        'password': password,
        'name': name,
        'birth': birth,
      });
      print('여기 학생 회원가입');

      return response.data;
    } catch (e) {
      print(e);
    }
  }

  // 부모 등록 인증코드
  Future<dynamic> registerParents() async {
    String? token = await storage.read(key: 'token');
    try {
      final response =
          await dio.get('${serverApi.serverURL}/user-service/v1/connection',
              options: Options(headers: {
                'Authorization': 'Bearer $token',
              }));
      print(response);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> registerChild(code) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
        '${serverApi.serverURL}/user-service/v1/connection',
        data: {'connectCode': code},
        options: Options(headers: {'Authorization': 'Bearer $token'}),
      );
      return response.data['data'];
    } catch (e) {
      print('API Error: $e');
      // 에러 메시지를 반환합니다.
      return 'API Request Failed: $e';
    }
  }

  // 학교 데이터 조회
  Future<dynamic> getSchoolList(schoolName) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/school-service/v1/schools?query=$schoolName',
          data: {'schoolName': schoolName});
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  // 학교 정보 조회
  Future<dynamic> getSchoolData(schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/school-service/v1/schools/$schoolId',);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  // 학급 등록 신청
  Future<dynamic> registSchool(schoolId, garde, classNum) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/school-service/v1/app/2023/schools/$schoolId/apply',
          data: {'grade': garde, 'classNum': classNum},
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data;
    } catch (e) {
      print(e);
    }
  }

  //이메일 인증
  Future<dynamic> authEmail(email) async {
    print(email);
    try {
      // final deviceToken = getMyDeviceToken();
      final response = await dio.post(
          '${serverApi.serverURL}/user-service/auth/email',
          data: {'email': email});
      print('실행');
      return response.data['data'];
    } catch (e) {
      print(e);
      // rethrow;
    }
  }

  Future<dynamic> checkAuthNumber(email, code) async {
    print(email);
    print(code);

    try {
      // final deviceToken = getMyDeviceToken();
      final response = await dio.post(
          '${serverApi.serverURL}/user-service/auth/email/check',
          data: {"email": email, "authCode": code.toString()});
      return response.data;
    } on DioException catch (e) {
      return e.response?.data;
    }
  }

  //비밀번호 변경
  Future<dynamic> changePassword(token, curPwd, newPwd) async {
    print(token);
    try {
      final response = await dio.patch(
          '${serverApi.serverURL}/user-service/v1/pwd',
          data: {'currentPwd': curPwd, 'newPwd': newPwd},
          options: Options(headers: {'Authorization': 'Bearer $token'}));

      return response.data['message'];
    } on DioException catch (e) {
      print(e.response?.data);

      return e.response?.data['data'];
    }
  }
}
