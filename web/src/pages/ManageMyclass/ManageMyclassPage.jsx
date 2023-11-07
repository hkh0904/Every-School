import { useMemo, useState, useEffect } from 'react';
import Table from '../../component/Table/Table';
import styles from './ManageMyclassPage.module.css';
import SvgIcon from '@mui/material/SvgIcon';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import { getStudentList } from '../../api/SchoolAPI/schoolAPI';

export default function ManageMyclassPage() {
  const columns = useMemo(
    () => [
      {
        accessor: 'grade',
        Header: '학년'
      },
      {
        accessor: 'class',
        Header: '반'
      },
      {
        accessor: 'number',
        Header: '번호'
      },
      {
        accessor: 'name',
        Header: '이름'
      },
      {
        accessor: 'birth',
        Header: '생년월일'
      }
      // {
      //   accessor: 'tel',
      //   Header: '연락처'
      // },
      // {
      //   accessor: 'parent',
      //   Header: '학부모 연락처'
      // }
    ],
    []
  );

  const [students, setStudents] = useState([]);
  const [totalStudents, setTotalStudents] = useState(0);

  useEffect(() => {
    const fetchStudents = async () => {
      const data = await getStudentList();
      const transformedData = data.content.map((student) => ({
        grade: parseInt(student.studentNumber.toString().substring(0, 1)),
        class: parseInt(student.studentNumber.toString().substring(1, 3)),
        number: parseInt(student.studentNumber.toString().substring(3, 5)),
        name: student.name,
        birth: student.birth
        // tel: student.tel, // 서버 데이터에 포함되어 있다면 주석을 해제하고 추가
        // parent: student.parent // 서버 데이터에 포함되어 있다면 주석을 해제하고 추가
      }));
      setStudents(transformedData);
      setTotalStudents(data.count);
    };

    fetchStudents();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>우리반 관리</div>
          <div className={styles.underText}>총원 : {totalStudents}명</div>
        </div>
        <div className={styles.plusButton}>
          <SvgIcon component={AddCircleIcon} inheritViewBox />
          <p>추가</p>
        </div>
      </div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={students} />
        </div>
      </div>
    </div>
  );
}
