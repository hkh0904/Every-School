import { useState } from 'react';
import styles from './ConsultApprovePage.module.css';

export default function ConsultApprovePage() {
  const [message, setMessage] = useState();

  const csltList = [
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2023, 10, 23, 16],
      reason: '자녀의진로상담어쩌고저쩌고'
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2023, 10, 23, 13],
      reason: '자녀의진로상담어쩌고저쩌고'
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2023, 11, 23, 16],
      reason: '자녀의진로상담어쩌고저쩌고'
    }
  ];

  return (
    <div className={styles.ConsultApprove}>
      <div className={styles.title}>
        <p>상담 확인</p>
        <p>대기 중 : {csltList.length}건</p>
        <p>상담 설명 메세지 : </p>
      </div>
    </div>
  );
}
