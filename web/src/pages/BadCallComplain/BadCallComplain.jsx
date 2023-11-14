import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import styles from './BadCallComplain.module.css';
import { CallComplainDetail } from '../../api/UserAPI/reportAPI';

export default function BadCallComplain() {
  const location = useLocation();
  const { complainId } = location.state || {};
  const [complains, setComplains] = useState([]);

  const fetchComplainDetail = async () => {
    let rawData = await CallComplainDetail(complainId);
    setComplains(rawData);
  };

  useEffect(() => {
    fetchComplainDetail();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.headText}>신고된 악성 민원</div>
      <div className={styles.underText}>악성 민원 번호 : {complainId}</div>
      <hr />
      <div className={styles.tableBox}>
        {complains && complains.details && complains.details.length > 0 ? (
          <div>
            <div className={styles.overallBox}>
              <div>전체 감정 결과 : {complains.overallSentiment}</div>
              <div>neutral : {complains.overallNeutral}%</div>
            </div>
            <div className={styles.overallBox}>
              <div>positive : {complains.overallPositive}%</div>
              <div>negative : {complains.overallNegative}%</div>
            </div>
            <div className={styles.scrollContainer}>
              {complains.details.map((detail, index) => (
                <ul className={styles.detailsItem} key={index}>
                  <li>{detail.content}</li>
                  <li>{detail.sentiment}</li>
                  <li>neutral: {detail.neutral}</li>
                  <li>positive: {detail.positive}</li>
                  <li>negative: {detail.negative}</li>
                  <li>
                    <a
                      className={styles.downloadBox}
                      href={`http://every-school.com:8000/call-service/v1/calls/download/?fileName=${detail.fileName}`}
                    >
                      음성 파일 들어보기
                    </a>
                  </li>
                </ul>
              ))}
            </div>
          </div>
        ) : (
          <div className={styles.noresultText}>분석 결과가 없습니다...</div>
        )}
      </div>
    </div>
  );
}
