import 'package:flutter/material.dart';

class CallDetail extends StatefulWidget {
  const CallDetail({super.key, this.callInfo, this.detail});
  final callInfo;
  final detail;

  @override
  State<CallDetail> createState() => _CallDetailState();
}

class _CallDetailState extends State<CallDetail> {
  int _visibleContentCount = 15; // 초기에 보여줄 텍스트 개수

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print(widget.callInfo);
    print(widget.detail);
    calculateCallTime();
  }

  calculateCallTime() {
    DateTime startDateTime = DateTime.parse(widget.callInfo['startDateTime']);
    DateTime endDateTime = DateTime.parse(widget.callInfo['endDateTime']);

    Duration difference = endDateTime.difference(startDateTime);

    int hours = difference.inHours;
    int minutes = difference.inMinutes % 60;
    int seconds = difference.inSeconds % 60;

    String elapsedTime = "";

    if (hours > 0) {
      elapsedTime += "$hours 시간 ";
    }

    if (minutes > 0 || hours > 0) {
      elapsedTime += "$minutes 분 ";
    }

    elapsedTime += "$seconds 초";

    return "통화시간 : $elapsedTime";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.grey[50],
        title: Text('통화내역 상세보기', style: TextStyle(color: Colors.black)),
        elevation: 0,
      ),
      body: Padding(
        padding: const EdgeInsets.fromLTRB(30, 10, 30, 20),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (widget.detail['overallSentiment'] != 'positive')
              Container(
                width: MediaQuery.of(context).size.width,
                decoration: BoxDecoration(
                    color: Color(0xffF4F6FD),
                    borderRadius: BorderRadius.circular(10)),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(10, 15, 5, 15),
                      child: Text('❗ '),
                    ),
                    Expanded(child: Text('해당 전화는 AI 판단 하에 악성민원으로 분류되었습니다.')),
                  ],
                ),
              ),
            if (widget.detail['overallSentiment'] == 'positive')
              Container(
                  width: MediaQuery.of(context).size.width,
                  decoration: BoxDecoration(
                      color: Color(0xffF4F6FD),
                      borderRadius: BorderRadius.circular(10)),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(10, 10, 10, 10),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text('💬 '),
                        Expanded(
                            child: Text('전체 전화 내용을 텍스트로 제공합니다. 하단에서 확인하세요.')),
                      ],
                    ),
                  )),
            SizedBox(
              height: 25,
            ),
            Text(
              '통화정보',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
            ),
            SizedBox(
              height: 10,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    children: [
                      Text('발신자 : ${widget.callInfo['senderName']}'),
                      Text('수신자 : ${widget.callInfo['receiverName']}'),
                    ],
                  ),
                  Text(calculateCallTime()),
                ],
              ),
            ),
            SizedBox(
              height: 25,
            ),
            Text(
              '통화내용',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
            ),
            SizedBox(
              height: 10,
            ),
            if (widget.detail['overallSentiment'] != 'positive')
              Container(
                width: MediaQuery.of(context).size.width,
                decoration: BoxDecoration(
                    color: Color(0xffF4F6FD),
                    borderRadius: BorderRadius.circular(10)),
                child: Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Text('해당 전화는 AI 판단 하에 악성민원으로 분류되었습니다.'),
                ),
              ),
            SizedBox(
              height: 25,
            ),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children:
                  widget.detail['details'].asMap().entries.map<Widget>((entry) {
                int index = entry.key;
                var detail = entry.value;
                return Padding(
                  padding: const EdgeInsets.only(bottom: 8),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        margin: const EdgeInsets.fromLTRB(0, 0, 0, 10),
                        padding: EdgeInsets.fromLTRB(15, 10, 15, 10),
                        decoration: BoxDecoration(
                            border: (detail['sentiment'] == 'negative' &&
                                    detail['negative'] > 80)
                                ? Border.all(color: Colors.red)
                                : Border.all(),
                            color: (detail['sentiment'] == 'negative' &&
                                    detail['negative'] > 80)
                                ? Colors.red[400]
                                : Color(0xff15075f),
                            borderRadius: BorderRadius.only(
                                bottomRight: Radius.circular(8),
                                topLeft: Radius.circular(8),
                                topRight: Radius.circular(8))),
                        child: Text(
                          detail['content'],
                          style: TextStyle(fontSize: 15, color: Colors.white),
                        ),
                      ),
                    ],
                  ),
                );
              }).toList(),
            ),
            SizedBox(
              height: 25,
            ),
            // 더보기 버튼
            if (_visibleContentCount < widget.detail['details'].length)
              Center(
                child: TextButton(
                  onPressed: () {
                    setState(() {
                      _visibleContentCount += 15;
                      if (_visibleContentCount >=
                          widget.detail['details'].length) {
                        _visibleContentCount = widget.detail['details'].length;
                      }
                    });
                  },
                  child: Container(
                    decoration: BoxDecoration(border: Border.all()),
                    child: Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Text(
                        '더보기',
                        style: TextStyle(color: Colors.black),
                      ),
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
