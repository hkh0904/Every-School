import 'package:flutter/material.dart';

class GetCallSuccess extends StatefulWidget {
  const GetCallSuccess({super.key, this.leave});
  final leave;

  @override
  State<GetCallSuccess> createState() => _GetCallSuccessState();
}

class _GetCallSuccessState extends State<GetCallSuccess> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

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
          ClipRRect(
            borderRadius: BorderRadius.circular(100),
            child: Image.asset(
              'assets/images/consulting/detail.png',
              height: 150,
              width: 150,
            ),
          ),
          SizedBox(
            height: 15,
          ),
          Text(
            '받는사람 정보',
            style: TextStyle(fontSize: 21, fontWeight: FontWeight.w700),
          ),
          Text(
            '유저설명',
            style: TextStyle(fontSize: 18),
          ),
          SizedBox(
            height: MediaQuery.of(context).size.height * 0.25,
          ),
          Center(
            child: GestureDetector(
              onTap: widget.leave,
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
