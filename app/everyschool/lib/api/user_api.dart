import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserApi {
  final storage = FlutterSecureStorage();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getUserInfo(token) async {
    try {
      final response = await serverApi.dio.get(
          '${serverApi.serverURL}/user-service/v1/info',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      print(response.data['data']);
      return response.data['data'];
    } catch (e) {
      return 0;
    }
  }
}
