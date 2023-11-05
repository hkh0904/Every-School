import { useEffect, useState } from 'react';
import styles from './ConsultCompleteCard.module.css';
import { getCompliteConsulting } from '../../api/ConsultingAPI/consultingAPI.js';

export default function ConsultCompleteCard() {
  const [csltList, setCsltList] = useState([
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2023, 10, 23, 16],
      reason: '자녀의진로상담어쩌고저쩌고'
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2023, 10, 23, 13],
      reason: '자녀의진로상담어쩌고저쩌고'
    },
    {
      studentName: '강가누엘',
      parentName: '이빛나라',
      parentSex: 'F',
      date: [2023, 11, 23, 16],
      reason: '자녀의 진로상담어쩌고저쩌고'
    },
    {
      studentName: '강가누엘',
      parentName: '이빛나라',
      parentSex: 'F',
      date: [2024, 1, 23, 16],
      reason:
        '자녀의진로상담어쩌고저쩌고자녀의자녀의진로상담어쩌고저쩌고자녀의 자녀의진로상담어쩌고저쩌고자녀의 자녀의진로상담어쩌고저쩌고자녀의  진로상담어쩌고저쩌고자녀의 진로상담어쩌고저쩌고자녀의 진로상담어쩌고저쩌고'
    },
    {
      studentName: '강OO',
      parentName: '김OO',
      parentSex: 'F',
      date: [2024, 1, 23, 16, 0],
      reason: '자녀의진로상담어쩌고저쩌고'
    }
  ]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const newCsltData = await getCompliteConsulting();
        console.log(newCsltData);
        setCsltList(newCsltData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

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
    <div className={styles.ConsultCompleteCard}>
      <div className={styles.year}>
        {Object.keys(groupCslt).map((year) => (
          <div key={year}>
            {Object.keys(groupCslt[year]).map((month) => (
              <div key={month} className={styles.listMonth}>
                <p className={styles.yearNmonth}>
                  {year}년 {month}월
                </p>
                <div className={styles.cardContainer}>
                  {groupCslt[year][month].map((item, index) => (
                    <div key={index} className={styles.csltCard}>
                      <div className={styles.cardInfo}>
                        <div>
                          <img
                            className={styles.cardImage}
                            src={`${process.env.PUBLIC_URL}/assets/consult/consult_history.png`}
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
