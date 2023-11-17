import 'package:dio/dio.dart';
import 'package:everyschool/api/base_api.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CommunityApi {
  final storage = FlutterSecureStorage();
  Dio dio = Dio();
  ServerApi serverApi = ServerApi();

  Future<dynamic> getBoardList(schoolYear, schoolId, page) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/free-boards?page=$page',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
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

  Future<dynamic> getNoticeList(schoolYear, schoolId, page) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/notice-boards?page=$page',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getHomeNoticeList(schoolYear, schoolId, page) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/communication-boards?page=$page',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
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
      return response.data['data'];
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> writeComment(boardId, schoolId, schoolYear, formData) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/comments',
          data: formData,
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> writeRecomment(
      boardId, schoolId, schoolYear, commentId, formData) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/comments/$commentId',
          data: formData,
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> scrapPost(boardId, schoolId, schoolYear) async {
    String? token = await storage.read(key: 'token');
    try {
      await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/scraps',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> unScrapPost(boardId, schoolId, schoolYear) async {
    String? token = await storage.read(key: 'token');
    try {
      await dio.post(
          '${serverApi.serverURL}/board-service/v1/app/$schoolYear/schools/$schoolId/boards/$boardId/scraps/unscraps',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
    } catch (e) {
      return null;
    }
  }

  Future<dynamic> getNewPostList(year, schoolId) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/free-boards/new',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getNewNoticeList(year, schoolId) async {
    String? token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/notice-boards/new',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
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
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }

  Future<dynamic> getMyScrap(year, schoolId) async {
    var token = await storage.read(key: 'token');
    try {
      final response = await dio.get(
          '${serverApi.serverURL}/board-service/v1/app/$year/schools/$schoolId/scraps',
          options: Options(headers: {
            'Authorization': 'Bearer $token',
          }));
      return response.data['data'];
    } catch (e) {
      print(e);
    }
  }
}
