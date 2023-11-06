import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserApi {
  final storage = FlutterSecureStorage();
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getUserInfo(token) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/user-service/v1/info',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data['data']);
      return response.data['data'];
    } catch (e) {
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
      return 1;
    } catch (e) {
      return 0;
    }
  }
}
