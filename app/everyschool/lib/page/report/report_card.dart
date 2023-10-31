import 'package:everyschool/page/report/report_detail.dart';
import 'package:flutter/material.dart';

class ReportCard extends StatefulWidget {
  const ReportCard({super.key, this.state, this.reportingList});
  final state;
  final reportingList;

  @override
  State<ReportCard> createState() => _ReportCardState();
}

class _ReportCardState extends State<ReportCard> {
  Color _getColorFromState(String state) {
    switch (state) {
      case 'past':
        return Color(0xff1FAD50);
      case 'waiting':
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

  @override
  Widget build(BuildContext context) {
    return SizedBox(
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
                        builder: (context) => const ReportDetail()));
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
                        color: _getColorFromState(widget.state as String),
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
                              if (item['name'] != null)
                                Text(item['name'] as String),
                              Text(
                                item['dateTime'] as String,
                                style: TextStyle(color: Color(0xff999999)),
                              ),
                            ],
                          ),
                          Container(
                            alignment: Alignment.center,
                            margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                            width: 65,
                            height: 30,
                            decoration: BoxDecoration(
                              color: _getColorFromState(widget.state as String),
                              borderRadius: BorderRadius.circular(8),
                            ),
                            child: Text(
                              _getStateName(widget.state),
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
