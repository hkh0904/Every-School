import 'package:everyschool/page/report/report_detail.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class ReportCard extends StatefulWidget {
  const ReportCard({super.key, this.state, this.reportingList});
  final state;
  final reportingList;

  @override
  State<ReportCard> createState() => _ReportCardState();
}

class _ReportCardState extends State<ReportCard> {
  Color _getColorFromState(String state) {
    print(state);
    switch (state) {
      case '처리 완료':
        return Color(0xff1FAD50);
      case '접수 완료':
        return Color(0xffFE0000);
      default:
        return Colors.black;
    }
  }

  String _getStateName(String state) {
    switch (state) {
      case 'past':
        return '처리 완료';
      case 'waiting':
        return '처리중';
      default:
        return '';
    }
  }

  String formatConsultDateTime(String consultDateTime) {
    DateTime dateTime = DateTime.parse(consultDateTime);

    String formattedDateTime =
        "${dateTime.year}/${dateTime.month}/${dateTime.day} "
        "${dateTime.hour < 12 ? '오전 ' : '오후 '}"
        "${(dateTime.hour % 12 == 0 ? 12 : dateTime.hour % 12)}:"
        "${dateTime.minute.toString().padLeft(2, '0')} ";

    return formattedDateTime;
  }

  @override
  Widget build(BuildContext context) {
    return widget.reportingList.length == 0
        ? Container(height: 50, child: Center(child: Text('등록된 신고가 없습니다.')))
        : SizedBox(
            width: MediaQuery.of(context).size.width,
            child: Container(
              margin: EdgeInsets.fromLTRB(30, 0, 30, 0),
              child: Column(
                children: List<dynamic>.from(widget.reportingList).map((item) {
                  return GestureDetector(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => ReportDetail(item: item)));
                    },
                    child: Container(
                      height: 100,
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 25),
                      decoration: BoxDecoration(
                          color: Colors.grey[50],
                          borderRadius: BorderRadius.circular(8)),
                      child: Row(
                        children: [
                          Container(
                            margin: EdgeInsets.fromLTRB(0, 0, 15, 0),
                            width: 5,
                            decoration: BoxDecoration(
                              color: _getColorFromState(item['status']),
                              borderRadius: BorderRadius.only(
                                bottomLeft: Radius.circular(8),
                                topLeft: Radius.circular(8),
                              ),
                            ),
                          ),
                          Expanded(
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    Text(
                                      item['type'] as String,
                                      style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                    SizedBox(
                                      height: 3,
                                    ),
                                    Text(
                                      formatConsultDateTime(item['date']),
                                      style:
                                          TextStyle(color: Color(0xff999999)),
                                    ),
                                  ],
                                ),
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                                  width: 65,
                                  height: 30,
                                  decoration: BoxDecoration(
                                    color: _getColorFromState(item['status']),
                                    borderRadius: BorderRadius.circular(8),
                                  ),
                                  child: Text(
                                    item['status'],
                                    style: TextStyle(
                                      color: Colors.grey[50],
                                      fontWeight: FontWeight.w700,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                }).toList(),
              ),
            ),
          );
  }
}
