import 'package:dio/dio.dart';
import 'dart:io';
import 'package:everyschool/api/base_api.dart';

class CommunityApi {
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getBoardList(schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/frees?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> createPost(
      schoolId, title, content, List<File> fileList) async {
    // FormData 생성
    FormData formData = FormData.fromMap({
      'title': title,
      'content': content,
      'useComment': true,
    });

    if (fileList.isNotEmpty) {
      for (var file in fileList) {
        String fileName = file.path.split('/').last;
        formData.files.add(
          MapEntry(
            "files",
            await MultipartFile.fromFile(file.path, filename: fileName),
          ),
        );
      }
    }

    try {
      final response = await dio.post(
        '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/frees',
        data: formData,
      );
      return response.data['data'];
    } catch (e) {
      print(e);
      return null;
    }
  }
}
