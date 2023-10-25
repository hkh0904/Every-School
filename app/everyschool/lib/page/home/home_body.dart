import 'package:everyschool/page/home/menu_buttons.dart';
import 'package:everyschool/page/home/school_info.dart';
import 'package:flutter/material.dart';

class HomeBody extends StatefulWidget {
  const HomeBody({super.key});

  @override
  State<HomeBody> createState() => _HomeBodyState();
}

class _HomeBodyState extends State<HomeBody> {
  @override
  Widget build(BuildContext context) {
    return CustomScrollView(
      slivers: [
        SliverToBoxAdapter(
          child: Image.asset('assets/images/home/banner.png'),
        ),
        SliverToBoxAdapter(
          child: Container(
              margin: EdgeInsets.fromLTRB(15, 15, 15, 10), child: SchoolInfo()),
        ),
        SliverToBoxAdapter(
          child: MenuButtons(),
        )
      ],
    );
  }
}
