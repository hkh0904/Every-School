import 'package:flutter/material.dart';

class CallDetail extends StatefulWidget {
  const CallDetail({super.key, this.detail});
  final detail;

  @override
  State<CallDetail> createState() => _CallDetailState();
}

class _CallDetailState extends State<CallDetail> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print(widget.detail);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        title: Text('통화내역 상세보기', style: TextStyle(color: Colors.black)),
        elevation: 0,
      ),
      body: Column(),
    );
  }
}
