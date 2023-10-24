import { useState } from 'react';
import styles from './ConsultTime.module.css';

export default function ConsultTime({ setIsTimeSet }) {
  const [selectWeekday, setSelectWeekday] = useState([]);
  const [selectTimes, setSelectTimes] = useState([]);

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

  const weekday = [
    { day: '월요일', num: 1 },
    { day: '화요일', num: 2 },
    { day: '수요일', num: 3 },
    { day: '목요일', num: 4 },
    { day: '금요일', num: 5 }
  ];

  const times = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00'];

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
          <p className={styles.setBtn} onClick={() => setIsTimeSet(false)}>
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
