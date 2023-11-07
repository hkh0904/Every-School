import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:everyschool/api/firebase_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoginApi {
  final storage = FlutterSecureStorage();
  ServerApi serverApi = ServerApi();

  Future<dynamic> login(id, password, deviceToken) async {
    try {
      final response = await serverApi.dio
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
      return 0;
    }
  }
}
