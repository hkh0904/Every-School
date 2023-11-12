import { useEffect, useState } from 'react';
import styles from './ManageClassPage.module.css';
import ApproveModal from './ApproveModal';
import { getClassAccess, getCpltAccessClassList } from '../../api/SchoolAPI/schoolAPI';

//axios 요청하기
export default function ManageClassPage() {
  const [accessList, setAccessList] = useState([
    // { id: 0, type: 2, grade: 3, calss: 2, number: 16, name: '김휘낭' },
    // { id: 1, type: 2, grade: 2, calss: 1, number: 11, name: '이마들' },
    // { id: 2, type: 2, grade: 1, calss: 3, number: 1, name: '운남길' },
    // { id: 3, type: 2, grade: 1, calss: 1, number: 14, name: '박파운' },
    // { id: 4, type: 1, grade: 1, calss: 1, number: 12, gender: 'F', name: '오연주' },
    // { id: 5, type: 1, grade: 2, calss: 2, number: 16, gender: 'M', name: '임우택' },
    // { id: 6, type: 1, grade: 3, calss: 3, number: 16, gender: 'F', name: '이예리' },
    // { id: 7, type: 1, grade: 1, calss: 3, number: 16, gender: 'M', name: '홍경환' }
  ]);
  const [completeList, setCompleteList] = useState([]);
  //SPA
  const [pageIdx, setPageIdx] = useState(0);
  const [isActive, setIsActive] = useState(true);
  //.모달
  const [isModalOpen, setIsModalOpen] = useState(false);

  // 승인대기 목록 조회
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newAccessData = await getClassAccess();
        console.log(newAccessData);
        setAccessList(newAccessData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);
  // 승인 완료 목록
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newAccessData = await getCpltAccessClassList();
        console.log(newAccessData);
        setCompleteList(newAccessData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  const mode = [
    {
      id: 0,
      component: (
        <>
          {accessList?.content?.length !== 0 ? (
            accessList?.content?.map((data) => {
              return (
                <div className={styles.approveCard}>
                  <img
                    className={styles.preApproveImg}
                    src={`${process.env.PUBLIC_URL}/assets/approve/pre_approve.png`}
                    alt=''
                  />
                  <div className={styles.information}>
                    <div>학생 신청</div>
                    <div>{data?.studentInfo}</div>
                    <div>관계</div>
                    <div>학년 반 이름</div>
                    <div>신청 날짜</div>
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
            })
          ) : (
            <>0</>
          )}
        </>
      )
    },
    {
      id: 1,
      component: (
        <>
          {completeList?.content?.length !== 0 ? (
            completeList?.content?.map((data) => {
              return (
                <div className={styles.approveCard}>
                  <img
                    className={styles.preApproveImg}
                    src={`${process.env.PUBLIC_URL}/assets/approve/pre_approve.png`}
                    alt=''
                  />
                  <div className={styles.information}>
                    {data?.applyType === '학생 신청' ? <div>학생 신청</div> : <div>학부모 신청</div>}
                    <div>
                      {data?.studentInfo.slice(0, 1)}학년 {data?.studentInfo.slice(1, 3)}반{' '}
                      {data?.studentInfo.slice(3, 5)}번 {data?.studentInfo.slice(-3)}
                    </div>

                    {data?.applyType === '학생 신청' ? null : <div>관계 : {data?.applyType.slice(0, 3)} </div>}

                    <div>신청 시간 :</div>
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
            })
          ) : (
            <>0</>
          )}
        </>
      )
    }
  ];
  return (
    <div className={styles.manageClass}>
      <div className={styles.title}>
        <p>학급승인</p>
        <p>승인 대기 중 : {accessList?.count}건 </p>
      </div>
      <div className={styles.approveClass}>
        <div className={styles.approveTab}>
          <div
            onClick={() => {
              setPageIdx(0);
              setIsActive(true);
            }}
          >
            <p style={isActive ? { backgroundColor: 'white' } : null}>
              <b>승인 대기</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(1);
              setIsActive(false);
            }}
          >
            <p style={!isActive ? { backgroundColor: 'white' } : null}>
              <b>승인 내역</b>
            </p>
          </div>
        </div>
        <div className={styles.approveCardBox}>{mode[pageIdx].component}</div>
      </div>
      {isModalOpen ? (
        <>
          <ApproveModal setIsModalOpen={setIsModalOpen} />
        </>
      ) : null}
    </div>
  );
}
