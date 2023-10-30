import 'Package:flutter/material.dart';
import 'package:everyschool/page/consulting/consulting_card_detail.dart';

class ConsultingCard extends StatefulWidget {
  const ConsultingCard({super.key, this.consultingList, this.state});
  final consultingList;
  final state;

  @override
  State<ConsultingCard> createState() => _ConsultingCardState();
}

class _ConsultingCardState extends State<ConsultingCard> {
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
                              Text(item['consultant'] as String),
                              Text(
                                item['dateTime'] as String,
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
