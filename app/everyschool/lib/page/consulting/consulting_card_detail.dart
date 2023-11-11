import 'Package:flutter/material.dart';

class ConsultingCardDetail {
  final Map<String, dynamic> cardInfo;
  ConsultingCardDetail(this.cardInfo);
  void cardDetail(BuildContext context) {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
              contentPadding: EdgeInsets.zero,
              shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(5)),
              content: SizedBox(
                width: MediaQuery.of(context).size.width * 0.7,
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0, 20, 0, 10),
                      child: Image.asset(
                        'assets/images/consulting/detail.png',
                        width: 50,
                      ),
                    ),
                    Text(
                      '상담 신청 정보',
                      style:
                          TextStyle(fontWeight: FontWeight.w700, fontSize: 18),
                    ),
                    Container(
                      padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
                      alignment: Alignment.centerLeft,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          Text('상담 종류',
                              style: TextStyle(fontWeight: FontWeight.w600)),
                          Text(cardInfo['type']),
                          SizedBox(
                            height: 10,
                          ),
                          Text('담당자',
                              style: TextStyle(fontWeight: FontWeight.w600)),
                          Text(cardInfo['teacherInfo']),
                          SizedBox(
                            height: 10,
                          ),
                          Text('신청자',
                              style: TextStyle(fontWeight: FontWeight.w600)),
                          Text(cardInfo['parentInfo']),
                          SizedBox(
                            height: 10,
                          ),
                          Text('상담 신청 날짜',
                              style: TextStyle(fontWeight: FontWeight.w600)),
                          Text(cardInfo['consultDateTime']),
                          SizedBox(
                            height: 10,
                          ),
                          Text('상담 사유',
                              style: TextStyle(fontWeight: FontWeight.w600)),
                          Text(cardInfo['message']),
                          SizedBox(
                            height: 10,
                          ),
                          if (cardInfo['rejectReason'] != null)
                            Column(
                              children: [
                                Text('거절 사유',
                                    style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        color: Colors.red)),
                                Text(cardInfo['rejectReason']),
                              ],
                            ),
                          SizedBox(
                            height: 10,
                          ),
                          if (cardInfo['resultContent'] != null)
                            Column(
                              children: [
                                Text('상담 내용',
                                    style:
                                        TextStyle(fontWeight: FontWeight.w600)),
                                Text(cardInfo['resultContent']),
                              ],
                            ),
                        ],
                      ),
                    ),
                    GestureDetector(
                      onTap: () {
                        Navigator.of(context).pop();
                      },
                      child: Container(
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                            color: Color(0xff15075F),
                            border: Border(
                                top: BorderSide(
                                    color: Color(0xffd9d9d9), width: 0.5))),
                        padding: EdgeInsets.fromLTRB(0, 15, 0, 15),
                        child: Text(
                          '확인',
                          style: TextStyle(
                              color: Colors.white, fontWeight: FontWeight.w700),
                        ),
                      ),
                    )
                  ],
                ),
              ));
        });
  }
}
