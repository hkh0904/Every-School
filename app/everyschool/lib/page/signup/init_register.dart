import 'package:everyschool/main.dart';
import 'package:everyschool/page/signup/init_register_box.dart';
import 'package:flutter/material.dart';

class InitRegister extends StatefulWidget {
  const InitRegister({super.key});

  @override
  State<InitRegister> createState() => _InitRegisterState();
}

class _InitRegisterState extends State<InitRegister> {
  var selectList = [
    {
      'image': 'assets/images/mypage/parent.png',
      'value': '학부모',
      'type': '1002'
    },
    {
      'image': 'assets/images/mypage/student.png',
      'value': '학생',
      'type': '1001'
    },
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(0xffD4DAF7),
        elevation: 0,
        leading: BackButton(
          color: Colors.black,
        ),
      ),
      body: Container(
        color: Color(0xffD4DAF7),
        width: MediaQuery.of(context).size.width,
        child: Padding(
          padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
            Text('누구신가요?',
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.w700)),
            Image.asset('assets/images/home/select_deco.png'),
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 0, 0, 50),
              child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: selectList.length,
                  itemBuilder: (context, index) {
                    return InitRegisterBox(child: selectList[index]);
                  }),
            )
          ]),
        ),
      ),
    );
  }
}
