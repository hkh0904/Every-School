import 'package:stomp_dart_client/stomp.dart';
import 'package:stomp_dart_client/stomp_config.dart';
import 'package:stomp_dart_client/stomp_frame.dart';

class SocketHandler {
  void onConnectCallback(StompFrame connectFrame) {
    // client is connected and ready
    print('connected');
  }

  late StompClient stompClient = StompClient(
      config: StompConfig(
          url: 'wss://localhost:3000', onConnect: onConnectCallback));

  void subscribe() {
    stompClient.subscribe(
        destination: '/foo/bar',
        headers: {},
        callback: (frame) {
          // Received a frame for this subscription
          print(frame.body);
        });
  }

  void sendMessage(message) {
    stompClient
        .send(destination: '/foo/bar', body: 'Your message body', headers: {});
  }
}
