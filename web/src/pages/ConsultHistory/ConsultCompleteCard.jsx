import { useEffect, useState } from 'react';
import styles from './ConsultCompleteCard.module.css';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ConsultDetailModal from './ConsultDetailModal';

export default function ConsultCompleteCard({ recieveList }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  console.log(recieveList);
  const [groupCslt, setGroupCslt] = useState([]);
  const [consultId, setConsultId] = useState();

  function groupByYearMonth(list) {
    const grouped = {};
    console.log(`여기 리시ㅡ트 ${list}`);

    if (list.length !== 0) {
      list.forEach((item) => {
        const year = item?.consultDate[0];
        const month = item?.consultDate[1];

        if (!grouped[year]) {
          grouped[year] = {};
        }
        if (!grouped[year][month]) {
          grouped[year][month] = [];
        }
        grouped[year][month].push(item);
      });
    } else {
      const year = '';
      const month = '';

      if (!grouped[year]) {
        grouped[year] = {};
      }
      if (!grouped[year][month]) {
        grouped[year][month] = [];
      }
      grouped[year][month].push('');
    }
    return grouped;
  }

  useEffect(() => {
    const groupedByYearMonth = groupByYearMonth(recieveList);
    setGroupCslt(groupedByYearMonth);
  }, [recieveList]);

  return (
    <>
      {recieveList.length === 0 ? (
        <></>
      ) : (
        <div className={styles.ConsultCompleteCard}>
          <div className={styles.year}>
            {Object.keys(groupCslt)?.map((year) => (
              <div key={year}>
                {Object.keys(groupCslt[year])?.map((month) => (
                  <div key={month} className={styles.listMonth}>
                    <p className={styles.yearNmonth}>
                      {year}년 {month}월
                    </p>
                    <div className={styles.cardContainer}>
                      {groupCslt[year][month]?.map((item, index) => (
                        <div key={index} className={styles.csltCard}>
                          <div className={styles.cardInfo}>
                            <div>
                              <img
                                className={styles.cardImage}
                                src={`${process.env.PUBLIC_URL}/assets/consult/consult_history.png`}
                                alt=''
                              />
                            </div>
                            <div className={styles.reasonBox}>
                              <p className={styles.parentName}>
                                {item?.parentInfo}{' '}
                                <span>
                                  ({item?.studentInfo?.slice(0, 1)}학년{' '}
                                  {item?.studentInfo?.slice(1, 2) === '0'
                                    ? `${item?.studentInfo?.slice(2, 3)}반`
                                    : `${item?.studentInfo?.slice(1, 3)}반`}{' '}
                                  {item?.studentInfo?.slice(3, 4) === '0'
                                    ? `${item?.studentInfo?.slice(4, 5)}번`
                                    : `${item?.studentInfo?.slice(3, 5)}번`}{' '}
                                  {item?.studentInfo?.split(' ')[1]} 학생)
                                </span>
                              </p>
                              <p className={styles.reasonBoxP1}>상담 일자</p>
                              <p className={styles.reasonBoxP2}>
                                {item?.consultDate?.slice(0, 3).join('.')}{' '}
                                {item?.consultDate ? item?.consultDate[3] : null}:00
                              </p>
                            </div>
                            <div
                              className={styles.seeMore}
                              onClick={() => {
                                setIsModalOpen(true);
                                setConsultId(item.consultId);
                              }}
                            >
                              <p>자세히 보기</p>
                              <ArrowForwardIosIcon fontSize='small' />
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            ))}
          </div>
          {isModalOpen ? (
            <div className={styles.refModal}>
              <ConsultDetailModal setIsModalOpen={setIsModalOpen} consultId={consultId} />
            </div>
          ) : null}
        </div>
      )}
    </>
  );
}
