import 'package:flutter/material.dart';

class ApproveWaiting extends StatefulWidget {
  const ApproveWaiting({super.key});

  @override
  State<ApproveWaiting> createState() => _ApproveWaitingState();
}

class _ApproveWaitingState extends State<ApproveWaiting> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: Text('학급 승인 대기중입니다.')),
    );
  }
}
