import 'package:everyschool/page/mypage/change_password.dart';
import 'package:everyschool/page/mypage/mute_time_set.dart';
import 'package:everyschool/page/mypage/my_like_post.dart';
import 'package:everyschool/page/mypage/userinfo_correction.dart';
import 'package:everyschool/page/mypage/register_parents.dart';
import 'package:everyschool/page/mypage/register_child.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class MypagePersonalMenu extends StatefulWidget {
  const MypagePersonalMenu({super.key});

  @override
  State<MypagePersonalMenu> createState() => _MypagePersonalMenuState();
}

class _MypagePersonalMenuState extends State<MypagePersonalMenu> {
  final storage = FlutterSecureStorage();

  var perMenulist = [
    ['학부모 등록하기', '좋아요한 글', '개인정보 수정', '비밀번호 변경'],
    ['자녀 등록하기', '개인정보 수정', '비밀번호 변경'],
    ['개인정보 수정', '비밀번호 변경', '방해금지 설정']
  ];

  var perPagelist = [
    [RegisterParents(), MyLikePost(), UserInfoCorrection(), ChangePassword()],
    [RegisterChild(), UserInfoCorrection(), ChangePassword()],
    [UserInfoCorrection(), ChangePassword(), MuteTimeSet()]
  ];

  getuserType() async {
    var userType = await storage.read(key: 'usertype');
    var intUserType = userType!.substring(userType.length - 1);

    return int.parse(intUserType);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            return Container(
              margin: EdgeInsets.fromLTRB(30, 35, 30, 20),
              width: MediaQuery.of(context).size.width,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: List.generate(
                  perMenulist[snapshot.data - 1].length,
                  (index) {
                    return GestureDetector(
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) =>
                                  perPagelist[snapshot.data - 1][index]),
                        );
                      },
                      child: Container(
                        padding: EdgeInsets.fromLTRB(15, 20, 15, 20),
                        margin: EdgeInsets.fromLTRB(0, 0, 0, 10),
                        width: MediaQuery.of(context).size.width,
                        decoration: BoxDecoration(
                            border:
                                Border.all(width: 1, color: Color(0xff9A9A9A)),
                            borderRadius: BorderRadius.circular(8)),
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(perMenulist[snapshot.data - 1][index],
                                style: TextStyle(fontWeight: FontWeight.w600)),
                            Icon(
                              Icons.arrow_forward_ios,
                              size: 14,
                            )
                          ],
                        ),
                      ),
                    );
                  },
                ),
              ),
            );
          } else if (snapshot.hasError) {
            return Container(
              height: 800,
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
