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
      final response =
          await dio.post('${serverApi.serverURL}/user-service/login', data: {
        'email': id.text,
        'password': password.text,
        // 'notiToken': deviceToken
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
}
