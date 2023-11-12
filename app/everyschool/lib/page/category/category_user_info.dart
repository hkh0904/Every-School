import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/mypage/mypage.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class CategoryUserInfo extends StatefulWidget {
  const CategoryUserInfo({super.key});

  @override
  State<CategoryUserInfo> createState() => _CategoryUserInfoState();
}

class _CategoryUserInfoState extends State<CategoryUserInfo> {
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
            return Column(
              children: [
                SizedBox(
                  height: MediaQuery.of(context).size.height * 0.05,
                ),
                Container(
                  decoration: BoxDecoration(
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
                  height: 10,
                ),
                Text(
                  '환영합니다. ${snapshot.data['name']}님!',
                  style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
                ),
                SizedBox(
                  height: 15,
                ),
                GestureDetector(
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => const MyPage()));
                  },
                  child: Container(
                      padding: EdgeInsets.fromLTRB(15, 10, 15, 10),
                      decoration: BoxDecoration(
                          border:
                              Border.all(color: Color(0xffd9d9d9), width: 0.5),
                          borderRadius: BorderRadius.circular(20),
                          gradient: LinearGradient(
                            begin: Alignment.topLeft,
                            end: Alignment.bottomRight,
                            colors: [Color(0xff15075F), Color(0xff594AAA)],
                          )),
                      child: Text(
                        '내정보',
                        style: TextStyle(
                            fontWeight: FontWeight.w600, color: Colors.white),
                      )),
                ),
                SizedBox(
                  height: 50,
                ),
              ],
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
              height: 208,
            );
          }
        });
  }
}
