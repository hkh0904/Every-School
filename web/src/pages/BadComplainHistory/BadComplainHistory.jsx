import { useMemo, useEffect, useState } from 'react';
import Table from '../../component/Table/Table';
import styles from './BadComplainHistory.module.css';
import { BadCallComplain, BadChatComplain } from '../../api/UserAPI/reportAPI';

export default function BadComplainHistoryPage() {
  const columns = useMemo(
    () => [
      {
        accessor: 'complainId',
        Header: '민원번호'
      },
      {
        accessor: 'type',
        Header: '민원 수단'
      },
      {
        accessor: 'reportedName',
        Header: '민원인'
      },
      {
        accessor: 'reportedDate',
        Header: '민원 날짜'
      },
      {
        accessor: 'complainDetail',
        Header: '상세내역'
      }
    ],
    []
  );

  const [badComplains, setBadComplains] = useState([]);

  const fetchAllBadComplains = async () => {
    try {
      // 두 API 요청을 동시에 수행
      const [callData, chatData] = await Promise.all([BadCallComplain(), BadChatComplain()]);

      // BadCallComplain 데이터 변환
      const transformedCallData = callData.map((item) => {
        const { userCallId, ...rest } = item;
        return {
          ...rest,
          complainId: userCallId
        };
      });

      // BadChatComplain 데이터 변환
      const transformedChatData = chatData.map((item) => {
        const { chatRoomId, reportId, ...rest } = item;
        return {
          ...rest,
          complainId: `${reportId}-${chatRoomId}`
        };
      });
      console.log(chatData)
      // 두 데이터 배열을 병합
      const combinedData = [...transformedCallData, ...transformedChatData];

      // 병합된 데이터로 상태 업데이트
      setBadComplains(combinedData);
    } catch (error) {
      console.error('Error fetching complains data:', error);
    }
  };

  useEffect(() => {
    fetchAllBadComplains();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>신고된 악성 민원</div>
          <div className={styles.underText}></div>
        </div>
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
