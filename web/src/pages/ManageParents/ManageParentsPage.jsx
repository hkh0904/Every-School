import { useMemo, useState, useEffect } from 'react';
import Table from '../../component/Table/Table';
import styles from './ManageParentsPage.module.css';
import { getParentList } from '../../api/SchoolAPI/schoolAPI';

export default function ManageParentsPage() {
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
        accessor: 'studentName',
        Header: '학생 이름'
      },
      {
        accessor: 'name',
        Header: '보호자 이름'
      },
      {
        accessor: 'parentType',
        Header: '관계'
      }
    ],
    []
  );

  const [parents, setParents] = useState([]);
  const [totalParents, setTotalParents] = useState(0);

  useEffect(() => {
    const fetchStudents = async () => {
      const data = await getParentList();
      const transformedData = data.content.map((parent) => ({
        grade: parseInt(parent.studentNumber.toString().substring(0, 1)),
        class: parseInt(parent.studentNumber.toString().substring(1, 3)),
        number: parseInt(parent.studentNumber.toString().substring(3, 5)),
        studentName: parent.studentName,
        name: parent.name,
        parentType: parent.parentType === '아버님' ? '부' : '모'
        // tel: student.tel, // 서버 데이터에 포함되어 있다면 주석을 해제하고 추가
        // parent: student.parent // 서버 데이터에 포함되어 있다면 주석을 해제하고 추가
      }));
      setParents(transformedData);
      setTotalParents(data.count);
    };

    fetchStudents();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>학부모 관리</div>
          <div className={styles.underText}>등록 인원 수 : {totalParents}명</div>
        </div>
      </div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={parents} />
        </div>
      </div>
    </div>
  );
}
