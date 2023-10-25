import 'package:flutter/material.dart';

class MenuButtons extends StatefulWidget {
  const MenuButtons({Key? key});

  @override
  State<MenuButtons> createState() => _MenuButtonsState();
}

class _MenuButtonsState extends State<MenuButtons> {
  var user_num = 1;

  @override
  Widget build(BuildContext context) {
    List<Widget> columns = [
      Column(
        children: [
          Image.asset(
            'assets/images/home/report.png',
            height: 40,
          ),
          Text('신고내역')
        ],
      ),
      Column(
        children: [
          Image.asset(
            'assets/images/home/noti.png',
            height: 40,
          ),
          Text('가정통신문')
        ],
      ),
      Column(
        children: [
          Image.asset(
            'assets/images/home/bill.png',
            height: 40,
          ),
          Text('고지서')
        ],
      )
    ];

    if (user_num == 1) {
      columns.insert(
          0,
          Column(
            children: [
              Image.asset(
                'assets/images/home/food.png',
                height: 40,
              ),
              Text('오늘의 급식')
            ],
          ));
    } else if (user_num == 2) {
      columns.insert(
          0,
          Column(
            children: [
              Image.asset(
                'assets/images/home/csltapp.png',
                height: 40,
              ),
              Text('상담신청')
            ],
          ));
    } else {
      columns.insert(
          0,
          Column(
            children: [
              Image.asset(
                'assets/images/home/csltlist.png',
                height: 40,
              ),
              Text('상담내역')
            ],
          ));
    }

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      children: columns,
    );
  }
}
