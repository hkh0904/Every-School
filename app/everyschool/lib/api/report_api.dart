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
      return null;
    }
  }

  Future<dynamic> getReportList(year, schoolId, reportType) async {
    String? token = await storage.read(key: 'token');

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/report-service/v1/web/$year/schools/$schoolId/reports?status=$reportType',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
      return null;
    }
  }

  Future<dynamic> getReportDetail(year, schoolId, reportId) async {
    String? token = await storage.read(key: 'token');

    try {
      final response = await dio.get(
          '${serverApi.serverURL}/report-service/v1/web/$year/schools/$schoolId/reports/$reportId',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));

      return response.data['data'];
    } catch (e) {
      print(e);
      return null;
    }
  }
}
