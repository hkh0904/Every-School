import 'package:everyschool/api/user_api.dart';
import 'package:everyschool/page/login/login_page.dart';
import 'package:everyschool/page/signup/private_policy.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class AddInfoForm extends StatefulWidget {
  const AddInfoForm({super.key, this.email, this.password, this.type});

  final email;
  final password;
  final type;

  @override
  State<AddInfoForm> createState() => _AddInfoFormState();
}

class _AddInfoFormState extends State<AddInfoForm> {
  String? devicetoken;
  TextEditingController name = TextEditingController();

  TextEditingController year = TextEditingController();
  TextEditingController month = TextEditingController();
  TextEditingController day = TextEditingController();
  TextEditingController birthday = TextEditingController();

  final TextEditingController genderController = TextEditingController();
  GenderLabel? selectedGender = GenderLabel.male;
  String? finalGender;

  bool yearcheck = false;
  bool monthcheck = false;
  bool daycheck = false;
  final storage = FlutterSecureStorage();

  void getToken() async {
    devicetoken = await storage.read(key: 'token') ?? "";
  }

  @override
  void initState() {
    super.initState();
    getToken();
    finalGender = selectedGender!.gender;
  }

  @override
  Widget build(BuildContext context) {
    final List<DropdownMenuEntry<GenderLabel>> genderList =
        <DropdownMenuEntry<GenderLabel>>[];
    for (final GenderLabel gender in GenderLabel.values) {
      genderList.add(
        DropdownMenuEntry<GenderLabel>(value: gender, label: gender.label),
      );
    }
    return Scaffold(
      appBar: AppBar(
        title: Text(
          '회원가입',
          style: TextStyle(
              fontSize: 25, color: Colors.black, fontWeight: FontWeight.w700),
        ),
        elevation: 0.0,
        backgroundColor: Colors.grey[50],
        centerTitle: true,
        toolbarHeight: 65,
        leading: IconButton(
          color: Colors.black,
          icon: Icon(Icons.keyboard_backspace_rounded),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.fromLTRB(30, 0, 30, 0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.values.first,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Padding(
                padding: const EdgeInsets.fromLTRB(0, 30, 0, 30),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: const [
                    Text(
                      '추가 정보',
                      style:
                          TextStyle(fontSize: 20, fontWeight: FontWeight.w700),
                    ),
                    Text('학교에 등록된 정보와 다를 경우\n학급 승인이 불가능 할 수 있습니다.'),
                  ],
                ),
              ),
              Text(
                '이름',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 30),
                child: TextField(
                  controller: name,
                  decoration: InputDecoration(
                      focusedBorder: UnderlineInputBorder(
                          borderSide:
                              BorderSide(width: 1.5, color: Color(0xff15075F))),
                      counterText: '',
                      border: UnderlineInputBorder(),
                      isDense: true,
                      contentPadding: EdgeInsets.symmetric(
                          vertical: 14.0, horizontal: 10.0),
                      focusColor: Color(0xff15075F)),

                  keyboardType: TextInputType.text,
                  // obscureText: true, // 비밀번호 안보이도록 하는 것111
                ),
              ),
              Text(
                '생년월일',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(0, 0, 0, 30),
                child: Row(
                  textBaseline: TextBaseline.ideographic,
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.baseline,
                  children: [
                    Flexible(
                        flex: 3,
                        child: TextField(
                          maxLength: 4,
                          onChanged: (value) {
                            if (!RegExp(
                                    r"^(19[0-9][0-9]|20[01][0-9]|202[0-3])$")
                                .hasMatch(value)) {
                              setState(() {
                                yearcheck = false;
                              });
                            } else {
                              setState(() {
                                yearcheck = true;
                              });
                            }
                          },
                          controller: year,
                          cursorColor: Color(0xff15075F),
                          decoration: InputDecoration(
                              enabledBorder: yearcheck
                                  ? UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 1.5, color: Colors.black),
                                    )
                                  : UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 2, color: Colors.red)),
                              focusedBorder: UnderlineInputBorder(
                                borderSide: BorderSide(
                                    width: 1.5,
                                    color: yearcheck
                                        ? Color(0xff15075F)
                                        : Colors.red),
                              ),
                              counterText: '',
                              border: UnderlineInputBorder(),
                              isDense: true,
                              contentPadding: EdgeInsets.symmetric(
                                  vertical: 14.0, horizontal: 10.0),
                              focusColor: Color(0xff15075F)),
                          keyboardType: TextInputType.datetime,
                        )),
                    Flexible(
                        child: Text(' 년 ',
                            style: TextStyle(
                              fontSize: 17,
                              fontWeight: FontWeight.w400,
                            ))),
                    Flexible(
                        flex: 2,
                        child: TextField(
                          maxLength: 2,
                          onChanged: (value) {
                            if (!RegExp(r"^(0?[1-9]|1[0-2])$")
                                .hasMatch(value)) {
                              setState(() {
                                monthcheck = false;
                              });
                            } else {
                              setState(() {
                                monthcheck = true;
                              });
                            }
                          },
                          controller: month,
                          cursorColor: Color(0xff15075F),
                          decoration: InputDecoration(
                              enabledBorder: monthcheck
                                  ? UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 1.5, color: Colors.black),
                                    )
                                  : UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 2, color: Colors.red)),
                              focusedBorder: UnderlineInputBorder(
                                borderSide: BorderSide(
                                    width: 1.5,
                                    color: monthcheck
                                        ? Color(0xff15075F)
                                        : Colors.red),
                              ),
                              counterText: '',
                              border: UnderlineInputBorder(),
                              isDense: true,
                              contentPadding: EdgeInsets.symmetric(
                                  vertical: 14.0, horizontal: 10.0),
                              focusColor: Color(0xff15075F)),
                          keyboardType: TextInputType.datetime,
                        )),
                    Flexible(
                        child: Text(' 월 ',
                            style: TextStyle(
                              fontSize: 17,
                              fontWeight: FontWeight.w400,
                            ))),
                    Flexible(
                        flex: 2,
                        child: TextField(
                          onChanged: (value) {
                            if (!RegExp(r"^(0?[1-9]|[12][0-9]|3[01])$")
                                .hasMatch(value)) {
                              setState(() {
                                daycheck = false;
                              });
                            } else {
                              setState(() {
                                daycheck = true;
                              });
                            }
                          },
                          maxLength: 2,
                          cursorColor: Color(0xff15075F),
                          controller: day,
                          decoration: InputDecoration(
                              enabledBorder: daycheck
                                  ? UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 1.5, color: Colors.black),
                                    )
                                  : UnderlineInputBorder(
                                      borderSide: BorderSide(
                                          width: 2, color: Colors.red)),
                              focusedBorder: UnderlineInputBorder(
                                borderSide: BorderSide(
                                    width: 1.5,
                                    color: daycheck
                                        ? Color(0xff15075F)
                                        : Colors.red),
                              ),
                              counterText: '',
                              border: UnderlineInputBorder(),
                              isDense: true,
                              contentPadding: EdgeInsets.symmetric(
                                  vertical: 14.0, horizontal: 10.0),
                              focusColor: Color(0xff15075F)),
                          keyboardType: TextInputType.datetime,
                        )),
                    Flexible(
                        child: Text(' 일 ',
                            style: TextStyle(
                              fontSize: 17,
                              fontWeight: FontWeight.w400,
                            ))),
                  ],
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 40),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '성별',
                      style: TextStyle(
                        fontSize: 16,
                        fontWeight: FontWeight.w700,
                      ),
                    ),
                    Center(
                      child: DropdownMenu<GenderLabel>(
                        width: MediaQuery.of(context).size.width - 60,
                        inputDecorationTheme: InputDecorationTheme(
                          constraints: BoxConstraints(maxHeight: 50),
                          isDense: true,
                          contentPadding: EdgeInsets.symmetric(
                              vertical: 14.0, horizontal: 16.0),
                          border: UnderlineInputBorder(),
                        ),
                        menuStyle: MenuStyle(
                            shadowColor:
                                MaterialStatePropertyAll(Color(0xff15075F))),
                        initialSelection: GenderLabel.male,
                        controller: genderController,
                        dropdownMenuEntries: genderList,
                        onSelected: (GenderLabel? gender) {
                          setState(() {
                            selectedGender = gender;
                            finalGender = selectedGender!.gender;
                          });
                        },
                      ),
                    ),
                  ],
                ),
              ),
              Padding(
                  padding: EdgeInsets.all(1),
                  child: GestureDetector(
                    onTap: () {
                      if (context.read<UserStore>().policycheck) {
                        context.read<UserStore>().changePolicyCheck();
                        setState(() {});
                      } else {
                        showDialog(
                          context: context,
                          builder: ((context) {
                            return AlertDialog(
                              actions: <Widget>[
                                TextButton(
                                  onPressed: () {
                                    context
                                        .read<UserStore>()
                                        .changePolicyCheck();
                                    setState(() {});

                                    Navigator.of(context).pop();
                                  },
                                  child: Text('동의'),
                                ),
                              ],
                              title: Text('<채움> 개인정보 처리 방안'),
                              content: SizedBox(
                                height: 300,
                                child: SingleChildScrollView(
                                  child: Policycheck(),
                                ),
                              ),
                            );
                          }),
                        );
                      }
                    },
                    child: Row(
                      children: [
                        Checkbox(
                            value: context.watch<UserStore>().policycheck,
                            onChanged: null),
                        Text('개인정보 처리방침에 동의합니다.'),
                      ],
                    ),
                  )),
              SizedBox(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Flexible(
                      child: TextButton(
                          onPressed: yearcheck &&
                                  monthcheck &&
                                  daycheck &&
                                  context.watch<UserStore>().policycheck
                              ? () async {
                                  print(widget.type);
                                  if (month.text.length == 1) {
                                    month.text = '0${month.text}';
                                  }
                                  if (day.text.length == 1) {
                                    day.text = '0${day.text}';
                                  }
                                  birthday.text =
                                      '${year.text}-${month.text}-${day.text}';

                                  final response = widget.type == '1002'
                                      ? await UserApi().parentSignUp(
                                          widget.type,
                                          widget.email.text,
                                          widget.password.text,
                                          name.text,
                                          birthday.text,
                                          finalGender)
                                      : await UserApi().studentSignUp(
                                          widget.type,
                                          widget.email.text,
                                          widget.password.text,
                                          name.text,
                                          birthday.text);

                                  print('여기는 나와서 ${response['data']}');
                                  if (response['data'] is Map) {
                                    showDialog(
                                        context: context,
                                        builder: ((context) {
                                          return WillPopScope(
                                            onWillPop: () async {
                                              return false;
                                            },
                                            child: AlertDialog(
                                              actions: <Widget>[
                                                TextButton(
                                                    onPressed: () {
                                                      Navigator.pushAndRemoveUntil(
                                                          context,
                                                          MaterialPageRoute(
                                                              builder: (context) =>
                                                                  const LoginPage()),
                                                          (Route<dynamic>
                                                                  route) =>
                                                              false);
                                                    },
                                                    child: Text('닫기'))
                                              ],
                                              content: SingleChildScrollView(
                                                child: Text('회원가입이 완료됐습니다.'),
                                              ),
                                            ),
                                          );
                                        }));
                                  } else {
                                    showDialog(
                                        context: context,
                                        builder: ((context) {
                                          return AlertDialog(
                                            actions: <Widget>[
                                              TextButton(
                                                  onPressed: () {
                                                    Navigator.of(context).pop();
                                                  },
                                                  child: Text('닫기'))
                                            ],
                                            content: SingleChildScrollView(
                                              child: Text('나중에 다시 시도해주세요.'),
                                            ),
                                          );
                                        }));
                                  }
                                }
                              : null,
                          style: ButtonStyle(
                              backgroundColor: yearcheck &&
                                      monthcheck &&
                                      daycheck &&
                                      context.watch<UserStore>().policycheck
                                  ? MaterialStatePropertyAll(Color(0xff15075F))
                                  : MaterialStatePropertyAll(Colors.grey)),
                          child: SizedBox(
                            height: 30,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: const [
                                Text(
                                  '회원가입 완료',
                                  style: TextStyle(
                                      fontWeight: FontWeight.w700,
                                      color: Colors.white),
                                ),
                              ],
                            ),
                          )),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

enum GenderLabel {
  male('남자', 'M'),
  female('여자', 'F');

  const GenderLabel(this.label, this.gender);
  final String label;
  final String gender;
}
