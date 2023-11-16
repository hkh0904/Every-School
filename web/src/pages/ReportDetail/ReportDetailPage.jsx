import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import styles from './ReportDetailPage.module.css';
import { Row, Col } from 'antd';
import Select from 'react-select';
import SvgIcon from '@mui/material/SvgIcon';
import Done from '@mui/icons-material/Done';
import { reportDetailInfo, checkReport, completeReport } from '../../api/UserAPI/reportAPI';

export default function ReportDetailPage() {
  const location = useLocation();
  const { reportId } = location.state || {};
  const [data, setData] = useState([]);
  const [selectedValue, setSelectedValue] = useState('');
  const [reportResult, setReportResult] = useState('');
  const selectOptions = [
    { value: 'receive', label: '접수 완료' },
    { value: 'doing', label: '처리중' },
    { value: 'complete', label: '처리 완료' }
  ];
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDetail = async () => {
      const detail = await reportDetailInfo(reportId);
      setData(detail);
      console.log(detail);
      if (detail.status) {
        const value = selectOptions.find((option) => option.label === detail.status)?.value || '';
        setSelectedValue(value);
      }
    };
    fetchDetail();
  }, [reportId]);

  const handleCheckReport = async () => {
    if (selectedValue === 'doing') {
      try {
        await checkReport(reportId);
        navigate('/report/history');
      } catch (error) {
        // 에러 처리 로직
        console.error('Report check failed', error);
      }
    } else if (selectedValue === 'complete' && reportResult !== '') {
      try {
        await completeReport(reportId, reportResult);
        navigate('/report/history');
      } catch (error) {
        console.log('error : ', error);
      }
    }
  };

  // 버튼 스타일을 결정하는 함수
  const getButtonStyle = () => {
    if ((selectedValue === 'receive' || selectedValue === 'complete') && reportResult === '') {
      // reportResult가 비어있고, selectedValue가 'receive'나 'complete'일 경우 회색 스타일
      return { backgroundColor: 'lightgrey' };
    } else {
      // 기본 스타일
      return {};
    }
  };

  const customStyles = {
    container: (provided) => ({
      ...provided,
      width: '90%'
    }),
    singleValue: (provided) => ({
      ...provided,
      textAlign: 'start'
    }),
    placeholder: (provided) => ({
      ...provided,
      textAlign: 'center'
    }),
    control: (provided) => ({
      ...provided,
      border: 'none',
      backgroundColor: 'transparent',
      boxShadow: 'none',
      caretColor: 'transparent',
      display: 'flex',
      alignitems: 'center',
      width: '14vw'
    }),
    menu: (provided) => ({
      ...provided,
      border: 'none',
      boxShadow: 'none'
    })
  };

  function formatWitness(data) {
    if (!data) {
      return '익명';
    }
    const [number, name] = data.split(' ');

    // 각 부분을 추출합니다.
    const part1 = number.substring(0, 1);
    const part2 = parseInt(number.substring(1, 3), 10);
    const part3 = parseInt(number.substring(3), 10);

    return `${part1}학년 ${part2}반 ${part3}번 / ${name}`;
  }

  function formatDateTime(dateTime) {
    if (!dateTime) {
      return '';
    }

    // 공백으로 구분하여 날짜와 시간 부분을 분리합니다.
    const [datePart, timePart] = dateTime.split(' TimeOfDay');

    // 날짜 부분에서 날짜를 추출합니다.
    const date = datePart.split(' ')[0];

    // 시간 부분에서 시간을 추출합니다.
    const timeMatch = timePart.match(/\(([^)]+)\)/);
    if (!timeMatch) {
      return 'Invalid Time Format'; // 또는 적절한 에러 처리
    }

    const time = timeMatch[1];

    return `${date} / ${time}`;
  }

  return (
    <div className={styles.container}>
      <div>
        <div className={styles.row}>
          <div>
            <div className={styles.headText}>신고 상세내역</div>
            <div className={styles.underText}>신고 제목 : {data.description}</div>
          </div>
          <div className={styles.plusButton} onClick={handleCheckReport} style={getButtonStyle()}>
            <SvgIcon component={Done} inheritViewBox />
            <p>처리하기</p>
          </div>
        </div>
        <hr style={{ marginBottom: '50px' }} />
      </div>
      <Row>
        {/* 왼쪽 박스 */}
        <Col span={11} className={styles.contentBox} style={{ paddingRight: 0 }}>
          <Row>
            <Col span={9} className={[styles.line, styles.category, styles.bottomLine]}>
              <div className={styles.contentText}>처리상태</div>
            </Col>
            <Col
              span={12}
              className={[styles.line, styles.bottomLine]}
              style={{ alignItems: 'center', display: 'flex', width: '100%' }}
            >
              <div className={styles.pickerText}>
                <Select
                  styles={customStyles}
                  onChange={(e) => setSelectedValue(e.value)}
                  options={selectOptions}
                  placeholder='유형 선택'
                  value={selectOptions.filter(function (option) {
                    return option.value === selectedValue;
                  })}
                />
              </div>
            </Col>
            <Col span={21} style={{ marginTop: '50px' }} />
            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>신고자</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>{formatWitness(data.witness)}</div>
            </Col>
            <Col span={9} className={[styles.line, styles.category, styles.bottomLine]}>
              <div className={styles.contentText}>분류</div>
            </Col>
            <Col span={12} className={[styles.line, styles.bottomLine]}>
              <div className={styles.contentText}>{data.type}</div>
            </Col>
            <Col span={21} style={{ marginTop: '50px' }} />
            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>누가</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>{data.who}</div>
            </Col>
            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>어디서</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>{data.where}</div>
            </Col>
            <Col span={9} className={[styles.line, styles.category, styles.bottomLine]}>
              <div className={styles.contentText}>목격 일시</div>
            </Col>
            <Col span={12} className={[styles.line, styles.bottomLine]}>
              <div className={styles.contentText}>{formatDateTime(data.when)}</div>
            </Col>
          </Row>
        </Col>
        <Col span={1} />
        {/* 오른쪽 박스 */}
        <Col span={12} className={styles.contentBox}>
          <Col span={24} className={[styles.line, styles.bottomLine]} style={{ backgroundColor: '#f6f5f9' }}>
            <div className={styles.contentText}>내용</div>
          </Col>
          <Col span={24}>
            <div className={[styles.reportBox]} style={{ minHeight: '20vh' }}>
              {data.what}
            </div>
          </Col>
          <Col span={24} className={[styles.line, styles.bottomLine]} style={{ backgroundColor: '#f6f5f9' }}>
            <div className={styles.contentText}>처리 결과</div>
          </Col>
          <Col span={24} className={styles.bottomLine}>
            <div className={[styles.reportBox]} style={{ minHeight: '20vh' }}>
              <textarea
                value={reportResult}
                onChange={(e) => setReportResult(e.target.value)}
                className={styles.resultText}
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  outline: 'none', 
                  width: '100%',
                  height: 'calc(1.5em * 5)', 
                  resize: 'none', 
                  overflowY: 'auto', 
                  fontFamily: 'Pretendard, sans-serif' 
                }}
                placeholder='신고 처리 결과를 입력해주세요'
              />
            </div>
          </Col>
        </Col>
      </Row>
    </div>
  );
}
