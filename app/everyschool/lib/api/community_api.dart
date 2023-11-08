import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';

class CommunityApi {
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getBoardList() async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/schools/100000/boards/frees?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
