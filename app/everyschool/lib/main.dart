import 'package:everyschool/api/firebase_api.dart';
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
import 'package:everyschool/store/chat_store.dart';
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

void main() async {
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
      ChangeNotifierProvider(create: (c) => ChatStore()),
      ChangeNotifierProvider(create: (c) => ChatController()),
      ChangeNotifierProvider(create: (c) => UserStore()),
    ],
    child: MaterialApp(
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

  @override
  void initState() {
    super.initState();

    FirebaseApi().getMyDeviceToken();
    FirebaseApi().setupInteractedMessage(context);
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      FirebaseApi().foregroundMessage(message);
    });
    FirebaseMessaging.onBackgroundMessage(firebaseMessagingBackgroundHandler);
    FirebaseApi().initializeNotifications(context);
    FirebaseApi().getIncomingCall(context);
    // checkAndNavigationCallingPage();
  }

  int selectedIndex = 0;
  void onItemTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
  }

  int userNum = 1003;

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
                  MessengerPage(),
                  CommunityPage(),
                  CategoryPage(),
                ];
              case 1002:
                return [
                  HomePage(),
                  ConsultingListPage(),
                  MessengerPage(),
                  CommunityPage(),
                  CategoryPage(),
                ];
              default:
                return [
                  HomePage(),
                  ReportConsultingPage(index: 0),
                  MessengerPage(),
                  CommunityPage(),
                  CategoryPage(),
                ];
            }
          }

          if (snapshot.hasData) {
            List<Widget> pages = getPagesForUser(userNum);
            return Scaffold(
              body: pages[selectedIndex],
              bottomNavigationBar: SizedBox(
                  height: 70,
                  child: BtmNav(
                      selectedIndex: selectedIndex,
                      onItemTapped: onItemTapped)),
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
