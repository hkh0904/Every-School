import { useMemo, useState, useEffect } from 'react';
import Table from '../../component/Table/Table';
import styles from './Payment.module.css';
import { getNotices, getPayments } from '../../api/BoardAPI/boardApi';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import { useNavigate } from 'react-router-dom';

export default function Payment() {
  const [boards, setBoards] = useState([]);

  const columns = useMemo(
    () => [
      {
        accessor: 'boardId',
        Header: '번호'
      },
      {
        accessor: 'titlepay',
        Header: '제목'
      },
      {
        accessor: 'content',
        Header: '내용'
      },
      {
        accessor: 'createdate',
        Header: '최종 수정 일시'
      }
    ],
    []
  );

  useEffect(() => {
    fetchBoards();
  }, []);

  const fetchBoards = async () => {
    try {
      const data = await getPayments();
      console.log(data.content);
      if (data && Array.isArray(data.content)) {
        const transformedData = data.content.map((board) => ({
          boardId: board.boardId,
          titlepay: board.title,
          content: board.content,
          createdate: board.createdDate.split('T')[0] + ' ' + board.createdDate.split('T')[1]
          // add other fields if necessary
        }));
        console.log('여기 찍어줘');
        console.log(transformedData);
        setBoards(transformedData);
      } else {
      }
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div className={styles.row}>
        <div className={styles.titleAndBtn}>
          <div className={styles.headText}>학사공지 관리</div>
          <div className={styles.btnAndWrite} onClick={() => navigate('/docs/register-payment/write')}>
            <AddCircleOutlineIcon sx={{ color: 'white' }} />
            <div className={styles.buttonDiv}>작성하기</div>
          </div>
        </div>
      </div>
      <hr />
      <div className={styles.tableBox}>
        <div className={styles.scrollContainer}>
          <Table columns={columns} data={boards} />
        </div>
      </div>
    </div>
  );
}
