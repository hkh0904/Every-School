import { useEffect, useState } from 'react';
import styles from './ConsultRejectCard.module.css';

import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import ConsultDetailModal from './ConsultDetailModal';

export default function ConsultRejectCard({ rejectList }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [consultId, setConsultId] = useState();
  const [csltList, setCsltList] = useState([
    {
      consultId: 2,
      status: '상담 불가',
      type: '방문상담',
      studentInfo: '10301 하예솔 학생',
      parentInfo: '박연진 어머님',
      consultDate: [2023, 11, 4, 15, 0],
      rejectedReason:
        '수업 시간을 인해서 상담 하지 못합니다. 제 공지 사항을 읽고 신청해주세요 가자다라마바사아자차타카파차타카파'
    },
    {
      consultId: 2,
      status: '상담 불가',
      type: '방문상담',
      studentInfo: '10301 하예솔 학생',
      parentInfo: '박연진 어머님',
      consultDate: [2023, 11, 4, 15, 0],
      rejectedReason: '수업 시간을 인해서 상담 하지 못합니다. 제 공'
    }
  ]);

  const [groupCslt, setGroupCslt] = useState([]);

  function groupByYearMonth(list) {
    const grouped = {};
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
    const groupedByYearMonth = groupByYearMonth(csltList);
    setGroupCslt(groupedByYearMonth);
  }, []);

  return (
    <>
      {rejectList.length === 0 ? (
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

                              <p className={styles.reasonBoxP3}>거절 사유</p>
                              <p className={styles.reasonBoxP4}>{item?.rejectedReason}</p>
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
