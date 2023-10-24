import { useState } from 'react';
import styles from './ManageClass.module.css';
import ApproveModal from './ApproveModal';

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
  //SPA
  const [pageIdx, setPageIdx] = useState(0);
  const [isActive, setIsActive] = useState(false);
  //.모달
  const [isModalOpen, setIsModalOpen] = useState(false);

  const mode = [
    {
      id: 0,
      component: (
        <>
          {dummyData.map((data) => {
            return (
              <div className={styles.approveCard}>
                <img
                  className={styles.preApproveImg}
                  src={`${process.env.PUBLIC_URL}/assets/approve/pre_approve.png`}
                  alt=''
                />
                <div className={styles.information}>
                  <div>학부모 신청</div>
                  <div>{data.name}</div>
                  <div>관계</div>
                  <div>학년 반 이름</div>
                  <div>신청 날짜</div>
                </div>
                <div className={styles.confirm}>확인하기 {'>'}</div>
              </div>
            );
          })}
        </>
      )
    },
    {
      id: 1,
      component: (
        <>
          {dummyData.map((data) => {
            return (
              <div className={styles.approveCard}>
                <img
                  className={styles.preApproveImg}
                  src={`${process.env.PUBLIC_URL}/assets/approve/pre_approve.png`}
                  alt=''
                />
                <div className={styles.information}>
                  <div>학부모 신청</div>
                  <div>{data.name}</div>
                  <div>관계</div>
                  <div>학년 반 이름</div>
                  <div>승인 날짜</div>
                </div>
                <div
                  className={styles.confirm}
                  onClick={() => {
                    setIsModalOpen(true);
                  }}
                >
                  확인하기 {'>'}
                </div>
              </div>
            );
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
          <div
            onClick={() => {
              setPageIdx(0);
              setIsActive(true);
            }}
          >
            <p style={isActive ? { backgroundColor: 'white' } : null}>승인 대기</p>
          </div>
          <div
            onClick={() => {
              setPageIdx(1);
              setIsActive(false);
            }}
          >
            <p style={!isActive ? { backgroundColor: 'white' } : null}>상담 내역</p>
          </div>
        </div>
        <div className={styles.approveCardBox}>{mode[pageIdx].component}</div>
      </div>
      {isModalOpen ? (
        <div className={styles.refModal}>
          <ApproveModal setIsModalOpen={setIsModalOpen} />
        </div>
      ) : null}
    </div>
  );
}
