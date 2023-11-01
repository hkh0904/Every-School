import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';

class CreatePostBody extends StatefulWidget {
  const CreatePostBody({Key? key}) : super(key: key);

  @override
  State<CreatePostBody> createState() => _CreatePostBodyState();
}

class _CreatePostBodyState extends State<CreatePostBody> {
  final TextEditingController _titleController = TextEditingController();
  final TextEditingController _contentController = TextEditingController();
  List<String> attachedFileNames = [];

  Future<void> pickFile() async {
    FilePickerResult? result = await FilePicker.platform.pickFiles(
      allowMultiple: true,
    );

    if (result != null) {
      List<String> newAttachedFileNames = result.paths
          .map((path) => path!.split('/').last)
          .toList(); // 선택된 모든 파일의 이름을 저장합니다.

      setState(() {
        attachedFileNames
            .addAll(newAttachedFileNames); // 기존 파일 이름 리스트에 새로운 파일 이름들을 추가합니다.
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScope.of(context).unfocus();
      },
      child: WillPopScope(
        onWillPop: () async {
          Navigator.pop(context); // 이전 페이지로 돌아갑니다.
          return true;
        },
        child: Scaffold(
          body: SingleChildScrollView(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  '제목',
                  style: TextStyle(
                    color: Colors.grey,
                    fontSize: 20,
                    fontWeight: FontWeight.w700,
                  ),
                ),
                SizedBox(height: 10),
                TextFormField(
                  controller: _titleController,
                  decoration: InputDecoration(
                    hintText: '제목을 입력하세요',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(25),
                      borderSide:
                          BorderSide(color: Color(0XFF15075F), width: 2),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Text(
                  '내용',
                  style: TextStyle(
                    color: Colors.grey,
                    fontSize: 20,
                    fontWeight: FontWeight.w700,
                  ),
                ),
                SizedBox(height: 10),
                TextFormField(
                  controller: _contentController,
                  decoration: InputDecoration(
                    hintText: '내용을 입력하세요',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(30),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(30),
                      borderSide:
                          BorderSide(color: Color(0XFF15075F), width: 2),
                    ),
                  ),
                  maxLines: 15,
                ),
                SizedBox(height: 20),
                Text(
                  '첨부파일',
                  style: TextStyle(
                    color: Colors.grey,
                    fontSize: 20,
                    fontWeight: FontWeight.w700,
                  ),
                ),
                SizedBox(height: 10),
                Container(
                  padding: EdgeInsets.fromLTRB(20, 0, 10, 0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(20),
                    border: Border.all(
                      color: Colors.grey,
                      width: 1.0,
                    ),
                  ),
                  child: Column(
                    children: [
                      for (var fileName in attachedFileNames)
                        Row(
                          children: [
                            Expanded(
                              child: Text(
                                fileName,
                                style: TextStyle(
                                  color: Colors.black,
                                ),
                              ),
                            ),
                            IconButton(
                              iconSize: 30,
                              onPressed: () {
                                setState(() {
                                  attachedFileNames.remove(fileName); // 파일 삭제
                                });
                              },
                              icon: Icon(
                                Icons.remove,
                                color: Colors.red,
                              ),
                            ),
                          ],
                        ),
                      Row(
                        children: [
                          Expanded(
                            child: attachedFileNames.isEmpty
                                ? Text(
                                    '파일을 추가하세요.',
                                    style: TextStyle(
                                      color: Colors.grey,
                                    ),
                                  )
                                : Container(),
                          ),
                          IconButton(
                            iconSize: 30,
                            onPressed: pickFile,
                            icon: Icon(
                              Icons.add,
                              color: Color(0XFF15075F),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
                SizedBox(
                  height: 30,
                ),
                Container(
                  padding: EdgeInsets.fromLTRB(0, 13, 0, 13),
                  decoration: BoxDecoration(
                    color: Color(0XFF15075F),
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: const [
                      Text(
                        '작성 완료',
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 28,
                          fontWeight: FontWeight.w700,
                        ),
                      )
                    ],
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
