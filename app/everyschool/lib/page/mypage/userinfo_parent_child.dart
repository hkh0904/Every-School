import 'package:flutter/material.dart';

class ParentChildInfo extends StatefulWidget {
  const ParentChildInfo({super.key, this.userInfo});
  final userInfo;
  @override
  State<ParentChildInfo> createState() => _ParentChildInfoState();
}

class _ParentChildInfoState extends State<ParentChildInfo> {
  TextStyle myTextStyle = TextStyle(fontSize: 18, fontWeight: FontWeight.bold);
  TextStyle unabledTextStyle =
      TextStyle(fontSize: 18, color: Color(0xff868E96));
  TextStyle unabledTitleTextStyle = TextStyle(
      fontSize: 18, color: Color(0xff868E96), fontWeight: FontWeight.bold);
  TextStyle enabledTitleTextStyle = TextStyle(fontSize: 18);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          crossAxisAlignment: CrossAxisAlignment.end,
          children: [
            Text('자녀 정보', style: myTextStyle),
          ],
        ),
        SizedBox(
          height: 10,
        ),
        Column(
          children:
              (widget.userInfo['descendants'] as List<dynamic>).map((item) {
            if (item != null && item is Map<String, dynamic>) {
              return Container(
                decoration: BoxDecoration(
                    border: Border.all(width: 0.6, color: Colors.grey),
                    borderRadius: BorderRadius.circular(4)),
                child: ListTile(
                  subtitle: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text('${item['name'] ?? ''}'),
                      Text('${item['school']?['name'] ?? ''}'),
                      Text(
                        '${item['schoolClass']?['grade'] ?? ''}학년 ${item['schoolClass']?['classNum'] ?? ''}반',
                      ),
                    ],
                  ),
                ),
              );
            } else {
              // Handle unexpected or null data
              return Container();
            }
          }).toList(),
        ),
      ],
    );
  }
}
