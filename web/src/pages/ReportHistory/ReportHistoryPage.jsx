import { useMemo, userState, useEffect, useState } from 'react';
import Table from '../../component/Table/Table';
import styles from './ReportHistoryPage.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import AddCircleIcon from '@mui/icons-material/AddCircle';
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
        accessor: 'time',
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

  const data = [
    {
      reportId: 1,
      type: '학교폭력',
      time: '23.10.17 17:00',
      status: true,
      detail: {
        type: '학교 폭력',
        reporter_class: '3학년 1반',
        reporter: '미카엘',
        report_status: true,
        report_where: '학교 비품실',
        report_when: '2020.03.03.12',
        report_who: '오연주',
        report_content:
          '오연주 학생이 김휘낭 학생에게 휘낭시에를 강탈하여 이마들렌 학우에게 주었습니다. 김휘낭은 정체성을 이마들렌에게 빼았기게 되었고... 삶을 잃었습니다....'
      }
    }
  ];

  const [reports, setReports] = useState([]);
  const [completeReports, setCompleteReports] = useState(0);

  useEffect(() => {
    const fetchReports = async () => {
      const data = await receivedReportInfo();
      setReports(data);
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
          <Table columns={columns} data={data} />
        </div>
      </div>
    </div>
  );
}
