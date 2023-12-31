import { useMemo, useEffect, useState } from 'react';
import Table from '../../component/Table/Table';
import styles from './ReportHistoryPage.module.css';
import { receivedReportInfo } from '../../api/UserAPI/reportAPI';

export default function ReportHistoryPage() {
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

  const fetchReports = async () => {
    let rawData = await receivedReportInfo();

    const filteredData = rawData.filter((item) => item.type !== '악성민원');
    const completeReportsCount = filteredData.reduce((count, item) => {
      return item.status === '처리 완료' ? count + 1 : count;
    }, 0);
    setCompleteReports(completeReportsCount);

    const formattedData = filteredData.map((item) => {
      if (item.type === '학칙위반(흡연, 기물파손 등)') {
        item.type = item.type.replace('(흡연, 기물파손 등)', '');
      }

      const splitDateTime = item.date.split('T');
      const time = `${parseInt(splitDateTime[1], 10)}시`;
      const formattedDate = `${splitDateTime[0]} / ${time}`;

      return { ...item, date: formattedDate };
    });
    formattedData.sort((a, b) => b.reportId - a.reportId);

    setReports(formattedData);
  };

  useEffect(() => {
    fetchReports();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>접수된 신고</div>
          <div className={styles.underText}>
            처리 필요 : {reports.length - completeReports}건 / 처리 완료 : {completeReports}건
          </div>
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
