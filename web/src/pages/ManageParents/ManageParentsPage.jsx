import { useMemo } from "react";
import Table from "../../component/Table/Table";
import styles from "./ManageParentsPage.module.css";
import SvgIcon from "@mui/material/SvgIcon";
import AddCircleIcon from '@mui/icons-material/AddCircle';

export default function ManageParentsPage() {
  const columns = useMemo(
    () => [
      {
        accessor: "grade",
        Header: "학년",
      },
      {
        accessor: "class",
        Header: "반",
      },
      {
        accessor: "number",
        Header: "번호",
      },
      {
        accessor: "childName",
        Header: "학생 이름",
      },
      {
        accessor: "relation",
        Header: "관계",
      },
      {
        accessor: "name",
        Header: "보호자 이름",
      },
      {
        accessor: "tel",
        Header: "연락처",
      },
      {
        accessor: "etc",
        Header: "비고",
      },
    ],
    []
  );

  const data = [
    {
      grade: 1,
      class: 3,
      number: 1,
      childName: "신성주",
      relation: '모',
      name:"OOO",
      tel: "01012345678",
      etc: null,
    },
    {
      grade: 1,
      class: 3,
      number: 2,
      childName: "임우택",
      relation: '모',
      name:"OOO",
      tel: "01022223333",
      etc: null,
    },
    {
      grade: 1,
      class: 3,
      number: 3,
      childName: "이지혁",
      relation: '부',
      name:"OOO",
      tel: "01033334444",
      etc: null,
    },
    {
      grade: 1,
      class: 3,
      number: 4,
      childName: "홍경환",
      relation: '모',
      name:"OOO",
      tel: "01055556666",
      etc: null,
    }
  ];


  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>학부모 관리</div>
          <div className={styles.underText}>등록 인원 수 : OO명</div>
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