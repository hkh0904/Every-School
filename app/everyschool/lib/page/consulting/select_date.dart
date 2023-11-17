import 'package:flutter/material.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:intl/intl.dart';
import 'package:table_calendar/table_calendar.dart';

class SelectDate extends StatefulWidget {
  SelectDate(
      {Key? key,
      this.selDate,
      this.scheduleTimes,
      this.onDateSelected,
      this.onTimeSelected})
      : super(key: key);

  final selDate;
  final scheduleTimes;
  final onDateSelected;
  final onTimeSelected;

  @override
  State<SelectDate> createState() => _SelectDateState();
}

class _SelectDateState extends State<SelectDate> {
  DateTime? _selectedDay;
  DateTime _focusedDay = DateTime.now().toLocal();
  CalendarFormat _calendarFormat = CalendarFormat.month;
  String? _selectedTime;

  var timelist = [
    '09:00',
    '10:00',
    '11:00',
    '12:00',
    '13:00',
    '14:00',
    '15:00',
    '16:00',
  ];

  @override
  void initState() {
    super.initState();
    initializeDateFormatting('ko_KR', null);
  }

  List<String> getAvailableTimes() {
    if (_selectedDay == null || widget.scheduleTimes == null) {
      return ['날짜를 선택해주세요'];
    }

    var selectedDayOfWeek = DateFormat('EEEE').format(_selectedDay!);
    var daySchedule = widget.scheduleTimes[selectedDayOfWeek.toLowerCase()];

    if (daySchedule?.contains(true) != true) {
      return ['상담 가능 시간이 없습니다'];
    }

    return timelist
        .where(
            (time) => daySchedule[timelist.indexOf(time) % daySchedule.length])
        .toList();
  }

  bool _isWeekend(DateTime day) {
    return day.weekday == DateTime.saturday || day.weekday == DateTime.sunday;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 25, 0, 10),
          child: Text(
            '날짜 선택',
            style: TextStyle(fontWeight: FontWeight.w600, fontSize: 18),
          ),
        ),
        TableCalendar(
          availableGestures: AvailableGestures.horizontalSwipe,
          firstDay: DateTime.now().toLocal(),
          lastDay: DateTime.utc(2999, 12, 31),
          focusedDay: _focusedDay,
          locale: 'ko-KR',
          selectedDayPredicate: (day) {
            return isSameDay(_selectedDay, day);
          },
          onDaySelected: (selectedDay, focusedDay) {
            if (!_isWeekend(selectedDay!)) {
              setState(() {
                _selectedDay = selectedDay;
                _focusedDay = selectedDay;
              });
              String dayOfWeek = DateFormat('EEEE').format(selectedDay);
              print('Selected Day: $_selectedDay');
              print('Selected Day of Week: $dayOfWeek');
              widget.onDateSelected(_selectedDay!);
            }
          },
          calendarFormat: _calendarFormat,
          onFormatChanged: (format) {
            setState(() {
              _calendarFormat = format;
            });
          },
          calendarBuilders: CalendarBuilders(
            defaultBuilder: (context, date, events) {
              if (_isWeekend(date)) {
                return Container(
                  margin: const EdgeInsets.all(6.0),
                  alignment: Alignment.center,
                  child: Text(
                    date.day.toString(),
                    style: TextStyle(color: Colors.grey[400]),
                  ),
                );
              } else {
                return null;
              }
            },
            dowBuilder: (context, day) {
              if (day.weekday == DateTime.sunday) {
                final text = DateFormat.E('ko-KR').format(day);
                return Center(
                  child: Text(
                    text,
                    style: TextStyle(color: Colors.red),
                  ),
                );
              } else if (day.weekday == DateTime.saturday) {
                final text = DateFormat.E('ko-KR').format(day);
                return Center(
                  child: Text(
                    text,
                    style: TextStyle(color: Colors.blueAccent),
                  ),
                );
              } else {
                return null;
              }
            },
          ),
          headerStyle:
              HeaderStyle(formatButtonVisible: false, titleCentered: true),
          calendarStyle: CalendarStyle(
              isTodayHighlighted: false,
              selectedDecoration: BoxDecoration(
                  color: Color(0xff8975EC), shape: BoxShape.circle),
              selectedTextStyle:
                  TextStyle(fontWeight: FontWeight.w700, color: Colors.white)),
        ),
        Padding(
          padding: const EdgeInsets.fromLTRB(0, 25, 0, 10),
          child: Text(
            '시간 선택',
            style: TextStyle(fontWeight: FontWeight.w600, fontSize: 18),
          ),
        ),
        Wrap(
          children: (() {
            var availableTimes = getAvailableTimes();
            if (availableTimes.length == 1 &&
                availableTimes[0] == '날짜를 선택해주세요') {
              return [
                Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Center(
                    child: Text(
                      '날짜를 선택해주세요',
                    ),
                  ),
                ),
              ];
            } else if (availableTimes.length == 1 &&
                availableTimes[0] == '상담 가능 시간이 없습니다') {
              return [
                Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Center(
                    child: Text(
                      '상담 가능 시간이 없습니다',
                    ),
                  ),
                ),
              ];
            } else {
              return availableTimes.map((time) {
                bool isSelected = time == _selectedTime;
                return GestureDetector(
                  onTap: () {
                    setState(() {
                      _selectedTime = isSelected ? null : time;
                    });
                    widget.onTimeSelected(_selectedTime!);
                  },
                  child: Container(
                    margin: EdgeInsets.fromLTRB(5, 5, 5, 5),
                    width: (MediaQuery.of(context).size.width - 60) * 0.3,
                    height: 33,
                    decoration: BoxDecoration(
                      border: Border.all(
                        color:
                            isSelected ? Color(0xff8975EC) : Color(0xffBABABA),
                        width: 1,
                      ),
                      borderRadius: BorderRadius.circular(30),
                      color: isSelected ? Color(0xff8975EC) : Colors.grey[50],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Text(
                          time,
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.w600,
                            color: isSelected ? Colors.white : Colors.black,
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              }).toList();
            }
          })(),
        ),
      ],
    );
  }
}
