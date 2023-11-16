import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class MypageUserInfo extends StatefulWidget {
  const MypageUserInfo({super.key});

  @override
  State<MypageUserInfo> createState() => _MypageUserInfoState();
}

class _MypageUserInfoState extends State<MypageUserInfo> {
  var userNum = 1;

  final yearList = ['2023 년도'];
  String selectedYear = '2023 년도';
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
            return snapshot.data['userType'] == 1002
                ? Container(
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
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Row(
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  GestureDetector(
                                    onTap: () {
                                      Navigator.pop(context);
                                    },
                                    child: Padding(
                                      padding:
                                          EdgeInsets.fromLTRB(10, 0, 10, 0),
                                      child: Icon(Icons.arrow_back, size: 25),
                                    ),
                                  ),
                                ]),
                            Container(
                                padding: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                child: Row(
                                  children: [
                                    DropdownButton(
                                      value: selectedYear,
                                      items: yearList
                                          .map((e) => DropdownMenuItem(
                                                value:
                                                    e, // 선택 시 onChanged 를 통해 반환할 value
                                                child: Text(e),
                                              ))
                                          .toList(),
                                      onChanged: (value) {
                                        context
                                            .read<UserStore>()
                                            .setYear(value!.substring(0, 4));

                                        // items 의 DropdownMenuItem 의 value 반환
                                        setState(() {
                                          selectedYear = value;
                                        });
                                        print(selectedYear);
                                      },
                                    ),
                                  ],
                                ))
                          ],
                        ),
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
                      Text(
                        '${snapshot.data['name']}',
                        style: TextStyle(
                            fontWeight: FontWeight.w700, fontSize: 18),
                      ),
                      Text(
                        '등록된 자녀 수 ${snapshot.data['descendants'].length}명',
                        style: TextStyle(fontSize: 16),
                      ),
                    ]),
                  )
                : Container(
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
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Row(
                                crossAxisAlignment: CrossAxisAlignment.center,
                                children: [
                                  GestureDetector(
                                    onTap: () {
                                      Navigator.pop(context);
                                    },
                                    child: Padding(
                                      padding:
                                          EdgeInsets.fromLTRB(10, 0, 10, 0),
                                      child: Icon(Icons.arrow_back, size: 25),
                                    ),
                                  ),
                                ]),
                            Container(
                                padding: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                child: Row(
                                  children: [
                                    DropdownButton(
                                      value: selectedYear,
                                      items: yearList
                                          .map((e) => DropdownMenuItem(
                                                value:
                                                    e, // 선택 시 onChanged 를 통해 반환할 value
                                                child: Text(e),
                                              ))
                                          .toList(),
                                      onChanged: (value) {
                                        context
                                            .read<UserStore>()
                                            .setYear(value!.substring(0, 4));

                                        // items 의 DropdownMenuItem 의 value 반환
                                        setState(() {
                                          selectedYear = value;
                                        });
                                        print(selectedYear);
                                      },
                                    ),
                                  ],
                                ))
                          ],
                        ),
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
                        style: TextStyle(
                            fontWeight: FontWeight.w700, fontSize: 18),
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
