import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:intl/intl.dart';
import 'package:everyschool/api/messenger_api.dart';

class MuteTimeSet extends StatefulWidget {
  const MuteTimeSet({Key? key}) : super(key: key);

  @override
  _MuteTimeSetState createState() => _MuteTimeSetState();
}

class _MuteTimeSetState extends State<MuteTimeSet> {
  DateTime? startTime;
  DateTime? endTime;
  bool isMuteEnabled = false;

  TextEditingController startController = TextEditingController();
  TextEditingController endController = TextEditingController();

  @override
  void initState() {
    super.initState();
    getMuteTime(); // 페이지가 로드될 때 한 번 호출
  }

  Future<void> getMuteTime() async {
    var time = await CallingApi().muteTimeInquiry();
    print(time[0]);

    DateTime startTime;
    DateTime endTime;

    if (time[0]['startTime'].runtimeType == String) {
      startTime = DateTime.parse(time[0]['startTime']);
      endTime = DateTime.parse(time[0]['endTime']);
    } else {
      startTime = DateTime(DateTime.now().year, DateTime.now().month, DateTime.now().day, 0, 0);
      endTime = DateTime(DateTime.now().year, DateTime.now().month, DateTime.now().day, 0, 0);
    }

    setState(() {
      this.startTime = startTime;
      this.endTime = endTime;
      isMuteEnabled = time[0]['isActivate'];
      startController.text = _formatTime(startTime);
      endController.text = _formatTime(endTime);
    });
  }

  Future<void> _selectTime(BuildContext context, TextEditingController controller, bool isStart) async {
    DateTime? selectedTime = await showModalBottomSheet(
      context: context,
      builder: (BuildContext builder) {
        return _buildBottomSheet(context, controller, isStart);
      },
    );

    if (selectedTime != null) {
      controller.text = _formatTime(selectedTime);
      if (isStart) {
        setState(() {
          startTime = selectedTime;
        });
      } else {
        setState(() {
          endTime = selectedTime;
        });
      }
    }
  }

  String _formatTime(DateTime time) {
    return DateFormat('hh:mm a').format(time);
  }

  Widget _buildBottomSheet(BuildContext context, TextEditingController controller, bool isStart) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Container(
          height: MediaQuery.of(context).size.height * 0.5,
          child: CupertinoDatePicker(
            mode: CupertinoDatePickerMode.time,
            initialDateTime: DateTime.now(),
            onDateTimeChanged: (DateTime dateTime) {
              controller.text = _formatTime(dateTime);
              if (isStart) {
                startTime = dateTime;
              } else {
                endTime = dateTime;
              }
            },
          ),
        ),
        ElevatedButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: Text('선택 완료'),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(
            '방해금지 설정',
            style: TextStyle(color: Colors.black, fontWeight: FontWeight.w700),
          ),
          leading: BackButton(),
          backgroundColor: Colors.grey[200],
          elevation: 0,
        ),
        body: Container(
          padding: EdgeInsets.fromLTRB(20, 20, 20, 20),
          color: Colors.grey[200],
          child: Column(
            children: [
          Container(
          decoration: BoxDecoration(
          color: Colors.grey[50],
            borderRadius: BorderRadius.circular(8.0),
          ),
          padding: EdgeInsets.fromLTRB(20, 0, 20, 0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text('방해금지', style: TextStyle(fontSize: 16,)),
              Switch(
                value: isMuteEnabled,
                onChanged: (value) {
                  setState(() {
                    isMuteEnabled = value;
                  });
                },
              ),
            ],
          ),
        ),
        SizedBox(
          height: 20,
        ),
        Align(
            alignment: Alignment.centerLeft,
            child: Text('시간 설정하기')),
        SizedBox(
          height: 5,
        ),
        Container(
          padding: EdgeInsets.fromLTRB(20, 10, 20, 10),
          decoration: BoxDecoration(
            color: Colors.grey[50],
            borderRadius: BorderRadius.circular(8.0),
          ),
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text('시작 시간', style: TextStyle(fontSize: 16,)),
                  Container(
                    width: 100,
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: TextFormField(
                      decoration: InputDecoration(
                          border: InputBorder.none,
                          contentPadding: EdgeInsets.zero
                      ),
                      textAlign: TextAlign.center,
                      controller: startController,
                      readOnly: true,
                      onTap: isMuteEnabled ? () => _selectTime(context, startController, true) : null,
                    ),
                  ),
                ],
              ),
              SizedBox(height: 10,),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text('종료 시간', style: TextStyle(fontSize: 16,)),
                  Container(
                    width: 100,
                    decoration: BoxDecoration(
                      color: Colors.grey[200],
                      borderRadius: BorderRadius.circular(8.0),
                    ),
                    child: TextFormField(
                      decoration: InputDecoration(
                          border: InputBorder.none,
                          contentPadding: EdgeInsets.zero
                      ),
                      textAlign: TextAlign.center,
                      controller: endController,
                      readOnly: true,
                      onTap: isMuteEnabled ? () => _selectTime(context, endController, false) : null,
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
        SizedBox(
          height: 20,
        ),
        GestureDetector(
        onTap: () {

        List<int> startTimeList = [
          startTime!.year,
          startTime!.month,
          startTime!.day,
          startTime!.hour,
          startTime!.minute,
          startTime!.second,
          startTime!.millisecond,
        ];

        List<int> endTimeList = [
          endTime!.year,
          endTime!.month,
          endTime!.day,
          endTime!.hour,
          endTime!.minute,
          endTime!.second,
          endTime!.millisecond,
        ];

        print('Selected Start Time: $startTimeList');
        print('Selected End Time: $endTimeList');
        print('Is Mute Enabled: $isMuteEnabled');
        CallingApi().muteTimeSet(startTimeList, endTimeList, isMuteEnabled);

        },
          child: Container(
            width: MediaQuery.of(context).size.width,
            height: 50,
            decoration: BoxDecoration(
              color: Colors.grey[50],
              borderRadius: BorderRadius.circular(8.0),
            ),
            child: Center(
              child: Text(
                '설정 완료',
                style: TextStyle(fontSize: 16, color: Colors.blueAccent),
              ),
            ),
          ),
        ),
            ],
          ),
        ),
    );
  }
}

