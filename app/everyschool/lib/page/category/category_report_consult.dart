import 'package:flutter/material.dart';

class CategoryRepnCslt extends StatefulWidget {
  const CategoryRepnCslt(
      {super.key, this.userNum, this.categoryList, this.titleTxt});
  final userNum;
  final categoryList;
  final titleTxt;

  @override
  State<CategoryRepnCslt> createState() => _CategoryRepnCsltState();
}

class _CategoryRepnCsltState extends State<CategoryRepnCslt> {
  @override
  Widget build(BuildContext context) {
    return Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          widget.titleTxt(),
          SizedBox(
            height: 10,
          ),
          GridView.builder(
            physics: NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              childAspectRatio: 7 / 2,
            ),
            itemCount: widget.categoryList[widget.userNum - 1].length,
            itemBuilder: (BuildContext context, int index) {
              return Container(
                decoration:
                    BoxDecoration(border: Border.all(color: Color(0xffd9d9d9))),
                height: 20,
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        widget.categoryList[widget.userNum - 1][index],
                        style: TextStyle(fontSize: 16),
                      ),
                      Icon(
                        Icons.arrow_forward_ios,
                        size: 16,
                      )
                    ],
                  ),
                ),
              );
            },
          )
        ]);
  }
}
