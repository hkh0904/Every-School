import 'package:flutter/material.dart';

class CommunityMenu extends StatefulWidget {
  const CommunityMenu({super.key});

  @override
  State<CommunityMenu> createState() => _CommunityMenuState();
}

class _CommunityMenuState extends State<CommunityMenu> {

  @override
  Widget build(BuildContext context) {
    List<Widget> columns = [
      Column(
        children: [
          Container(
            height: 55,
            width: 55,
            margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
            decoration: BoxDecoration(
              color: Color(0xFFF1F1F1),
              borderRadius: BorderRadius.circular(100),
            ),
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Image.asset('assets/images/community/calendar.png'),
            ),
          ),
          Text(
            '학사일정',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          Container(
            height: 55,
            width: 55,
            margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
            decoration: BoxDecoration(
              color: Color(0xffF1F1F1),
              borderRadius: BorderRadius.circular(100),
            ),
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Image.asset('assets/images/community/annouce.png'),
            ),
          ),
          Text(
            '학사공지',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          Container(
            height: 55,
            width: 55,
            margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
            decoration: BoxDecoration(
              color: Color(0xffF1F1F1),
              borderRadius: BorderRadius.circular(100),
            ),
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: Image.asset('assets/images/community/meal.png'),
            ),
          ),
          Text(
            '급식메뉴',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      ),
      Column(
        children: [
          Container(
            height: 55,
            width: 55,
            margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
            decoration: BoxDecoration(
              color: Color(0xffF1F1F1),
              borderRadius: BorderRadius.circular(100),
            ),
            child: Padding(
              padding: const EdgeInsets.all(9.0),
              child: Image.asset('assets/images/community/task.png'),
            ),
          ),
          Text(
            '가정통신문',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      )
    ];
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: columns,
    );
  }
}
