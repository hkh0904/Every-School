import 'package:dio/dio.dart';

class ServerApi {
  final Dio dio = Dio();
  final serverURL = 'https://every-school.com/api/';
}

class SocketApi {
  final socketURL = 'ws://10.0.2.2:8080/chat';
}
