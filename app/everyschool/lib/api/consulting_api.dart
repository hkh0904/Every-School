import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class ConsultingApi {
  final storage = FlutterSecureStorage();
  ServerApi serverApi = ServerApi();
  Dio dio = Dio();

  Future<dynamic> getConsultingList(context) async {
    var userType = await storage.read(key: 'usertype');
    String? token = await storage.read(key: 'token');

    String lastUrl;

    if (userType == '1002') {
      lastUrl = '/consult-service/v1/app/2023/schools/100000/consults/parent';
    } else {
      lastUrl = '/consult-service/v1/app/2023/schools/100000/consults/teacher';
    }

    try {
      final response = await dio.get('${serverApi.serverURL}$lastUrl',
          options: Options(headers: {'Authorization': 'Bearer $token'}));
      return response.data['data'];
    } catch (e) {
      print(e);
      return 0;
    }
  }
}
