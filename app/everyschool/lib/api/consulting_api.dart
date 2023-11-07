import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class LoginApi {
  final storage = FlutterSecureStorage();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getConsultingList() async {}
}
