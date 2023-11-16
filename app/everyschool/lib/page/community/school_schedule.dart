import 'package:flutter/material.dart';

class SchoolSchedule extends StatefulWidget {
  const SchoolSchedule({Key? key}) : super(key: key);

  @override
  State<SchoolSchedule> createState() => _SchoolScheduleState();
}

class _SchoolScheduleState extends State<SchoolSchedule> {
  var scheduleList = [
    {
      "startDate": "06.01",
      "endDate": "06.01",
      "content": "모의평가",
    },
    {
      "startDate": "06.06",
      "endDate": "06.06",
      "content": "현충일",
    },
    {
      "startDate": "06.07",
      "endDate": "06.07",
      "content": "과학경시대회",
    },
    {
      "startDate": "06.08",
      "endDate": "06.08",
      "content": "수강신청 시작일",
    },
    {
      "startDate": "06.12",
      "endDate": "06.12",
      "content": "수강신청 종료일",
    },
    {
      "startDate": "06.14",
      "endDate": "06.14",
      "content": "수학경시대회",
    },
    {
      "startDate": "06.21",
      "endDate": "06.21",
      "content": "영어어휘경시대회",
    },
    {
      "startDate": "06.23",
      "endDate": "06.23",
      "content": "학생회장선거",
    },
    {
      "startDate": "06.28",
      "endDate": "06.28",
      "content": "교직원회의",
    },
    {
      "startDate": "07.03",
      "endDate": "07.06",
      "content": "2차 지필평가",
    },
    {
      "startDate": "07.11",
      "endDate": "07.17",
      "content": "수업량 유연화 주간",
    },
    {
      "startDate": "07.18",
      "endDate": "07.18",
      "content": "방학 선언일",
    },
    {
      "startDate": "08.09",
      "endDate": "08.09",
      "content": "개학",
    },
    {
      "startDate": "08.15",
      "endDate": "08.15",
      "content": "광복절",
    },
    {
      "startDate": "08.22",
      "endDate": "08.25",
      "content": "3학년 1차 지필평가",
    },
    {
      "startDate": "09.06",
      "endDate": "09.06",
      "content": "모의평가(3학년)",
    },
    {
      "startDate": "09.20",
      "endDate": "09.20",
      "content": "영작문 경시대회",
    },
    {
      "startDate": "09.28",
      "endDate": "09.30",
      "content": "추석 연휴",
    },
    {
      "startDate": "10.03",
      "endDate": "10.03",
      "content": "개천절",
    },
    {
      "startDate": "10.05",
      "endDate": "10.11",
      "content": "지필평가",
    },
    {
      "startDate": "10.19",
      "endDate": "10.19",
      "content": "수업 나눔의날",
    },
    {
      "startDate": "10.25",
      "endDate": "10.25",
      "content": "어휘 경시대회",
    },
    {
      "startDate": "11.01",
      "endDate": "11.01",
      "content": "과학탐구대회",
    },
    {
      "startDate": "11.16",
      "endDate": "11.16",
      "content": "대학수학능력시험일",
    },
    {
      "startDate": "11.24",
      "endDate": "11.24",
      "content": "축제",
    },
    {
      "startDate": "12.08",
      "endDate": "12.08",
      "content": "수능성적통지",
    },
    {
      "startDate": "12.11",
      "endDate": "12.14",
      "content": "지필평가(1, 2학년)",
    },
    {
      "startDate": "12.19",
      "endDate": "12.19",
      "content": "전국연합(1, 2학년)",
    },
    {
      "startDate": "12.21",
      "endDate": "12.21",
      "content": "축제",
    },
    {
      "startDate": "12.28",
      "endDate": "12.28",
      "content": "방학선언일",
    },
    {
      "startDate": "01.12",
      "endDate": "01.12",
      "content": "학교생활기록부 작성 마감일",
    },
    {
      "startDate": "01.15",
      "endDate": "01.17",
      "content": "학생부 점검일",
    },
    {
      "startDate": "01.19",
      "endDate": "01.19",
      "content": "졸업식",
    },
  ];

  String formatScheduleDate(String startDateStr, String endDateStr) {
    // StartDate와 EndDate가 같은 경우, 단순히 해당 날짜 반환
    if (startDateStr == endDateStr) {
      return startDateStr;
    } else {
      // StartDate와 EndDate가 다른 경우, "startDate ~ endDate" 형식으로 반환
      return '$startDateStr ~ $endDateStr';
    }
  }

  DateTime currentDate = DateTime(DateTime.now().year, DateTime.now().month);

  void _decreaseMonth() {
    // 3월인 경우 함수를 빠져나가서 날짜를 감소시키지 않음
    if (currentDate.month == 3) return;

    setState(() {
      if (currentDate.month == 1) {
        // 1월인 경우 전년도 12월로 설정
        currentDate = DateTime(currentDate.year - 1, 12);
      } else {
        // 그 외에는 한 달 감소
        currentDate = DateTime(currentDate.year, currentDate.month - 1);
      }
    });
  }

  void _increaseMonth() {
    // 2월인 경우 함수를 빠져나가서 날짜를 증가시키지 않음
    if (currentDate.month == 2) return;

    setState(() {
      if (currentDate.month == 12) {
        // 12월인 경우 다음년도 1월로 설정
        currentDate = DateTime(currentDate.year + 1, 1);
      } else {
        // 그 외에는 한 달 증가
        currentDate = DateTime(currentDate.year, currentDate.month + 1);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    // 현재 선택된 월에 해당하는 이벤트만 필터링합니다.
    List<Map<String, String>> filteredScheduleList =
        scheduleList.where((event) {
      // null 체크 후 startDate에서 월을 추출합니다.
      int? eventMonth = int.tryParse(event['startDate']?.split('.')[0] ?? '');
      // 현재 선택된 월과 일치하는지 확인합니다.
      return eventMonth == currentDate.month;
    }).toList();

    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context); // 이전 페이지로 돌아가기
        return true;
      },
      child: Scaffold(
          appBar: AppBar(
            backgroundColor: Colors.grey[50],
            elevation: 1,
            centerTitle: true,
            title: Text(
              '학사 일정',
              style:
                  TextStyle(color: Colors.black, fontWeight: FontWeight.w700),
            ),
          ),
          body: Container(
            padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    IconButton(
                      icon: const Icon(Icons.arrow_back_ios),
                      iconSize: 25,
                      onPressed: _decreaseMonth,
                    ),
                    SizedBox(width: 30),
                    Text(
                      '${currentDate.year}년 ${currentDate.month.toString().padLeft(2, '0')}월', // 날짜 형식을 '월'이 항상 두 자리 숫자로 표시되도록 조정
                      style: const TextStyle(
                          fontSize: 20, fontWeight: FontWeight.w800),
                    ),
                    SizedBox(width: 35),
                    IconButton(
                      icon: const Icon(Icons.arrow_forward_ios),
                      iconSize: 25,
                      onPressed: _increaseMonth,
                    ),
                  ],
                ),
                SizedBox(height: 20),
                Expanded(
                  child: ListView.builder(
                    itemCount: filteredScheduleList.length, // 이 부분을 수정합니다.
                    itemBuilder: (context, index) {
                      bool isLastItem = index ==
                          filteredScheduleList.length - 1; // 마지막 요소인지 확인
                      var event = filteredScheduleList[index]; // 이 부분을 추가합니다.

                      return Container(
                        padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
                        decoration: BoxDecoration(
                          border: Border(
                            top: BorderSide(
                              color: Colors.grey.shade400,
                              width: 1.0,
                            ),
                            bottom: isLastItem
                                ? BorderSide(
                                    color: Colors.grey.shade400,
                                    width: 1.0,
                                  )
                                : BorderSide.none,
                          ),
                        ),
                        child: Column(
                          children: [
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Text(
                                  formatScheduleDate(
                                      event['startDate'] as String,
                                      event['endDate'] as String),
                                  style: TextStyle(
                                    fontSize: 16,
                                    color: Colors.grey[600],
                                  ),
                                ),
                                Text(
                                  event['content'] as String,
                                  style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.w600),
                                ),
                              ],
                            ),
                          ],
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
          )),
    );
  }
}
