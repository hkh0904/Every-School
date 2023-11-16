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

  const formatName = (name) => {
    if (name === 'positive') {
      return '긍정적';
    } else if (name === 'negative') {
      return '부정적';
    } else if (name === 'neutral') {
      return '중립적';
    }
  };

  const formatPercentage = (value) => {
    const rounded = Math.round(value * 10) / 10; // 소수점 둘째 자리에서 반올림
    return rounded === 0 ? 0 : rounded; // 0.0인 경우 0으로 표시
  };

  const sentimentValues = [
    { label: '긍정적', value: formatPercentage(complains.overallPositive) },
    { label: '부정적', value: formatPercentage(complains.overallNegative) },
    { label: '중립적', value: formatPercentage(complains.overallNeutral) }
  ];

  // 값이 큰 순서대로 정렬
  sentimentValues.sort((a, b) => b.value - a.value);

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
          <div className={styles.contentbox}>
            <div className={styles.overallBox}>
              <div className={styles.titleText}>통화 감정 결과 : {formatName(complains.overallSentiment)}</div>
            </div>
            <div className={styles.overallBox}>
              {sentimentValues.map((item) => (
                <div className={styles.subText} key={item.label}>
                  {item.label} : {item.value}%
                </div>
              ))}
            </div>
            <div className={styles.scrollContainer}>
              {complains.details.map((detail, index) => (
                <div key={index}>
                  <div className={styles.detailsItem}>
                    <div className={styles.detailContent}>
                      <div className={styles.commentText}>{detail.content}</div>
                      <div className={styles.emoText}>
                        {formatName(detail.sentiment)} : {formatPercentage(detail[detail.sentiment])}%
                      </div>
                    </div>
                    <div className={styles.downloadContainer}>
                      <a
                        className={styles.downloadBox}
                        href={`http://every-school.com:8000/call-service/v1/calls/download/?fileName=${detail.fileName}`}
                      >
                        음성 파일 다운로드
                      </a>
                    </div>
                  </div>
                  <hr />
                </div>
              ))}
            </div>
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div>
  );
}
