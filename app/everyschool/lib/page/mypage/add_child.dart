import 'package:flutter/material.dart';

class AddChild extends StatefulWidget {
  const AddChild({super.key});

  @override
  State<AddChild> createState() => _AddChildState();
}

class _AddChildState extends State<AddChild> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: Text('자녀등록페이지')),
    );
  }
}
