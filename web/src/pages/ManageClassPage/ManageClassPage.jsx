import { useState } from 'react';
import styles from './ManageClass.module.css';

//axios 요청하기
export default function ManageClassPage() {
  const [pageIdx, setPageIdx] = useState(0);

  const mode = [
    { id: 0, component: '' },
    {
      id: 1,
      component: ''
    }
  ];
  return (
    <div className={styles.manageClass}>
      <div className={styles.title}>
        <p>학급승인</p>
        <p>승인 대기 중 : {}건 </p>
      </div>
      <div className={styles.approveClass}>
        <div className={styles.approveTab}>
          <button>승인 대기</button>
          <button>승인 내역</button>
        </div>
      </div>
    </div>
  );
}
