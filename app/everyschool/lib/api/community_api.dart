import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CommunityApi {
  final storage = FlutterSecureStorage();
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

  Future<dynamic> getPostDetail(schoolId, boardName, postId) async {
    String? token = await storage.read(key: 'token');
    try {
      print(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/$boardName/$postId');
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/$boardName/$postId',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getNoticeList(schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/notices?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getHomeNoticeList(schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/communications?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> createPost(schoolId, formData) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/board-service/v1/schools/$schoolId/boards/frees',
          data: formData,
          options: Options(contentType: 'multipart/form-data', headers: {
            'Authorization': 'Bearer $token',
          }));
      print(response.data);
      return response.data['data'];
    } catch (e) {
      print("에러임!!!!!!!! $e");
      return null;
    }
  }

  Future<dynamic> getNewPostList(year, schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/free-boards/new');

      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getNewNoticeList(year, schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/notice-boards/new');
      print(response.data['data']);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
