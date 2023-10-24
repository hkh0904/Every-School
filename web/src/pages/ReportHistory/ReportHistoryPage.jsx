import { useMemo } from 'react';
import Table from '../../component/Table/Table';
import styles from './ReportHistoryPage.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import AddCircleIcon from '@mui/icons-material/AddCircle';

export default function ReportHistoryPage() {
  const columns = useMemo(
    () => [
      {
        accessor: 'number',
        Header: '번호'
      },
      {
        accessor: 'category',
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
      number: 1,
      category: '학교폭력',
      time: '23.10.17 17:00',
      status: true,
      detail: {
        category: '학교 폭력',
        reporter_class: '3학년 1반',
        reporter: '미카엘',
        report_status: true,
        report_where: '학교 비품실',
        report_when: '2020.03.03.12',
        report_who: '오연주',
        report_content:
          '오연주 학생이 김휘낭 학생에게 휘낭시에를 강탈하여 이마들렌 학우에게 주었습니다. 김휘낭은 정체성을 이마들렌에게 빼았기게 되었고... 삶을 잃었습니다....'
      }
    },
    {
      number: 2,
      category: '학교폭력',
      time: '23.10.15 12:10',
      status: false,
      detail: {
        category: '학교 폭력',
        reporter_class: '3학년 1반',
        reporter: '미카엘',
        report_status: false,
        report_where: '학교 비품실',
        report_when: '2020.03.03.12',
        report_who: '오연주',
        report_content:
          '오연주 학생이 김휘낭 학생에게 휘낭시에를 강탈하여 이마들렌 학우에게 주었습니다. 김휘낭은 정체성을 이마들렌에게 빼았기게 되었고... 삶을 잃었습니다....'
      }
    },
    {
      number: 3,
      category: '메세지 신고',
      time: '23.10.14 15:40',
      status: true,
      detail: {
        category: '학교 폭력',
        reporter_class: '3학년 1반',
        reporter: '미카엘',
        report_status: true,
        report_where: '학교 비품실',
        report_when: '2020.03.03.12',
        report_who: '오연주',
        report_content:
          '오연주 학생이 김휘낭 학생에게 휘낭시에를 강탈하여 이마들렌 학우에게 주었습니다. 김휘낭은 정체성을 이마들렌에게 빼았기게 되었고... 삶을 잃었습니다....'
      }
    },
    {
      number: 4,
      category: '학교폭력',
      time: '23.10.13 12:00',
      status: false,
      detail: {
        category: '학교 폭력',
        reporter_class: '3학년 1반',
        reporter: '미카엘',
        report_status: false,
        report_where: '학교 비품실',
        report_when: '2020.03.03.12',
        report_who: '오연주',
        report_content:
          '오연주 학생이 김휘낭 학생에게 휘낭시에를 강탈하여 이마들렌 학우에게 주었습니다.\n 김휘낭은 정체성을 이마들렌에게 빼았기게 되었고... 삶을 잃었습니다....'
      }
    }
  ];

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>접수된 신고</div>
          <div className={styles.underText}>처리 완료 : OO건</div>
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
