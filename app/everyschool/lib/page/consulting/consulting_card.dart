import 'Package:flutter/material.dart';
import 'package:everyschool/page/consulting/consulting_card_detail.dart';

class ConsultingCard extends StatefulWidget {
  const ConsultingCard(
      {super.key, this.consultingList, this.state, this.userType});
  final consultingList;
  final state;
  final userType;

  @override
  State<ConsultingCard> createState() => _ConsultingCardState();
}

class _ConsultingCardState extends State<ConsultingCard> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    print(widget.consultingList);
  }

  Color _getColorFromState(String state) {
    switch (state) {
      case 'past':
        return Color(0xffFDCE01);
      case 'upcoming':
        return Color(0xff77B6FF);
      default:
        return Colors.black;
    }
  }

  String formatConsultDateTime(List<dynamic> consultDateTime) {
    DateTime dateTime = DateTime(
      consultDateTime[0],
      consultDateTime[1],
      consultDateTime[2],
      consultDateTime[3],
      consultDateTime[4],
      consultDateTime[5],
      consultDateTime[6] ~/ 1000, // 밀리초 단위 제거
    );

    String formattedDateTime =
        "${dateTime.year}.${dateTime.month}.${dateTime.day} "
        "${dateTime.hour.toString().padLeft(2, '0')}:"
        "${dateTime.minute.toString().padLeft(2, '0')}";

    return formattedDateTime;
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width,
      child: Container(
        margin: EdgeInsets.fromLTRB(30, 0, 30, 0),
        child: Column(
          children: List<dynamic>.from(widget.consultingList).map((item) {
            return GestureDetector(
              onTap: () {
                ConsultingCardDetail(item).cardDetail(context);
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
                              Text(item['info'] as String),
                              Text(
                                formatConsultDateTime(item['consultDateTime']),
                                style: TextStyle(color: Color(0xff999999)),
                              ),
                            ],
                          ),
                          Container(
                            alignment: Alignment.center,
                            margin: EdgeInsets.fromLTRB(0, 0, 20, 0),
                            width: 75,
                            height: 30,
                            decoration: BoxDecoration(
                              color: _getColorFromState(widget.state as String),
                              borderRadius: BorderRadius.circular(8),
                            ),
                            child: Text(
                              '자세히보기',
                              style: TextStyle(
                                color: Colors.grey[50],
                                fontWeight: FontWeight.w600,
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
