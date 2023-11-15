import 'package:everyschool/api/user_api.dart';
import 'package:flutter/services.dart';
import 'package:flutter/material.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:provider/provider.dart';

class School {
  final int schoolId;
  final String name;
  final String address;

  School({
    required this.schoolId,
    required this.name,
    required this.address,
  });

  factory School.fromJson(Map<String, dynamic> json) {
    return School(
      schoolId: json['schoolId'],
      name: json['name'],
      address: json['address'],
    );
  }
}

class SelectSchool extends StatefulWidget {
  const SelectSchool({Key? key}) : super(key: key);

  @override
  State<SelectSchool> createState() => _SelectSchoolState();
}

class _SelectSchoolState extends State<SelectSchool> {
  final UserApi userApi = UserApi();
  String? userName;
  List<School> schoolData = [];
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _gradeController = TextEditingController();
  final TextEditingController _classController = TextEditingController();
  final TextEditingController _searchController = TextEditingController();
  int? _selectedSchoolId;

  final LayerLink _layerLink = LayerLink();
  OverlayEntry? _overlayEntry;

  final storage = FlutterSecureStorage();

  findUserName() async {
    final name = await storage.read(key: 'userName') ?? "";
    print(name);
    print(name.runtimeType);
    setState(() {
      userName = name;
    });
    _nameController.text = name;
  }

  @override
  void initState() {
    super.initState();
    findUserName();

    _searchController.addListener(_mayUpdateOverlay);
  }

  @override
  void dispose() {
    _searchController.removeListener(_mayUpdateOverlay);
    _searchController.dispose();
    _nameController.dispose();
    _gradeController.dispose();
    _classController.dispose();
    _overlayEntry?.remove();
    super.dispose();
  }

  void _mayUpdateOverlay() {
    if (_searchController.text.isEmpty) {
      _overlayEntry?.remove();
      _overlayEntry = null;
    } else {
      _overlayEntry?.remove();
      _overlayEntry = _createOverlayEntry();
      Overlay.of(context).insert(_overlayEntry!);
    }
  }

  void _removeOverlay() {
    _overlayEntry?.remove();
    _overlayEntry = null;
  }

  Future<List<School>> performSearch(String query) async {
    List<School> schools = [];
    if (query.isNotEmpty) {
      final response = await userApi.getSchoolList(query);
      if (response is List) {
        schools = response.map((json) => School.fromJson(json)).toList();
      }
    }
    return schools; // Future<List<School>>를 반환합니다.
  }

  OverlayEntry _createOverlayEntry() {
    RenderBox renderBox = context.findRenderObject() as RenderBox;
    var size = renderBox.size;
    var offset = renderBox.localToGlobal(Offset.zero);

    return OverlayEntry(
      builder: (context) => Positioned(
        left: offset.dx,
        top: offset.dy + size.height,
        width: size.width * 0.855,
        child: CompositedTransformFollower(
          link: _layerLink,
          showWhenUnlinked: false,
          offset: Offset(0, 50.0),
          child: Material(
            elevation: 4.0,
            child: ConstrainedBox(
              constraints: BoxConstraints(
                maxHeight: 250.0, // 최대 높이 설정
              ),
              child: FutureBuilder<List<School>>(
                future: performSearch(_searchController.text), // 비동기 검색 함수 호출
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return Center(
                      child: SizedBox(
                        height: 50.0,
                        width: 50.0,
                        child: CircularProgressIndicator(),
                      ),
                    );
                  } else if (snapshot.hasError) {
                    return Center(child: Text('Error: ${snapshot.error}'));
                  } else if (snapshot.hasData) {
                    var schools = snapshot.data ?? [];
                    if (schools.isEmpty) {
                      return Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Text(
                          '검색 결과가 없습니다.',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontSize: 18, fontWeight: FontWeight.w600),
                        ),
                      );
                    }
                    return ListView.builder(
                      padding: EdgeInsets.zero,
                      itemCount: schools.length,
                      itemBuilder: (context, index) {
                        var school = schools[index];
                        return InkWell(
                          onTap: () {
                            setState(() {
                              _searchController.text = school.name;
                              _selectedSchoolId = school.schoolId;
                              _overlayEntry?.remove();
                              _overlayEntry = null;
                            });
                          },
                          child: Padding(
                            padding: EdgeInsets.symmetric(
                                horizontal: 16.0, vertical: 10.0), // 여기서 패딩 조정
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: <Widget>[
                                Text(
                                  school.name,
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w600),
                                ),
                                SizedBox(height: 4.0),
                                Text(
                                  school.address,
                                  style: TextStyle(
                                      color: Colors.grey, fontSize: 14),
                                ),
                              ],
                            ),
                          ),
                        );
                      },
                    );
                  } else {
                    // 데이터가 없고 로딩 중이 아닐 때의 메시지 표시
                    return Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text(
                        '검색 결과가 없습니다.',
                        textAlign: TextAlign.center,
                        style: TextStyle(fontSize: 18),
                      ),
                    );
                  }
                },
              ),
            ),
          ),
        ),
      ),
    );
  }

  Future<void> showResultDialog(BuildContext context, String message,
      {bool barrierDismissible = true}) async {
    return showDialog(
      barrierDismissible: barrierDismissible,
      context: context,
      builder: (context) {
        return AlertDialog(
          contentPadding: EdgeInsets.zero,
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              SizedBox(height: 40),
              Center(
                child: Text(
                  message,
                  style: TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
                ),
              ),
              SizedBox(height: 40),
              GestureDetector(
                onTap: () {
                  Navigator.of(context).pop(); // 다이얼로그 닫기
                  if (!barrierDismissible) {
                    Navigator.of(context).pop(); // 추가적인 pop
                  }
                },
                child: Container(
                  height: 50,
                  color: Color(0xff15075f),
                  child: Center(
                    child: Text(
                      "확인",
                      style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.w700,
                          fontSize: 18),
                    ),
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Future<void> _registerSchool(BuildContext context) async {
    if (_selectedSchoolId == null) {
      await showResultDialog(context, '학교를 선택해주세요.');
      return;
    }

    final grade = _gradeController.text;
    final classNum = _classController.text;

    if (grade.isEmpty || classNum.isEmpty) {
      await showResultDialog(context, '학년과 반을 모두 입력해주세요.');
      return;
    }

    final result =
        await userApi.registSchool(_selectedSchoolId!, grade, classNum);
    if (result != null) {
      // 등록 성공 시, 다이얼로그가 'barrierDismissible: false'로 설정됩니다.
      await showResultDialog(context, '학교 등록 신청에 성공하였습니다.\n곧 서비스를 이용하실 수 있습니다.',
          barrierDismissible: false);
    } else {
      await showResultDialog(context, '등록 실패');
    }
  }

  InputDecoration getTextFieldDecoration(String hint,
      {bool showIcon = false, String? suffixText}) {
    return InputDecoration(
      filled: false,
      hintText: hint,
      border: UnderlineInputBorder(
        borderSide: BorderSide(color: Colors.blue),
      ),
      enabledBorder: UnderlineInputBorder(
        borderSide: BorderSide(color: Colors.grey),
      ),
      focusedBorder: UnderlineInputBorder(
        borderSide: BorderSide(color: Colors.blue),
      ),
      contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
      suffixIcon: showIcon
          ? IconButton(
              icon: Icon(Icons.search),
              onPressed: () {
                performSearch(_searchController.text);
              },
            )
          : null,
      suffixText: suffixText,
      suffixStyle: TextStyle(color: Colors.black, fontSize: 18),
    );
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).requestFocus(FocusNode());
        _removeOverlay();
      },
      behavior: HitTestBehavior.translucent,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 0,
          centerTitle: true,
          // leading: IconButton(
          //   iconSize: 30,
          //   icon: Icon(Icons.arrow_back, color: Colors.black),
          //   onPressed: () {
          //     Navigator.pop(context);
          //   },
          // ),
          title: Text(
            '학교 등록하기',
            style: TextStyle(
              color: Color(0XFF15075F),
              fontSize: 24,
              fontWeight: FontWeight.w700,
            ),
          ),
        ),
        body: SingleChildScrollView(
          padding: EdgeInsets.fromLTRB(30, 50, 30, 0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                '학교 선택하기',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.w700,
                ),
              ),
              SizedBox(height: 10),
              CompositedTransformTarget(
                link: _layerLink,
                child: TextField(
                  controller: _searchController,
                  style: TextStyle(fontSize: 18),
                  // onTap: () {
                  //   // 오버레이가 열릴 때 포커스를 잃지 않게 하기 위해서 onTap을 빈 콜백으로 오버라이드합니다.
                  // },
                  onChanged: (value) => performSearch(value), // 입력할 때마다 검색 수행
                  decoration: getTextFieldDecoration('학교 검색', showIcon: true),
                ),
              ),
              SizedBox(height: 25),
              Text(
                '이름',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.w700,
                ),
              ),
              SizedBox(height: 10),
              Container(
                width: double.infinity,
                padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 10.0),
                decoration: BoxDecoration(
                  border: Border(
                    bottom: BorderSide(
                      color: Colors.grey, // 포커스 여부에 따라 색상 변경
                    ),
                  ),
                ),
                child: Text(
                  _nameController.text,
                  textAlign: TextAlign.center,
                  style: TextStyle(
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
              ),
              SizedBox(height: 25),
              Text(
                '학년 / 반',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.w700,
                ),
              ),
              SizedBox(height: 10),
              Row(
                children: [
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.only(right: 6.0),
                      child: TextField(
                        controller: _gradeController,
                        textAlign: TextAlign.center,
                        style: TextStyle(fontSize: 18),
                        keyboardType: TextInputType.number, // 숫자 키보드 타입
                        inputFormatters: [
                          FilteringTextInputFormatter.digitsOnly
                        ], // 숫자만 입력 허용
                        decoration: getTextFieldDecoration('',
                            suffixText: '학년'), // 우측에 "학년" 텍스트 추가
                      ),
                    ),
                  ),
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.only(left: 6.0),
                      child: TextField(
                        controller: _classController,
                        textAlign: TextAlign.center,
                        style: TextStyle(fontSize: 18),
                        keyboardType: TextInputType.number, // 숫자 키보드 타입
                        inputFormatters: [
                          FilteringTextInputFormatter.digitsOnly
                        ], // 숫자만 입력 허용
                        decoration: getTextFieldDecoration('',
                            suffixText: '반'), // 우측에 "반" 텍스트 추가
                      ),
                    ),
                  ),
                ],
              ),
              SizedBox(height: 40),
              Row(
                children: <Widget>[
                  Expanded(
                    child: InkWell(
                      onTap: () => _registerSchool(context),
                      child: Container(
                        padding: EdgeInsets.fromLTRB(20, 15, 20, 15),
                        decoration: BoxDecoration(
                          color: Color(0xFF15075F),
                          borderRadius: BorderRadius.all(Radius.circular(8.0)),
                        ),
                        child: Text(
                          '제출하기',
                          style: TextStyle(
                              fontSize: 25,
                              fontWeight: FontWeight.w800,
                              color: Colors.white),
                          textAlign: TextAlign.center, // 텍스트를 컨테이너 중앙에 위치시킴
                        ),
                      ),
                    ),
                  ),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
