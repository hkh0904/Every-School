import { useMemo } from "react";
import Table from "../../component/Table/Table";
import styles from "./ReportHistoryPage.module.css";
import SvgIcon from "@mui/material/SvgIcon";
import AddCircleIcon from '@mui/icons-material/AddCircle';

export default function ReportHistoryPage() {
  const columns = useMemo(
    () => [
      {
        accessor: "number",
        Header: "번호",
      },
      {
        accessor: "kind",
        Header: "신고 종류",
      },
      {
        accessor: "time",
        Header: "접수시간",
      },
      {
        accessor: "now",
        Header: "처리내역",
      },
      {
        accessor: "detail",
        Header: "상세내역",
      }
    ],
    []
  );

  const data = [
    {
      number: 1,
      kind: '학교폭력',
      time: '23.10.17 17:00',
      now: true,
      detail: '자세히보기',
    },
    {
      number: 2,
      kind: '학교폭력',
      time: '23.10.15 12:10',
      now: false,
      detail: '자세히보기',
    },
    {
      number: 3,
      kind: '메세지 신고',
      time: '23.10.14 15:40',
      now: true,
      detail: '자세기',
    },
    {
      number: 4,
      kind: '학교폭력',
      time: '23.10.13 12:00',
      now: true,
      detail: 'gh',
    },
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
  )
}