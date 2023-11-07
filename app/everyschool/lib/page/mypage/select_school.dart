import 'package:everyschool/api/user_api.dart';
import 'package:flutter/material.dart';

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

  // @override
  // String toString() {
  //   return 'School(id: $schoolId, name: $name, address: $address)';
  // }
}

class SelectSchool extends StatefulWidget {
  const SelectSchool({Key? key}) : super(key: key);

  @override
  State<SelectSchool> createState() => _SelectSchoolState();
}

class _SelectSchoolState extends State<SelectSchool> {
  final UserApi userApi = UserApi();
  List<School> schoolData = [];

  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _gradeController = TextEditingController();
  final TextEditingController _classController = TextEditingController();
  final TextEditingController _searchController = TextEditingController();

  final LayerLink _layerLink = LayerLink();
  OverlayEntry? _overlayEntry;

  @override
  void initState() {
    super.initState();
    _searchController.addListener(_mayUpdateOverlay);
  }

  @override
  void dispose() {
    _nameController.dispose();
    _gradeController.dispose();
    _classController.dispose();
    _searchController.dispose();
    _overlayEntry?.remove();
    _searchController.removeListener(_mayUpdateOverlay);
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

  OverlayEntry _createOverlayEntry() {
    RenderBox renderBox = context.findRenderObject() as RenderBox;
    var size = renderBox.size;
    var offset = renderBox.localToGlobal(Offset.zero);

    return OverlayEntry(
      builder: (context) => Positioned(
        left: offset.dx,
        top: offset.dy + size.height + 100000,
        width: size.width * 0.86,
        child: CompositedTransformFollower(
          link: _layerLink,
          showWhenUnlinked: false,
          offset: Offset(0, 10.0), // 기준점에서의 y축 오프셋 조정
          child: Material(
            elevation: 4.0,
            child: ConstrainedBox(
              constraints: BoxConstraints(
                maxHeight: 250.0, // 최대 높이 설정, 필요에 따라 조정
              ),
              child: ListView.builder(
                padding: EdgeInsets.zero,
                itemCount: schoolData.length,
                itemBuilder: (context, index) {
                  var school = schoolData[index];
                  return ListTile(
                    title: Text(school.name),
                    onTap: () {
                      // Handle the school selection
                    },
                  );
                },
              ),
            ),
          ),
        ),
      ),
    );
  }

  void performSearch(String query) async {
    if (query.isNotEmpty) {
      final response = await userApi.getSchoolList(query);
      if (response is List) {
        // API가 Map의 리스트를 반환한다고 가정합니다.
        setState(() {
          schoolData = response.map((json) => School.fromJson(json)).toList();
        });
      }
    }
  }

  InputDecoration getTextFieldDecoration(String hint, {bool showIcon = false}) {
    return InputDecoration(
      filled: true,
      fillColor: Colors.white,
      hintText: hint,
      border: OutlineInputBorder(
        borderRadius: BorderRadius.circular(8),
      ),
      enabledBorder: OutlineInputBorder(
        borderSide: BorderSide(color: Colors.grey),
        borderRadius: BorderRadius.circular(8),
      ),
      focusedBorder: OutlineInputBorder(
        borderSide: BorderSide(color: Colors.blue),
        borderRadius: BorderRadius.circular(8),
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
    );
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).requestFocus(FocusNode());
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 0,
          centerTitle: true,
          leading: IconButton(
            iconSize: 30,
            icon: Icon(Icons.arrow_back, color: Colors.black),
            onPressed: () {
              Navigator.pop(context);
            },
          ),
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
              TextField(
                controller: _nameController,
                decoration: getTextFieldDecoration('이름을 입력하세요'),
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
                        decoration: getTextFieldDecoration('학년'),
                      ),
                    ),
                  ),
                  Expanded(
                    child: Padding(
                      padding: const EdgeInsets.only(left: 6.0),
                      child: TextField(
                        controller: _classController,
                        decoration: getTextFieldDecoration('반'),
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
