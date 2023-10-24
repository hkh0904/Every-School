import { useState } from 'react';
import styles from './ConsultApprovePage.module.css';
import ConsultCheckList from './ConsultCheckList';
import RefuseModal from './RefuseModal';
import ConsultTime from './ConsultTime';

export default function ConsultApprovePage() {
  const [message, setMessage] = useState('상담 메세지를 설정하세요');
  const [isCorrect, setIsCorrect] = useState(false);
  const [isTimeSet, setIsTimeSet] = useState(false);

  const [isModalOpen, setIsModalOpen] = useState(false);

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
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2024, 1, 23, 16],
      reason: '자녀의진로상담어쩌고저쩌고'
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2024, 1, 23, 16],
      reason: '자녀의진로상담어쩌고저쩌고'
    }
  ];

  return (
    <div className={styles.ConsultApprove}>
      <div className={styles.title}>
        <p>상담 확인</p>
        <p>대기 중 : {csltList.length}건</p>
        <div className={styles.csltMessage}>
          <p>상담 설명 메세지 :&nbsp;</p>
          {isCorrect ? (
            <div className={styles.correctMsg}>
              <input type='text' value={message} onChange={(e) => setMessage(e.target.value)} />
              <p className={styles.msgBtn} onClick={() => setIsCorrect(false)}>
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
            <p className={styles.msgBtn} onClick={() => setIsTimeSet(true)}>
              설정
            </p>
          </div>
          {isTimeSet ? <ConsultTime /> : null}
        </div>
      </div>
      <ConsultCheckList csltList={csltList} setIsModalOpen={setIsModalOpen} />
      {isModalOpen ? (
        <div className={styles.refModal}>
          <RefuseModal setIsModalOpen={setIsModalOpen} />
        </div>
      ) : null}
    </div>
  );
}
