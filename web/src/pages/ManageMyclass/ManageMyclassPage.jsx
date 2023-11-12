import { useMemo, useState, useEffect } from 'react';
import Table from '../../component/Table/Table';
import styles from './ManageMyclassPage.module.css';
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
  const [retryCount, setRetryCount] = useState(0);
  const maxRetries = 3; // Set a max limit for retries

  const fetchStudents = async () => {
    try {
      const data = await getStudentList();
      if (data && Array.isArray(data.content)) {
        const transformedData = data.content.map((student) => ({
          grade: parseInt(student.studentNumber.toString().substring(0, 1)),
          class: parseInt(student.studentNumber.toString().substring(1, 3)),
          number: parseInt(student.studentNumber.toString().substring(3, 5)),
          name: student.name,
          birth: student.birth
          // add other fields if necessary
        }));
        setStudents(transformedData);
        setTotalStudents(data.count);
      } else {
        handleRetry();
      }
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  const handleRetry = () => {
    if (retryCount < maxRetries) {
      setRetryCount(retryCount + 1);
    } else {
      // You might want to show an error message after max retries
      console.error('Max retries reached. Unable to fetch students.');
    }
  };

  useEffect(() => {
    if (retryCount > 0) {
      // Retry fetching students if retryCount is updated and less than maxRetries
      fetchStudents();
    }
  }, [retryCount]);

  useEffect(() => {
    fetchStudents();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>우리반 관리</div>
          <div className={styles.underText}>총원 : {totalStudents}명</div>
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
