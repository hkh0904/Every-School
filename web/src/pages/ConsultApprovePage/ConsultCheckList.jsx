import { useState } from 'react';
import styles from './ConsultCheckList.module.css';
import { useEffect } from 'react';
import { approveConsulting } from '../../api/ConsultingAPI/consultingAPI';

export default function ConsultCheckList({ csltList, setIsModalOpen, setRejectNum }) {
  const [groupCslt, setGroupCslt] = useState([]);
  console.log(csltList);

  function groupByYearMonth(list) {
    const grouped = {};

    list.forEach((item) => {
      const year = item.consultDate[0];
      const month = item.consultDate[1];

      if (!grouped[year]) {
        grouped[year] = {};
      }
      if (!grouped[year][month]) {
        grouped[year][month] = [];
      }
      grouped[year][month].push(item);
    });
    return grouped;
  }

  useEffect(() => {
    const groupedByYearMonth = groupByYearMonth(csltList);
    setGroupCslt(groupedByYearMonth);
  }, []);

  return (
    <div className={styles.ConsultCheckList}>
      <div className={styles.checkList}>
        {Object.keys(groupCslt).map((year) => (
          <div key={year}>
            {Object.keys(groupCslt[year]).map((month) => (
              <div key={month} className={styles.listMonth}>
                <p className={styles.yearNmonth}>
                  {year}년 {month}월
                </p>
                <div>
                  {groupCslt[year][month].map((item, index) => (
                    <div key={index} className={styles.csltCard}>
                      <div className={styles.cardInfo}>
                        <div>
                          <img
                            className={styles.cardImage}
                            src={process.env.PUBLIC_URL + '/assets/consult/consultcheck.png'}
                            alt=''
                          />
                          <p className={styles.childName}>
                            {item.parentInfo.split(' ')[0]} {item.parentInfo.split(' ')[1]}{' '}
                            {item.parentInfo.split(' ')[2]}
                          </p>
                          <p className={styles.parentName}>
                            {item.parentInfo.split(' ')[3]}&nbsp;
                            {item.parentInfo.split(' ')[4]}
                          </p>
                        </div>
                        <div className={styles.reasonBox}>
                          <p className={styles.reasonBoxP1}>상담 종류</p>
                          <p className={styles.reasonBoxP2}>{item.type}</p>
                          <p className={styles.reasonBoxP1}>상담 일자</p>
                          <p className={styles.reasonBoxP2}>
                            {item.consultDate.slice(0, 3).join('.')} {item.consultDate[3]}:00
                          </p>
                          <p className={styles.reasonBoxP3}>신청 사유</p>
                          <p className={styles.reasonBoxP4}>아직 신청사유를 못받았어요</p>
                        </div>
                      </div>
                      <div className={styles.checkBtn}>
                        <p
                          onClick={() => {
                            approveConsulting(item.consultId);
                          }}
                        >
                          승인
                        </p>
                        <p
                          onClick={() => {
                            setIsModalOpen(true);
                            setRejectNum(item.consultId);
                          }}
                        >
                          거절
                        </p>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
}
