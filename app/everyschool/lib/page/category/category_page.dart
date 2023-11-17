import 'package:everyschool/page/category/Category.dart';
import 'package:everyschool/page/category/category_user_info.dart';
import 'package:flutter/material.dart';

class CategoryPage extends StatefulWidget {
  const CategoryPage({super.key});

  @override
  State<CategoryPage> createState() => _CategoryPageState();
}

class _CategoryPageState extends State<CategoryPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ListView(
        children: [CategoryUserInfo(), Category()],
      ),
    );
  }
}
