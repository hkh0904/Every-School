import 'package:everyschool/api/report_api.dart';
import 'package:everyschool/store/user_store.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ReportDetail extends StatefulWidget {
  const ReportDetail({super.key, this.item});
  final item;

  @override
  State<ReportDetail> createState() => _ReportDetailState();
}

class _ReportDetailState extends State<ReportDetail> {
  detailList() async {
    final myInfo = context.read<UserStore>().userInfo;
    final year = context.read<UserStore>().year;
    var response = await ReportApi().getReportDetail(
        year, myInfo['school']['schoolId'], widget.item['reportId']);
    print(response);
    return response;
  }

  TextStyle labelStyle = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.w700,
  );

  Color _getColorFromState(String state) {
    switch (state) {
      case '처리 완료':
        return Color(0xff1FAD50);
      case '접수 완료':
        return Color(0xffFE0000);
      default:
        return Colors.black;
    }
  }

  formatDateTimeString(String dateTimeString) {
    List<String> parts = dateTimeString.split(' ');

    String dateText = parts[0];
    String timeText = parts[2].replaceAll("TimeOfDay(", "").replaceAll(")", "");

    // 날짜를 포맷팅
    DateTime dateTime = DateTime.parse(dateText);
    String formattedDate = "${dateTime.year}-${dateTime.month}-${dateTime.day}";

    // 시간을 포맷팅
    List<String> timeParts = timeText.split(':');
    String hour = timeParts[0];
    String minute = timeParts[1];
    String formattedTime = "$hour시 $minute분경";

    return {'date': formattedDate, 'time': formattedTime};
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
        future: detailList(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if (snapshot.hasData) {
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

            return Scaffold(
                appBar: AppBar(
                  backgroundColor: Colors.grey[50],
                  elevation: 0,
                  leading: BackButton(color: Colors.black),
                  title: Text(
                    '신고 상세내역',
                    style: TextStyle(
                        color: Colors.black, fontWeight: FontWeight.w700),
                  ),
                  centerTitle: true,
                ),
                body: SingleChildScrollView(
                  child: Container(
                    margin: EdgeInsets.fromLTRB(20, 20, 20, 10),
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          _buildDetailItem(
                              '분류', snapshot.data['what'] as String),
                          _buildDetailItem(
                              '일시',
                              formatDateTimeString(
                                  snapshot.data['when'])['date'] as String),
                          _buildDetailItem(
                              '시간',
                              formatDateTimeString(
                                  snapshot.data['when'])['time'] as String),
                          _buildDetailItem(
                              '장소', snapshot.data['where'] as String),
                          _buildDetailItem(
                              '주체', snapshot.data['who'] as String),
                          _buildDetailItem(
                              '상세 내용', snapshot.data['description'] as String),
                        ]),
                  ),
                ),
                bottomNavigationBar: Container(
                  height: 60,
                  color: _getColorFromState(snapshot.data['status'] as String),
                  child: Center(
                    child: Text(snapshot.data['status'] as String,
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: 18,
                            fontWeight: FontWeight.w700)),
                  ),
                ));
          } else if (snapshot.hasError) {
            return Container(
              height: 800,
            );
          } else {
            return Container(
              height: 800,
            );
          }
        });
  }
}
