import 'dart:convert';

import 'package:everyschool/api/firebase_api.dart';
import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/global_variable.dart';
import 'package:everyschool/page/messenger/chat/chat_controller.dart';
import 'package:everyschool/page/category/category_page.dart';
import 'package:everyschool/page/consulting/consulting_list_page.dart';
import 'package:everyschool/page/report_consulting/consulting_list_teacher.dart';
import 'package:everyschool/page/home/home_page.dart';
import 'package:everyschool/page/main/bottom_navigation.dart';
import 'package:everyschool/page/main/splash.dart';
import 'package:everyschool/page/community/community_page.dart';
import 'package:everyschool/page/messenger/messenger_page.dart';
import 'package:everyschool/page/report/my%20_report_list_page.dart';
import 'package:everyschool/page/report_consulting/teacher_report_consulting_page.dart';
import 'package:everyschool/store/call_store.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_callkit_incoming/entities/entities.dart';
import 'package:flutter_callkit_incoming/flutter_callkit_incoming.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:intl/date_symbol_data_local.dart';
// fcm
import 'package:firebase_core/firebase_core.dart';
import 'package:provider/provider.dart';
import 'package:uuid/uuid.dart';
import 'firebase_options.dart';
// timezone
import 'package:timezone/data/latest.dart' as tz;
import 'package:timezone/timezone.dart' as tz;

import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

void main() async {
  tz.initializeTimeZones();
  tz.setLocalLocation(tz.getLocation('Asia/Seoul'));
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );

  SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle(
    statusBarColor: Colors.transparent,
  ));
  await initializeDateFormatting();

  runApp(MultiProvider(
    providers: [
      ChangeNotifierProvider(create: (c) => ChatController()),
      ChangeNotifierProvider(create: (c) => UserStore()),
      ChangeNotifierProvider(create: (c) => CallStore()),
    ],
    child: MaterialApp(
        localizationsDelegates: [
          AppLocalizations.delegate,
          GlobalMaterialLocalizations.delegate, // Add this line
          GlobalWidgetsLocalizations.delegate, // Add this line
          GlobalCupertinoLocalizations.delegate, // Add this line
        ],
        supportedLocales: const [
          Locale('ko'),
        ],
        navigatorKey: CandyGlobalVariable.naviagatorState,
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
            fontFamily: "Pretendard",
            appBarTheme: AppBarTheme(
              iconTheme: IconThemeData(color: Colors.black),
            )),
        home: Splash()),
  ));
}

class Main extends StatefulWidget {
  const Main({super.key});

  @override
  State<Main> createState() => _MainState();
}

class _MainState extends State<Main> {
  String? fcmToken;
  final storage = FlutterSecureStorage();

  getuserType() async {
    final storage = FlutterSecureStorage();
    var userType = await storage.read(key: 'usertype') ?? "";
    return int.parse(userType);
  }

  saveUserInfo() async {
    var token = await storage.read(key: 'token') ?? "";
    final userinfo = await UserApi().getUserInfo(token);
    await Provider.of<UserStore>(context, listen: false).setUserInfo(userinfo);
    print('여기는 스토아 ');
    print(context.read<UserStore>().userInfo);
  }

  @override
  void initState() {
    super.initState();
    // storage.delete(key: 'token');
    FirebaseApi().getMyDeviceToken();
    FirebaseApi().setupInteractedMessage(context);
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      FirebaseApi().foregroundMessage(message);
    });
    FirebaseMessaging.onBackgroundMessage(firebaseMessagingBackgroundHandler);
    FirebaseApi().initializeNotifications(context);
    FirebaseApi().getIncomingCall(context);
    // checkAndNavigationCallingPage();
    saveUserInfo();
  }

  int selectedIndex = 0;
  void onItemTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
  }

  int userNum = 1003;
  DateTime? currentBackPressTime;

  Future<bool> onWillPop() {
    DateTime now = DateTime.now();
    if (currentBackPressTime == null ||
        now.difference(currentBackPressTime!) > Duration(seconds: 2)) {
      currentBackPressTime = now;
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          backgroundColor: Color(0xff15075f),
          content: Text(
            '한번 더 뒤로가기를 누를 시 종료됩니다',
            style: TextStyle(color: Colors.white),
          ),
          duration: Duration(seconds: 2),
        ),
      );
      return Future.value(false);
    }
    SystemNavigator.pop();
    return Future.value(true);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: getuserType(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          List<Widget> getPagesForUser(int userNum) {
            switch (snapshot.data) {
              case 1001:
                return [
                  HomePage(),
                  ReportListPage(),
                  MessengerPage(indexNum: 0),
                  CommunityPage(),
                  CategoryPage(),
                ];
              case 1002:
                return [
                  HomePage(),
                  ConsultingListPage(),
                  MessengerPage(indexNum: 0),
                  CommunityPage(),
                  CategoryPage(),
                ];
              default:
                return [
                  HomePage(),
                  ReportConsultingPage(index: 0),
                  MessengerPage(indexNum: 0),
                  CommunityPage(),
                  CategoryPage(),
                ];
            }
          }

          if (snapshot.hasData) {
            List<Widget> pages = getPagesForUser(userNum);
            return WillPopScope(
              onWillPop: onWillPop,
              child: Scaffold(
                body: pages[selectedIndex],
                bottomNavigationBar: SizedBox(
                    height: 70,
                    child: BtmNav(
                        selectedIndex: selectedIndex,
                        onItemTapped: onItemTapped)),
              ),
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
