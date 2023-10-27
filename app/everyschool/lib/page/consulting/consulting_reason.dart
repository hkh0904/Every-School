import 'package:flutter/material.dart';

class ConsultingReason extends StatefulWidget {
  ConsultingReason({super.key, this.onReasonSelected});
  final onReasonSelected;

  @override
  State<ConsultingReason> createState() => _ConsultingReasonState();
}

class _ConsultingReasonState extends State<ConsultingReason> {
  TextEditingController textarea = TextEditingController();

  String inputText = '';

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.fromLTRB(0, 20, 0, 20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            margin: EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: Text(
              '상담 사유',
              style: TextStyle(fontWeight: FontWeight.w600, fontSize: 18),
            ),
          ),
          TextFormField(
              controller: textarea,
              minLines: 6,
              keyboardType: TextInputType.multiline,
              maxLines: null,
              onChanged: (value) {
                inputText = textarea.text;
                widget.onReasonSelected(textarea.text);
              },
              decoration: InputDecoration(
                enabledBorder: OutlineInputBorder(
                    borderSide: BorderSide(width: 1, color: Color(0xffbababa)),
                    borderRadius: BorderRadius.zero),
                focusedBorder: OutlineInputBorder(
                    borderSide: BorderSide(width: 1, color: Colors.black),
                    borderRadius: BorderRadius.zero),
              ))
        ],
      ),
    );
  }
}
