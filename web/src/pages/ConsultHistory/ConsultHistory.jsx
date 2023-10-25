import { useState } from 'react';
import styles from './ConsultHistory.module.css';

import ConsultCompleteCard from './ConsultCompleteCard';
import ConsultRejectCard from './ConsultRejectCard';

export default function ConsultHistory() {
  const [pageIdx, setPageIdx] = useState(0);
  const [isActive, setIsActive] = useState(false);

  return (
    <div className={styles.consultHistory}>
      <div className={styles.title}>
        <p>상담 내역</p>
      </div>
      <div className={styles.consultClass}>
        <div className={styles.consultTab}>
          <div
            onClick={() => {
              setPageIdx(0);
              setIsActive(true);
            }}
          >
            <p style={isActive ? { backgroundColor: 'white' } : null}>
              <b>상담 완료</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(1);
              setIsActive(false);
            }}
          >
            <p style={!isActive ? { backgroundColor: 'white' } : null}>
              <b>상담 거절</b>
            </p>
          </div>
        </div>
        <div className={styles.consultCardBox}>
          {pageIdx === 0 ? <ConsultCompleteCard></ConsultCompleteCard> : <ConsultRejectCard></ConsultRejectCard>}
        </div>
      </div>
    </div>
  );
}
