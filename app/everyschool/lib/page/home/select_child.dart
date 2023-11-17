import 'package:everyschool/main.dart';
import 'package:everyschool/page/home/select_child_box.dart';
import 'package:flutter/material.dart';

class SelectChild extends StatefulWidget {
  const SelectChild({super.key, this.descendants});
  final descendants;

  @override
  State<SelectChild> createState() => _SelectChildState();
}

class _SelectChildState extends State<SelectChild> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(0xffD4DAF7),
        elevation: 0,
        leading: BackButton(
            color: Colors.black,
            onPressed: () {
              Navigator.pushAndRemoveUntil(
                  context,
                  MaterialPageRoute(builder: (context) => Main()),
                  (Route route) => false);
            }),
      ),
      body: Container(
        color: Color(0xffD4DAF7),
        width: MediaQuery.of(context).size.width,
        child: Padding(
          padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
            Text('자녀 선택하기',
                style: TextStyle(fontSize: 24, fontWeight: FontWeight.w700)),
            Image.asset('assets/images/home/select_deco.png'),
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 0, 0, 50),
              child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: widget.descendants.length,
                  itemBuilder: (context, index) {
                    return SelectChildBox(child: widget.descendants[index]);
                  }),
            )
          ]),
        ),
      ),
    );
  }
}
