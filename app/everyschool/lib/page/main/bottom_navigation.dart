import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class BtmNav extends StatefulWidget {
  const BtmNav({super.key, this.selectedIndex, this.onItemTapped});
  final selectedIndex;
  final onItemTapped;

  @override
  State<BtmNav> createState() => _BtmNavState();
}

class _BtmNavState extends State<BtmNav> {
  int? userNum;
  final storage = FlutterSecureStorage();

  getuserType() async {
    final storage = FlutterSecureStorage();
    var userType = await storage.read(key: 'usertype') ?? "";
    return int.parse(userType);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
            String label;
            switch (snapshot.data) {
              case 1001:
                label = '신고';
                break;
              case 1002:
                label = '상담';
                break;
              default:
                label = '신고 및 상담';
                break;
            }
            return BottomNavigationBar(
              type: BottomNavigationBarType.fixed,
              items: <BottomNavigationBarItem>[
                BottomNavigationBarItem(
                  icon: Icon(Icons.home_outlined),
                  activeIcon: Icon(Icons.home),
                  label: '홈',
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.report_outlined),
                  activeIcon: Icon(Icons.report),
                  label: label,
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.mail_outlined),
                  activeIcon: Icon(Icons.mail),
                  label: '메신저',
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.dashboard_outlined),
                  activeIcon: Icon(Icons.dashboard),
                  label: '커뮤니티',
                ),
                BottomNavigationBarItem(
                  icon: Icon(Icons.menu),
                  activeIcon: Icon(Icons.menu),
                  label: '전체보기',
                ),
              ],
              currentIndex: widget.selectedIndex,
              onTap: widget.onItemTapped,
              selectedItemColor: Color(0xff15075F),
              selectedFontSize: 12,
              iconSize: 28,
              unselectedItemColor: Color(0xff94A3B8),
              unselectedFontSize: 12,
              showUnselectedLabels: true,
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
