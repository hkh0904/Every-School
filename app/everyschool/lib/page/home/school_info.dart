import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

class SchoolInfo extends StatefulWidget {
  const SchoolInfo({super.key});

  @override
  State<SchoolInfo> createState() => _SchoolInfoState();
}

class _SchoolInfoState extends State<SchoolInfo> {
  Dio dio = Dio();

  // Future<dynamic> getUserInfo() async {
  //   try {
  //     final response = await dio.get('https://jsonplaceholder.typicode.com/todos/1110');
  //     return response.data;
  //   } catch (e) {
  //     print(e);
  //   }
  // }

  var info = {
    // 'user_num': 2,
    // 'school': '수완초등학교',
    // 'grade': 1,
    // 'class': 3,
    // 'name': '이지혁'
  };

  @override
  Widget build(BuildContext context) {
    // return SizedBox(
    //   height: 50,
    //   child: FutureBuilder(future: getUserInfo(), builder: (BuildContext context, AsyncSnapshot snapshot) {
    //     if (snapshot.hasData == false) {
    //       return Scaffold();
    //     }
    //     else if (snapshot.hasError) {
    //       return Padding(
    //         padding: const EdgeInsets.all(8.0),
    //         child: Text(
    //           'Error: ${snapshot.error}',
    //           style: TextStyle(fontSize: 15),
    //         ),
    //       );
    //     }
    //     else {
    //       if (snapshot.data) {
    //         return Row(
    //           children: [
    //             Text('등록된 정보가 있습니다')
    //           ],
    //         );
    //       } else {
    //         return Row(
    //           children: [
    //             Text('등록된 정보가 없습니다')
    //           ],
    //         );
    //       }
    //     }
    //   }),
    // );
    if (info.isNotEmpty) {
      if (info['user_num'] == 1) {
        return SizedBox(
          height: 60,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(info['school'] as String,
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700)),
              Text('학생'),
              Text('${info['grade']}학년 ${info['class']}반'),
            ],
          ),
        );
      } else if (info['user_num'] == 2) {
        return SizedBox(
          height: 60,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(info['school'] as String,
                      style:
                          TextStyle(fontSize: 18, fontWeight: FontWeight.w700)),
                  Text('학부모'),
                  Text('${info['grade']}학년 ${info['class']}반'),
                ],
              ),
              OutlinedButton(
                onPressed: () => {},
                child: Text('변경', style: TextStyle(color: Colors.black)),
                style: OutlinedButton.styleFrom(
                    minimumSize: Size.zero,
                    padding: EdgeInsets.fromLTRB(13, 10, 13, 10),
                    side: BorderSide(color: Colors.black, width: 0.5),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(0)))),
              )
            ],
          ),
        );
      } else {
        return SizedBox(
          height: 60,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(info['school'] as String,
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700)),
              Text('${info['grade']}학년 ${info['class']}반 담임'),
            ],
          ),
        );
      }
    } else {
      return SizedBox(
        height: 60,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text('등록된 정보가 없습니다',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600)),
            OutlinedButton(
              onPressed: () => {},
              child: Text('등록', style: TextStyle(color: Colors.black)),
              style: OutlinedButton.styleFrom(
                  minimumSize: Size.zero,
                  padding: EdgeInsets.fromLTRB(13, 10, 13, 10),
                  side: BorderSide(color: Colors.black, width: 0.5),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(0)))),
            )
          ],
        ),
      );
    }
    ;
  }
}
