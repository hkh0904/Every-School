import { useState } from 'react';
import styles from './ConsultCheckList.module.css';
import { useEffect } from 'react';
import RefuseModal from './RefuseModal';

export default function ConsultCheckList({ csltList, setIsModalOpen }) {
  const [groupCslt, setGroupCslt] = useState([]);

  function groupByYearMonth(list) {
    const grouped = {};

    list.forEach((item) => {
      const year = item.date[0];
      const month = item.date[1];

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
                          <p className={styles.childName}>{item.studentName} 학생</p>
                          <p className={styles.parentName}>
                            {item.parentName}
                            {item.parentSex === 'F' ? <span>(모)</span> : <span>(부)</span>}
                          </p>
                        </div>
                        <div className={styles.reasonBox}>
                          <p className={styles.reasonBoxP1}>상담 일자</p>
                          <p className={styles.reasonBoxP2}>
                            {item.date.slice(0, 3).join('.')} {item.date[3]}:00
                          </p>
                          <p className={styles.reasonBoxP3}>신청 사유</p>
                          <p className={styles.reasonBoxP4}>{item.reason}</p>
                        </div>
                      </div>
                      <div className={styles.checkBtn}>
                        <p>승인</p>
                        <p onClick={() => setIsModalOpen(true)}>거절</p>
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
