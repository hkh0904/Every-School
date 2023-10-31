import 'package:flutter/material.dart';

class ReportDetail extends StatefulWidget {
  const ReportDetail({super.key});

  @override
  State<ReportDetail> createState() => _ReportDetailState();
}

class _ReportDetailState extends State<ReportDetail> {
  var reportDetail = {
    'classification': '악성민원',
    'state': '접수중',
    'date': '2023.10.12',
    'time': '14:00',
    'place': '학교 후문',
    'suspect': '김OO',
    'detail':
        '충 이런 이유입니다.그래서  저쩌고입니다. 대충 이니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.충 이런 이유입니다.그래서 신고를 하고 어쩌고 저쩌고입니다. 대충 이런 이유입니다.',
    'file': []
  };

  TextStyle labelStyle = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.w700,
  );

  Color _getColorFromState(String state) {
    switch (state) {
      case '처리 완료':
        return Color(0xff1FAD50);
      case '접수중':
        return Color(0xffFE0000);
      default:
        return Colors.black;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.grey[50],
          elevation: 0,
          leading: BackButton(color: Colors.black),
          title: Text(
            '신고 상세내역',
            style: TextStyle(color: Colors.black, fontWeight: FontWeight.w700),
          ),
          centerTitle: true,
        ),
        body: SingleChildScrollView(
          child: Container(
            margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
            child:
                Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
              _buildDetailItem('분류', reportDetail['classification'] as String),
              _buildDetailItem('일시', reportDetail['date'] as String),
              _buildDetailItem('시간', reportDetail['time'] as String),
              _buildDetailItem('장소', reportDetail['place'] as String),
              _buildDetailItem('주체', reportDetail['suspect'] as String),
              _buildDetailItem('상세 내용', reportDetail['detail'] as String),
            ]),
          ),
        ),
        bottomNavigationBar: Container(
          height: 60,
          color: _getColorFromState(reportDetail['state'] as String),
          child: Center(
            child: Text(reportDetail['state'] as String,
                style: TextStyle(
                    color: Colors.white,
                    fontSize: 18,
                    fontWeight: FontWeight.w700)),
          ),
        ));
  }

  Widget _buildDetailItem(String label, String value) {
    bool isDetail = label == '상세 내용';
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          margin: EdgeInsets.fromLTRB(0, 0, 0, 5),
          child: Text(
            label,
            style: labelStyle,
          ),
        ),
        Container(
          padding: EdgeInsets.fromLTRB(10, 10, 10, 10),
          width: MediaQuery.of(context).size.width,
          decoration: BoxDecoration(
            border: Border.all(color: Color(0xffbababa)),
          ),
          constraints: BoxConstraints(
            minHeight: isDetail ? 100 : 0,
          ),
          child: Text(
            textAlign: TextAlign.justify,
            value,
            style: TextStyle(
              fontSize: 16,
              height: isDetail ? 1.4 : 0,
            ),
          ),
        ),
        SizedBox(height: 15),
      ],
    );
  }
}
