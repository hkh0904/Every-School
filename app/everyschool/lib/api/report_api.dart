import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ReportApi {
  final storage = FlutterSecureStorage();
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> writeReport(data) async {
    var userType = await storage.read(key: 'usertype');
    String? token = await storage.read(key: 'token');

    try {
      final response = await dio.post(
          '${serverApi.serverURL}/report-service/v1/app/2023/schools/100000/reports',
          data: data,
          options: Options(contentType: 'multipart/form-data', headers: {
            'Authorization': 'Bearer $token',
          }));
      print(response.data);
      return response.data['data'];
    } catch (e) {
      print(e);
      return 0;
    }
  }
}
