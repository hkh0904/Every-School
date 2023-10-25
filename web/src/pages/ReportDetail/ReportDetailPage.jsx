import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import styles from './ReportDetailPage.module.css';
import { Row, Col } from 'antd';
import Select from 'react-select';

export default function ReportDetailPage() {
  const location = useLocation();
  const data = location.state.detailData;
  const [selectedValue, setSelectedValue] = useState(data.report_status ? 'true' : 'false');
  let selectOptions = [
    { value: 'false', label: '처리 중' },
    { value: 'true', label: '처리 완료' }
  ];

  const customStyles = {
    container: (provided) => ({
      ...provided,
      width: '100%'
    }),
    singleValue: (provided) => ({
      ...provided,
      textAlign: 'center'
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
      caretColor: 'transparent'
    }),
    menu: (provided) => ({
      ...provided,
      border: 'none',
      boxShadow: 'none'
    })
  };
  return (
    <div className={styles.container}>
      <Row>
        <Col span={6}>
          <div className={styles.headText}>분류</div>
          <div className={styles.whiteBox}>{data.category}</div>
        </Col>
        <Col span={2} />
        <Col span={10}>
          <div className={styles.headText}>신고자</div>
          <div style={{ display: 'flex' }}>
            <div className={styles.whiteBox} style={{ width: '35%',marginRight: '1vw' }}>
              {data.reporter_class}
            </div>
            <div className={styles.whiteBox} style={{ width: '35%' }}>
              {data.reporter}
            </div>
          </div>
        </Col>
        <Col span={6}>
          <div className={styles.headText}>처리 상태</div>
          <div className={styles.whiteBox}>
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
      </Row>
      <Row>
        <Col span={6}>
          <div className={styles.headText}>어디서</div>
          <div className={styles.whiteBox}>{data.report_where}</div>
        </Col>
        <Col span={2} />
        <Col span={10}>
          <div className={styles.headText}>목격 일시</div>
          <div style={{ display: 'flex' }}>
            <div className={styles.whiteBox} style={{ width: '35%', marginRight: '1vw' }}>
              {data.report_when.split('.')[0]}.{data.report_when.split('.')[1]}.{data.report_when.split('.')[2]}
            </div>
            <div className={styles.whiteBox} style={{ width: '35%',}}>
              {data.report_when.split('.')[3]}시경
            </div>
          </div>
        </Col>
        <Col span={6}>
          <div className={styles.headText}>누가</div>
          <div className={styles.whiteBox}>{data.report_who}</div>
        </Col>
        <Col span={24} className={styles.headText}>
          내용
        </Col>
        <Col span={24} className={styles.contentBox}>
          {data.report_content}
        </Col>
      </Row>
    </div>
  );
}
