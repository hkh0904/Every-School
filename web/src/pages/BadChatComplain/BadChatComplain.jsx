import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styles from './BadChatComplain.module.css';
import { ChatComplainDetail } from '../../api/UserAPI/reportAPI';

export default function BadChatComplain() {
  const location = useLocation();
  const { complainId, reportedDate } = location.state || {};
  const [complains, setComplains] = useState([]);

  useEffect(() => {
    fetchComplainDetail();
  }, []);

  const fetchComplainDetail = async () => {
    // console.log(complainId)
    if (complainId) {
      const [reportIdStr, chatIdStr] = complainId.split('-');
      const reportId = parseInt(reportIdStr, 10); // 문자열을 정수로 변환
      const chatId = parseInt(chatIdStr, 10); // 문자열을 정수로 변환

      let rawData = await ChatComplainDetail(reportId, chatId, reportedDate);
      setComplains(rawData);
      console.log(rawData);
    }
  };

  const formatTime = (timeString) => {
    const date = new Date(timeString);
    return date.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' });
  };

  return (
    <div className={styles.container}>
      <div className={styles.headText}>신고된 악성 민원</div>
      <div className={styles.underText}>악성 민원 번호 : {complainId}</div>
      <hr />
      <div className={styles.tableBox}>
        {complains.chatList &&
          complains.chatList.map((complain, index) => (
            <div key={index} className={complain.teacherSend ? styles.chatRow : styles.teacherChatRow}>
              <div className={styles.chatContentWrapper}>
                <div className={styles.chatContent}>{complain.content}</div>
                <div className={styles.chatTime}>{formatTime(complain.sendTime)}</div>
                <div className={styles.chatReason}>{complain.reason}</div>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
}
