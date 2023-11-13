import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styles from './BadComplainDetail.module.css';
import { ComplainDetail } from '../../api/UserAPI/reportAPI';

export default function BadComplainDetail() {
  const location = useLocation();
  const { userCallId } = location.state || {};
  const [complains, setComplains] = useState([]);

  const fetchComplainDetail = async () => {
    let rawData = await ComplainDetail(userCallId);
    setComplains(rawData);
  };

  useEffect(() => {
    fetchComplainDetail();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.headText}>신고된 악성 민원</div>
      <div className={styles.underText}>악성 민원 번호 : {userCallId}</div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <div>왜 빈데이터.........</div>
        </div>
      </div>
    </div>
  );
}
