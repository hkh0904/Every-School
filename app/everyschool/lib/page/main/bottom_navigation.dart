import 'package:flutter/material.dart';

class BtmNav extends StatefulWidget {
  const BtmNav({super.key, this.selectedIndex, this.onItemTapped});
  final selectedIndex;
  final onItemTapped;

  @override
  State<BtmNav> createState() => _BtmNavState();
}

class _BtmNavState extends State<BtmNav> {
  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      type: BottomNavigationBarType.fixed,
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(Icons.report_outlined),
          activeIcon: Icon(Icons.report),
          label: '신고 및 상담',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.person_outlined),
          activeIcon: Icon(Icons.person),
          label: '연락처',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.home_outlined),
          activeIcon: Icon(Icons.home),
          label: '홈',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.mail_outlined),
          activeIcon: Icon(Icons.mail),
          label: '메세지',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.dashboard_outlined),
          activeIcon: Icon(Icons.dashboard),
          label: '커뮤니티',
        ),
      ],
      currentIndex: widget.selectedIndex,
      onTap: widget.onItemTapped,
      selectedItemColor: Color(0xff15075F),
      selectedFontSize: 12,
      iconSize: 26,
      unselectedItemColor: Color(0xff94A3B8),
      unselectedFontSize: 12,
      showUnselectedLabels: true,
    );
  }
}
