import 'package:flutter/material.dart';

class GetCallSuccess extends StatefulWidget {
  const GetCallSuccess({super.key, this.leave, this.userInfo, this.remoteUid});
  final leave;
  final userInfo;
  final remoteUid;

  @override
  State<GetCallSuccess> createState() => _GetCallSuccessState();
}

class _GetCallSuccessState extends State<GetCallSuccess> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print('다른사람아이디 ${widget.remoteUid}');
  }

  bool canClick = true;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SizedBox(
      height: MediaQuery.of(context).size.height,
      width: MediaQuery.of(context).size.width,
      // color: Colors.grey,
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        // crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            '연결됨',
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          SizedBox(
            height: 20,
          ),
          Image.asset(
            'assets/images/contact/call.gif',
            height: 150,
            width: 150,
          ),
          SizedBox(
            height: 15,
          ),
          Text(
            widget.userInfo['name'],
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.25,
          ),
          Center(
            child: GestureDetector(
              onTap: () {
                if (canClick) {
                  setState(() {
                    canClick = false;
                  });

                  widget.leave();
                }
              },
              child: Container(
                height: 80,
                width: 80,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(100),
                  color: Color(0xffFF3B3B),
                ),
                child: Icon(
                  Icons.call_end,
                  color: Colors.white,
                  size: 35,
                ),
              ),
            ),
          )
        ],
      ),
    ));
  }
}
