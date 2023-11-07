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
      lastAdd = '/v1/app/info/parent';
    } else {
      lastAdd = '/v1/app/info/teacher';
    }
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service$lastAdd',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
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
      await storage.write(key: 'token', value: response.headers['token']?[0]);
      await storage.write(
          key: 'userKey', value: response.headers['userKey']?[0]);
      await storage.write(
          key: 'usertype', value: response.headers['usertype']?[0]);
      return 1;
    } catch (e) {
      print(e);
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
      print('여기 회원가입 되돌리는,${response.data}');
      return response.data;
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> studentSignUp(code, email, password, name, birth) async {
    try {
      final response = await dio
          .post('${serverApi.serverURL}/user-service/join/parent', data: {
        'userCode': code,
        'email': email.text,
        'password': password.text,
        'name': name,
        'birth': birth,
      });

      return 1;
    } catch (e) {
      print(e);
    }
  }

  //사용자 학교 등록
  Future<dynamic> getSchoolList(schoolName) async {
    try {
      // final deviceToken = getMyDeviceToken();
      final response = await dio.get(
          '${serverApi.serverURL}/school-service/v1/schools?query=$schoolName',
          data: {'schoolName': schoolName});
      return response.data['data'];
    } catch (e) {
      print(e);
      // rethrow;
    }
  }
}
