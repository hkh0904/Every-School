import { useState } from 'react';
import styles from './ManageClass.module.css';

//axios 요청하기
export default function ManageClassPage() {
  const dummyData = [
    { id: 0, type: 2, grade: 3, calss: 2, number: 16, name: '김휘낭' },
    { id: 1, type: 2, grade: 2, calss: 1, number: 11, name: '이마들' },
    { id: 2, type: 2, grade: 1, calss: 3, number: 1, name: '운남길' },
    { id: 3, type: 2, grade: 1, calss: 1, number: 14, name: '박파운' },
    { id: 4, type: 1, grade: 1, calss: 1, number: 12, gender: 'F', name: '오연주' },
    { id: 5, type: 1, grade: 2, calss: 2, number: 16, gender: 'M', name: '임우택' },
    { id: 6, type: 1, grade: 3, calss: 3, number: 16, gender: 'F', name: '이예리' },
    { id: 7, type: 1, grade: 1, calss: 3, number: 16, gender: 'M', name: '홍경환' }
  ];
  const [pageIdx, setPageIdx] = useState(0);

  const mode = [
    {
      id: 0,
      component: (
        <>
          {dummyData.map((data) => {
            return <div>{data.name}</div>;
          })}
        </>
      )
    },
    {
      id: 1,
      component: (
        <>
          {dummyData.map((data) => {
            return <div>{data.gender}</div>;
          })}
        </>
      )
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
          <button
            onClick={() => {
              setPageIdx(0);
            }}
          >
            승인 대기
          </button>
          <button
            onClick={() => {
              setPageIdx(1);
            }}
          >
            승인 내역
          </button>
          <div>{mode[pageIdx].component}</div>
        </div>
      </div>
    </div>
  );
}
