import { useEffect, useState } from 'react';
import styles from './ConsultTime.module.css';
import { getConsultingMessage, modifyConsultTime } from '../../api/ConsultingAPI/consultingAPI';

export default function ConsultTime({ setIsTimeSet, consultScheduleId }) {
  const [selectWeekday, setSelectWeekday] = useState([]);
  const [selectTimes, setSelectTimes] = useState([]);

  const [monday, setMonday] = useState([]);
  const [tuesday, setTuesday] = useState([]);
  const [wednesday, setWednesday] = useState([]);
  const [thursday, setThursday] = useState([]);
  const [friday, setFriday] = useState([]);

  const handleWeekdayChange = (e, num) => {
    const isChecked = e.target.checked;
    if (isChecked) {
      setSelectWeekday([...selectWeekday, num]);
    } else {
      setSelectWeekday(selectWeekday.filter((day) => day !== num));
    }
    console.log(selectWeekday);
  };

  const handleTimeChange = (e, selectedTime) => {
    const isChecked = e.target.checked;
    if (isChecked) {
      setSelectTimes([...selectTimes, selectedTime]);
    } else {
      setSelectTimes(selectTimes.filter((time) => time !== selectedTime));
    }
    console.log(selectTimes);
  };

  const convertSelectionToData = () => {
    const result = {
      monday: Array(times.length).fill(false),
      tuesday: Array(times.length).fill(false),
      wednesday: Array(times.length).fill(false),
      thursday: Array(times.length).fill(false),
      friday: Array(times.length).fill(false)
    };

    selectWeekday.forEach((day) => {
      selectTimes.forEach((time) => {
        result[weekday[day - 1].value][times.indexOf(time)] = true;
      });
    });

    console.log(result);

    modifyConsultTime(consultScheduleId, result);

    return result;
  };

  const weekday = [
    { day: '월요일', num: 1, value: 'monday' },
    { day: '화요일', num: 2, value: 'tuesday' },
    { day: '수요일', num: 3, value: 'wednesday' },
    { day: '목요일', num: 4, value: 'thursday' },
    { day: '금요일', num: 5, value: 'friday' }
  ];

  const times = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00'];

  useEffect(() => {
    const consultingSchedule = async () => {
      try {
        const schedule = await getConsultingMessage();
        console.log(schedule.description);

        setMonday(schedule.monday);
        setTuesday(schedule.tuesday);
        setWednesday(schedule.wednesday);
        setThursday(schedule.thursday);
        setFriday(schedule.friday);
      } catch (error) {}
    };
    consultingSchedule();
  }, []);

  return (
    <div className={styles.ConsultTime}>
      <div className={styles.consultTimeMain}>
        <div>
          <p className={styles.selectTitle}>요일 선택하기</p>
        </div>
        <div className={styles.daySelectBox}>
          {weekday.map((day, index) => (
            <div key={index} className={styles.weekInput}>
              <input
                type='checkbox'
                name={day.num}
                id={day.num}
                value={day.num}
                onChange={(e) => handleWeekdayChange(e, day.num)}
              />
              <label htmlFor={day.num}>{day.day}</label>
            </div>
          ))}
        </div>
        <div>
          <p className={styles.selectTitle}>시간 선택하기</p>
        </div>
        <div className={styles.timeSelectBox}>
          {times.map((time, index) => (
            <div key={index} className={styles.timeInput}>
              <input type='checkbox' name={time} id={time} value={time} onChange={(e) => handleTimeChange(e, time)} />
              <label htmlFor={time}>{time}</label>
            </div>
          ))}
        </div>
        <div className={styles.timeBtn}>
          <p
            className={styles.setBtn}
            onClick={() => {
              convertSelectionToData();
              setIsTimeSet(false);
            }}
          >
            설정하기
          </p>
          <p className={styles.cancelBtn} onClick={() => setIsTimeSet(false)}>
            취소
          </p>
        </div>
      </div>
    </div>
  );
}
