import 'package:everyschool/page/mypage/userinfo_correction.dart';
import 'package:flutter/material.dart';

class MypagePersonalMenu extends StatefulWidget {
  const MypagePersonalMenu({super.key});

  @override
  State<MypagePersonalMenu> createState() => _MypagePersonalMenuState();
}

class _MypagePersonalMenuState extends State<MypagePersonalMenu> {
  int userNum = 1;
  var perMenulist = [
    ['스크랩한 글', '개인정보 수정', '비밀번호 변경'],
    ['스크랩한 글', '개인정보 수정', '비밀번호 변경'],
    ['공지사항 관리', '비밀번호 변경']
  ];

  var perPagelist = [
    ['Scrap', 'UserInfoCorrection', 'ChangePwd'],
    ['Scrap', 'UserInfoCorrection', 'ChangePwd'],
    ['Notification', 'ChangePwd']
  ];

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(30, 35, 30, 20),
      width: MediaQuery.of(context).size.width,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: List.generate(
          perMenulist[userNum - 1].length,
          (index) {
            return GestureDetector(
              onTap: () {
                String pageName = perPagelist[userNum - 1][index];
                Widget pageWidget = Container();

                if (pageName == 'UserInfoCorrection') {
                  pageWidget = UserInfoCorrection();
                }
                // else if (pageName == 'Scrap') {
                //   pageWidget = Scrap();
                // }
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => pageWidget),
                );
              },
              child: Container(
                padding: EdgeInsets.fromLTRB(15, 20, 15, 20),
                margin: EdgeInsets.fromLTRB(0, 0, 0, 10),
                width: MediaQuery.of(context).size.width,
                decoration: BoxDecoration(
                    border: Border.all(width: 1, color: Color(0xff9A9A9A)),
                    borderRadius: BorderRadius.circular(8)),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(perMenulist[userNum - 1][index],
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
    ;
  }
}
