import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class UserApi {
  final storage = FlutterSecureStorage();
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
      final response = await serverApi.dio.get(
          '${serverApi.serverURL}/user-service$lastAdd',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
      return 0;
    }
  }
}
