import {useMemo, useState, useEffect} from 'react';
import Table from '../../component/Table/Table';
import styles from './NotiHistory.module.css';
import {getNotices} from "../../api/BoardAPI/boardApi";

export default function NotiHistory() {

  const [boards, setBoards] = useState([]);

  const columns = useMemo(
    () => [
      {
        accessor: 'no',
        Header: '번호'
      },
      {
        accessor: 'title',
        Header: '제목'
      },
      {
        accessor: 'writer',
        Header: '작성자'
      },
      {
        accessor: 'lastModifiedDate',
        Header: '최종 수정 일시'
      },
    ],
    []
  );

  useEffect(() => {
    fetchBoards();
  }, []);
  // ds private Long boardId;
  //     private String title;
  //     private String writer;
  //     private LocalDateTime lastModifiedDate;
  const fetchBoards = async () => {
    try {
      const data = await getNotices();
      if (data && Array.isArray(data)) {
        const transformedData = data.map((board) => ({
          no: 0,
          boardId: board.boardId,
          title: board.title,
          writer: board.writer,
          lastModifiedDate: board.lastModifiedDate.split('T')[0] + ' ' + board.lastModifiedDate.split('T')[1],
          // add other fields if necessary
        }));
        console.log(transformedData);
        setBoards(transformedData);
      } else {
      }
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div>
          <div className={styles.headText}>가정통신문 관리</div>
          {/*<div className={styles.underText}>총원 : {totalStudents}명</div>*/}
        </div>
      </div>
      <hr/>
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={boards}/>
        </div>
      </div>
    </div>
  );
}
