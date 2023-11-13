import { useMemo, useEffect, useState } from 'react';
import Table from '../../component/Table/Table';
import styles from './BadComplainHistory.module.css';
import { BadComplainList } from '../../api/UserAPI/reportAPI';

export default function BadComplainHistoryPage() {
  const columns = useMemo(
    () => [
      {
        accessor: 'userCallId',
        Header: '민원번호'
      },
      {
        accessor: 'senderName',
        Header: '발신자'
      },
      {
        accessor: 'receiverName',
        Header: '수신자'
      },
      {
        accessor: 'startDateTime',
        Header: '통화 시작'
      },
      {
        accessor: 'endDateTime',
        Header: '통화 종료'
      },
      {
        accessor: 'complainDetail',
        Header: '상세내역'
      }
    ],
    []
  );

  const [badComplains, setBadComplains] = useState([]);
  const [completeBadComplains, setCompleteBadComplains] = useState(0);

  function formatDateTime(dateTime) {
    const date = new Date(dateTime);
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours().toString().padStart(2, '0'); 
  const minutes = date.getMinutes().toString().padStart(2, '0');
  
    return `${month}월 ${day}일 ${hours}:${minutes.toString().padStart(2, '0')}`;
  }
  
  const fetchBadComplains = async () => {
    let rawData = await BadComplainList();
  
    // rawData 내의 각 항목에 대해 날짜 형식을 변환합니다.
    const transformedData = rawData.map(item => ({
      ...item,
      startDateTime: formatDateTime(item.startDateTime),
      endDateTime: formatDateTime(item.endDateTime)
    }));
  
    setBadComplains(transformedData); // 변환된 데이터로 상태를 업데이트합니다.
    setCompleteBadComplains(rawData.length);
  };
  

  useEffect(() => {
    fetchBadComplains();
  }, []);

  console.log(badComplains);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>신고된 악성 민원</div>
          <div className={styles.underText}>
            {/* 처리 필요 : {badComplains.length - completeBadComplains}건 / 처리 완료 : {completeBadComplains}건 */}
          </div>
        </div>
        {/* <div className={styles.plusButton}>
          <SvgIcon component={AddCircleIcon} inheritViewBox />
          <p>추가</p>
        </div> */}
      </div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={badComplains} />
        </div>
      </div>
    </div>
  );
}
