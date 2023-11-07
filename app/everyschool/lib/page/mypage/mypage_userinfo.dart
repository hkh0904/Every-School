import 'package:everyschool/api/user_api.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MypageUserInfo extends StatefulWidget {
  const MypageUserInfo({super.key});

  @override
  State<MypageUserInfo> createState() => _MypageUserInfoState();
}

class _MypageUserInfoState extends State<MypageUserInfo> {
  var userNum = 1;

  final storage = FlutterSecureStorage();

  getCategoryUserInfo() async {
    var token = await storage.read(key: 'token') ?? "";
    var info = await UserApi().getUserInfo(token);
    print('정보 $info');
    return info;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getCategoryUserInfo(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            String imageAsset;
            if (snapshot.data['userType'] == 1001) {
              imageAsset = 'assets/images/mypage/student.png';
            } else if (snapshot.data['userType'] == 1002) {
              imageAsset = 'assets/images/mypage/parent.png';
            } else {
              imageAsset = 'assets/images/mypage/teacher.png';
            }
            return Container(
              width: MediaQuery.of(context).size.width,
              height: 330,
              decoration: BoxDecoration(
                image: DecorationImage(
                    image: AssetImage('assets/images/mypage/top.png'),
                    fit: BoxFit.cover),
              ),
              child: Column(children: [
                SizedBox(
                  height: 100,
                  child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        GestureDetector(
                          onTap: () {
                            Navigator.pop(context);
                          },
                          child: Padding(
                            padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
                            child: Icon(Icons.arrow_back, size: 25),
                          ),
                        ),
                        Text(
                          '내정보',
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w700),
                        )
                      ]),
                ),
                SizedBox(
                  height: MediaQuery.of(context).size.height * 0.07,
                ),
                Container(
                  decoration: BoxDecoration(
                      color: Colors.grey[50],
                      border: Border.all(color: Color(0xffd9d9d9)),
                      borderRadius: BorderRadius.circular(100)),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(100),
                    child: Image.asset(
                      imageAsset,
                      height: 100,
                      width: 100,
                    ),
                  ),
                ),
                SizedBox(
                  height: 5,
                ),
                Text(snapshot.data['school']['name']),
                Text(
                    '${snapshot.data['schoolClass']['grade']}학년 ${snapshot.data['schoolClass']['classNum']}반'),
                Text(
                  '${snapshot.data['name']}',
                  style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
                )
              ]),
            );
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
