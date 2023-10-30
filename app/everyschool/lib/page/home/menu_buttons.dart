import 'package:flutter/material.dart';

class MenuButtons extends StatefulWidget {
  const MenuButtons({Key? key});

  @override
  State<MenuButtons> createState() => _MenuButtonsState();
}

class _MenuButtonsState extends State<MenuButtons> {
  var user_num = 3;

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
                borderRadius: BorderRadius.circular(5),
                border: Border.all(color: Color(0xffd9d9d9))),
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Image.asset('assets/images/home/report.png'),
            ),
          ),
          Text(
            '신고내역',
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
                borderRadius: BorderRadius.circular(5),
                border: Border.all(color: Color(0xffd9d9d9))),
            child: Padding(
              padding: const EdgeInsets.all(10.0),
              child: Image.asset('assets/images/home/noti.png'),
            ),
          ),
          Text(
            '가정통신문',
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
                borderRadius: BorderRadius.circular(5),
                border: Border.all(color: Color(0xffd9d9d9))),
            child: Padding(
              padding: const EdgeInsets.all(9.0),
              child: Image.asset('assets/images/home/bill.png'),
            ),
          ),
          Text(
            '고지서',
            style: TextStyle(fontWeight: FontWeight.w600),
          )
        ],
      )
    ];

    if (user_num == 1) {
      columns.insert(
          0,
          Column(
            children: [
              Container(
                  height: 55,
                  width: 55,
                  margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(5),
                      border: Border.all(color: Color(0xffd9d9d9))),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(5, 0, 5, 0),
                    child: Image.asset('assets/images/home/food.png'),
                  )),
              Text(
                '오늘의 급식',
                style: TextStyle(fontWeight: FontWeight.w600),
              )
            ],
          ));
    } else if (user_num == 2) {
      columns.insert(
          0,
          Column(
            children: [
              Container(
                  height: 55,
                  width: 55,
                  margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(5),
                      border: Border.all(color: Color(0xffd9d9d9))),
                  child: Padding(
                    padding: const EdgeInsets.all(10.0),
                    child: Image.asset('assets/images/home/csltapp.png'),
                  )),
              Text(
                '상담신청',
                style: TextStyle(fontWeight: FontWeight.w600),
              )
            ],
          ));
    } else {
      columns.insert(
          0,
          Column(
            children: [
              Container(
                height: 55,
                width: 55,
                margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(5),
                    border: Border.all(color: Color(0xffd9d9d9))),
                child: Padding(
                  padding: const EdgeInsets.all(9.0),
                  child: Image.asset('assets/images/home/csltlist2.png'),
                ),
              ),
              Text(
                '상담내역',
                style: TextStyle(fontWeight: FontWeight.w600),
              )
            ],
          ));
    }

    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: columns,
    );
  }
}
