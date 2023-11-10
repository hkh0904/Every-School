import 'package:flutter/material.dart';

class ModalCallPage extends StatefulWidget {
  const ModalCallPage({super.key, this.leave, this.remoteUid});
  final leave;
  final remoteUid;

  @override
  State<ModalCallPage> createState() => _ModalCallPageState();
}

class _ModalCallPageState extends State<ModalCallPage> {
  @override
  Widget build(BuildContext context) {
    if (widget.remoteUid == null) {
      return Scaffold(body: Text('사람없음'));
    } else {
      return Scaffold(body: Text('사람있음'));
    }
  }
}
