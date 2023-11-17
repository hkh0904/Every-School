import styles from './RegisterNotiPage.module.css';
import DetailBox from './DetailBox';
import { useEffect, useState } from 'react';
import EditBtn from '../../component/Button/EditBtn';
import { getNotice } from '../../api/BoardAPI/boardApi';
import { useLocation } from 'react-router-dom';

export default function NotiDetailPage() {
  const location = useLocation();
  const boardId = location.state.boardId;
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [fileName, setFileName] = useState([]);

  useEffect(() => {
    fetchNotiDetail(boardId);
  }, []);

  const fetchNotiDetail = async (boardId) => {
    try {
      const data = await getNotice(boardId);
      if (data) {
        setTitle(data.title);
        setContent(data.content);

        // const transformedData = data.content.map((consult) => ({
        //   type: consult.type,
        //   grade: consult.parentInfo.split(' ')[0].replace('학년', ''),
        //   class: consult.parentInfo.split(' ')[1].replace('반', ''),
        //   number: consult.parentInfo.split(' ')[2].replace('번', ''),
        //   name: consult.parentInfo.split(' ')[3],
        //   relationship: consult.parentInfo.split(' ')[4] === '아버님' ? '부' : '모',
        //   lastModifiedDate: consult.lastModifiedDate.split('T')[0] + ' ' + consult.lastModifiedDate.split('T')[1],
        //   // add other fields if necessary
        // }));
        // setConsults(transformedData);
        // setTotalConsults(data.count);
      } else {
        // handleRetry();
      }
    } catch (error) {
      console.error('Failed to fetch students:', error);
    }
  };

  return (
    <div className={styles.registerNoti}>
      <div className={styles.title}>
        <p>가정통신문</p>
      </div>
      <div className={styles.selectBox}>
        <DetailBox
          setTitle={setTitle}
          setContent={setContent}
          setFileName={setFileName}
          fileName={fileName}
          title={title}
          content={content}
        />
      </div>
      <div className={styles.register}>
        <EditBtn title={title} content={content} fileName={fileName} type='pay' />
      </div>
    </div>
  );
}
