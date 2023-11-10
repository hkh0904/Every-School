import { useEffect, useState } from 'react';
import styles from './ConsultHistory.module.css';

import ConsultCompleteCard from './ConsultCompleteCard';
import ConsultRejectCard from './ConsultRejectCard';
import { getCompliteConsulting } from '../../api/ConsultingAPI/consultingAPI';

export default function ConsultHistory() {
  const [pageIdx, setPageIdx] = useState(0);

  const [approveList, setApproveList] = useState([]);
  const [compliteLIst, setCompliteLIst] = useState([]);
  const [rejectList, setRejectList] = useState([]);
  const [csltList, setCsltList] = useState([
    {
      consultId: 1,
      status: '승인 완료',
      type: '방문상담',
      studentInfo: '10301 하예솔 학생',
      parentInfo: '하도영 아버님',
      consultDate: [2023, 11, 4, 14, 0],
      rejectedReason: ''
    },
    {
      consultId: 2,
      status: '상담 불가',
      type: '방문상담',
      studentInfo: '10301 하예솔 학생',
      parentInfo: '박연진 어머님',
      consultDate: [2023, 11, 4, 15, 0],
      rejectedReason: '수업시간으로 인해 하지 못합니다. 제 상담시간을 확인해주시고 신청해주세요'
    }
  ]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newCsltData = await getCompliteConsulting();
        console.log(newCsltData);
        setCsltList(newCsltData);

        let accessList = [];
        let compliteList = [];
        let rejectList = [];

        for (const item of csltList) {
          // <-- for...of 사용
          if (item['status'] === '승인 완료') {
            accessList.push(item);
          } else if (item['status'] === '상담 완료') {
            compliteList.push(item);
          } else if (item['status'] === '상담 불가') {
            rejectList.push(item);
          }
        }
        setApproveList(accessList);
        setCompliteLIst(compliteList);
        setRejectList(rejectList);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);
  const pageList = [
    <ConsultCompleteCard recieveList={approveList}></ConsultCompleteCard>,
    <ConsultCompleteCard recieveList={compliteLIst}></ConsultCompleteCard>,
    <ConsultRejectCard rejectList={rejectList}></ConsultRejectCard>
  ];
  const pagereturn = (index) => {
    return pageList[index];
  };
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
            }}
          >
            <p style={0 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>예정된 상담</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(1);
            }}
          >
            <p style={1 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>상담 완료</b>
            </p>
          </div>
          <div
            onClick={() => {
              setPageIdx(2);
            }}
          >
            <p style={2 === pageIdx ? { backgroundColor: 'white' } : null}>
              <b>상담 거절</b>
            </p>
          </div>
        </div>
        <div className={styles.consultCardBox}>{pagereturn(pageIdx)}</div>
      </div>
    </div>
  );
}
