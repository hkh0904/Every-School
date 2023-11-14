import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CommunityApi {
  final storage = FlutterSecureStorage();
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getBoardList(schoolYear, schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/free-boards?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getPostDetail(schoolYear, schoolId, boardName, postId) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/$boardName-boards/$postId',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getNoticeList(schoolYear, schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/notice-boards?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getHomeNoticeList(schoolYear, schoolId) async {
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/communication-boards?page=1');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> createPost(schoolYear, schoolId, formData) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/free-boards',
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

  Future<dynamic> writeComment(boardId, schoolId, schoolYear, formData) async {
    print(
        '==============================$boardId, $schoolId, $schoolYear, $formData');
    print(
        '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/comments');
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/comments',
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

  Future<dynamic> getMyPost(year, schoolId) async {
    var token = await storage.read(key: 'token') ?? "";
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/my/boards?category=6001',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      print(response.data['data']);
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getMyComment(year, schoolId) async {
    var token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/my/comments',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      print('${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getMyScrap(year, schoolId) async {
    var token = await storage.read(key: 'token');
    print('왜 안돼 $token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/scraps',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      print('왜 안돼 ${response.data}');
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
