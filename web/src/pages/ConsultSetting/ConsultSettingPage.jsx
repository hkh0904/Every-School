import { useEffect, useState } from 'react';
import styles from './ConsultSettingPage.module.css';
import ConsultTime from './ConsultTime';
import { getConsultingList, getConsultingMessage } from '../../api/ConsultingAPI/consultingAPI';
import { modifyConsultMsg } from '../../api/ConsultingAPI/consultingAPI';

export default function ConsultSettingPage() {
  const [message, setMessage] = useState('');
  const [isCorrect, setIsCorrect] = useState(false);
  const [isTimeSet, setIsTimeSet] = useState(false);

  const [csltData, setCsltData] = useState([]);
  const [csltSchedule, setCsltSchedule] = useState([]);

  const [rejectNum, setRejectNum] = useState();

  const [consultScheduleId, setConsultScheduleId] = useState();

  useEffect(() => {
    const consultingSchedule = async () => {
      try {
        const schedule = await getConsultingMessage();
        console.log(schedule.description);
        if (schedule.description) {
          setMessage(schedule.description);
        } else {
          setMessage('상담 메세지를 설정하세요');
        }
        setConsultScheduleId(schedule.consultScheduleId);
      } catch (error) {}
    };

    const fetchData = async () => {
      try {
        const newCsltData = await getConsultingList();
        const formattedCsltData = newCsltData.map((cslt) => ({
          ...cslt,
          consultDate: [
            new Date(cslt.consultDate).getFullYear(),
            new Date(cslt.consultDate).getMonth() + 1,
            new Date(cslt.consultDate).getDate(),
            new Date(cslt.consultDate).getHours()
          ]
        }));
        setCsltData(formattedCsltData);
      } catch (error) {
        console.error(error);
      }
    };
    consultingSchedule();
    fetchData();
  }, []);

  return (
    <div className={styles.ConsultApprove}>
      <div className={styles.title}>
        <p>상담 설정</p>
        <div className={styles.csltMessage}>
          <p>상담 설명 메세지 :&nbsp;</p>
          {isCorrect ? (
            <div className={styles.correctMsg}>
              <input type='text' value={message} onChange={(e) => setMessage(e.target.value)} />
              <p
                className={styles.msgBtn}
                onClick={() => {
                  setIsCorrect(false);
                  modifyConsultMsg(consultScheduleId, message);
                }}
              >
                완료
              </p>
            </div>
          ) : (
            <div className={styles.setMsg}>
              <p>{message}</p>
              <p className={styles.msgBtn} onClick={() => setIsCorrect(true)}>
                수정
              </p>
            </div>
          )}
        </div>
        <div className={styles.timeSetBox}>
          <div className={styles.setTime}>
            <p className={styles.timeSetTitle}>상담 가능 시간 설정하기</p>
          </div>
          <ConsultTime setIsTimeSet={setIsTimeSet} consultScheduleId={consultScheduleId} />
        </div>
      </div>
      {csltData.length === 0 && <p className={styles.nocslt}>상담 신청 내역이 존재하지 않습니다.</p>}
    </div>
  );
}
