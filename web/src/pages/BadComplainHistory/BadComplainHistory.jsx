import { useMemo, useEffect, useState } from 'react';
import Table from '../../component/Table/Table';
import styles from './BadComplainHistory.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { receivedReportInfo } from '../../api/UserAPI/reportAPI';

export default function BadComplainHistoryPage() {
  const columns = useMemo(
    () => [
      {
        accessor: 'reportId',
        Header: '번호'
      },
      {
        accessor: 'type',
        Header: '신고 종류'
      },
      {
        accessor: 'date',
        Header: '접수시간'
      },
      {
        accessor: 'status',
        Header: '처리내역'
      },
      {
        accessor: 'detail',
        Header: '상세내역'
      }
    ],
    []
  );

  const [reports, setReports] = useState([]);
  const [completeReports, setCompleteReports] = useState(0);

  useEffect(() => {
    const fetchReports = async () => {
      let rawData = await receivedReportInfo();
      
      const formattedData = rawData.map(item => {
        const splitDateTime = item.date.split('T');
        const time = `${parseInt(splitDateTime[1], 10)}시`;
        const formattedDate = `${splitDateTime[0]} / ${time}`;
        
        return { ...item, date: formattedDate };
      });
  
      setReports(formattedData); // 변환된 데이터로 상태를 업데이트합니다.
      setCompleteReports(formattedData.length);
    };
    fetchReports();
  }, []);
  

  console.log(reports);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>접수된 신고</div>
          <div className={styles.underText}>
            처리 필요 : {reports.length - completeReports}건 / 처리 완료 : {completeReports}건
          </div>
        </div>
        <div className={styles.plusButton}>
          <SvgIcon component={AddCircleIcon} inheritViewBox />
          <p>추가</p>
        </div>
      </div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={reports} />
        </div>
      </div>
    </div>
  );
}
