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

  var info = {'user_num': 1, 'school': '수완초등학교', 'grade': 1, 'class': 3};

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
        return Row(
          children: [
            Column(
              children: [
                Text(info['school'] as String),
                Text('${info['grade']}학년 ${info['class']}반'),
              ],
            )
          ],
        );
      } else if (info['user_num'] == 2) {
        return Row(
          children: [
            Text(info['school'] as String),
            Text('${info['grade']}학년 ${info['class']}반 담임'),
          ],
        );
      } else {
        return Row(
          children: [
            Text(info['school'] as String),
            Text('${info['grade']}학년 ${info['class']}반 담임'),
          ],
        );
      }
      ;
    } else {
      return Row(
        children: [Text('등록된 정보가 없습니다')],
      );
    }
    ;
  }
}
