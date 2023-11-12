import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/home/select_child.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SchoolInfo extends StatefulWidget {
  const SchoolInfo({super.key});

  @override
  State<SchoolInfo> createState() => _SchoolInfoState();
}

class _SchoolInfoState extends State<SchoolInfo> {
  final storage = FlutterSecureStorage();
  var descend;

  getMainUserInfo() async {
    var token = await storage.read(key: 'token') ?? "";
    var info = await UserApi().getUserInfo(token);
    print('정보 $info');

    if (info['userType'] == 1002) {
      print('여기');
      var descendant = await storage.read(key: 'descendant') ?? "";
      var selectDescendant = jsonDecode(descendant);
      print(selectDescendant);
      descend = selectDescendant;
    }

    return info;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getMainUserInfo(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            if (snapshot.data.isNotEmpty) {
              if (snapshot.data['userType'] == 1001) {
                return SizedBox(
                  height: 60,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(snapshot.data['school']['name'] as String,
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w700)),
                      Text('학생'),
                      Text(
                          '${snapshot.data['schoolClass']['grade']}학년 ${snapshot.data['schoolClass']['classNum']}반'),
                    ],
                  ),
                );
              } else if (snapshot.data['userType'] == 1002) {
                return snapshot.data['descendants'].length > 0
                    ? SizedBox(
                        height: 60,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(descend['school']['name'] as String,
                                    style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.w700)),
                                Text('학부모'),
                                Text(
                                    '${descend['schoolClass']['grade']}학년 ${descend['schoolClass']['classNum']}반'),
                              ],
                            ),
                            OutlinedButton(
                              onPressed: () => {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => SelectChild(
                                          descendants:
                                              snapshot.data['descendants'])),
                                )
                              },
                              style: OutlinedButton.styleFrom(
                                  minimumSize: Size.zero,
                                  padding: EdgeInsets.fromLTRB(13, 10, 13, 10),
                                  side: BorderSide(
                                      color: Colors.black, width: 0.5),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.all(
                                          Radius.circular(0)))),
                              child: Text('변경',
                                  style: TextStyle(color: Colors.black)),
                            )
                          ],
                        ),
                      )
                    : SizedBox(
                        height: 60,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text('등록된 자녀가 없습니다',
                                style: TextStyle(
                                    fontSize: 16, fontWeight: FontWeight.w600)),
                            OutlinedButton(
                              onPressed: () => {},
                              style: OutlinedButton.styleFrom(
                                  minimumSize: Size.zero,
                                  padding: EdgeInsets.fromLTRB(13, 10, 13, 10),
                                  side: BorderSide(
                                      color: Colors.black, width: 0.5),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.all(
                                          Radius.circular(0)))),
                              child: Text('등록',
                                  style: TextStyle(color: Colors.black)),
                            )
                          ],
                        ),
                      );
              } else {
                return SizedBox(
                  height: 70,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(snapshot.data['school']['name'] as String,
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w700)),
                      Text(
                          '${snapshot.data['schoolClass']['grade']}학년 ${snapshot.data['schoolClass']['classNum']}반 담임'),
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
                        style: TextStyle(
                            fontSize: 16, fontWeight: FontWeight.w600)),
                    OutlinedButton(
                      onPressed: () => {},
                      style: OutlinedButton.styleFrom(
                          minimumSize: Size.zero,
                          padding: EdgeInsets.fromLTRB(13, 10, 13, 10),
                          side: BorderSide(color: Colors.black, width: 0.5),
                          shape: RoundedRectangleBorder(
                              borderRadius:
                                  BorderRadius.all(Radius.circular(0)))),
                      child: Text('등록', style: TextStyle(color: Colors.black)),
                    )
                  ],
                ),
              );
            }
          } else if (snapshot.hasError) {
            return Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                'Error: ${snapshot.error}',
                style: TextStyle(fontSize: 15),
              ),
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
