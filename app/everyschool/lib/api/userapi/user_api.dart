import 'package:everyschool/api/base_api.dart';

class UserApi {
  final dio = ServerApi().dio;
  final url = ServerApi().serverURL;

  Future<dynamic> getSchoolList(schoolName) async {
    try {
      // final deviceToken = getMyDeviceToken();
      final response = await dio.get(
          '$url/school-service/v1/schools?query=$schoolName',
          data: {'schoolName': schoolName});
      return response.data['data'];
    } catch (e) {
      print(e);
      // rethrow;
    }
  }
}
