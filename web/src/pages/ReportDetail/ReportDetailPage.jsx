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
    }),
    option: (provided, state) => {
      let color;
      if (state.isSelected) {
        color = state.data.value === 'true' ? 'green' : 'red'; // 처리 완료일 때는 초록색, 처리 중일 때는 빨간색
      }

      return {
        ...provided,
        color: color, // 글자색 적용
        backgroundColor: state.isFocused ? 'lightgray' : 'white', // 마우스를 올렸을 때의 배경색 설정, 그 외에는 흰색 배경
        textAlign: 'left'
      };
    }
  };

  return (
    <div className={styles.container}>
      <div>
        <div className={styles.headText}>신고 상세내역</div>
        <hr style={{ marginBottom: '40px' }} />
      </div>
      <Row>
        {/* 왼쪽 박스 */}
        <Col span={11} className={styles.contentBox} style={{ paddingRight: 0 }}>
          <Row>
            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>신고자</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>
                {data.reporter_class} {data.reporter}
              </div>
            </Col>
            <Col span={9} className={[styles.line, styles.category, styles.bottomLine]}>
              <div className={styles.contentText}>분류</div>
            </Col>
            <Col span={12} className={[styles.line, styles.bottomLine]}>
              <div className={styles.contentText}>{data.type}</div>
            </Col>
            <Col span={21} style={{ marginTop: '40px' }} />

            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>누가</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>{data.report_who}</div>
            </Col>
            <Col span={9} className={[styles.line, styles.category]}>
              <div className={styles.contentText}>어디서</div>
            </Col>
            <Col span={12} className={styles.line}>
              <div className={styles.contentText}>{data.report_where}</div>
            </Col>
            <Col span={9} className={[styles.line, styles.category, styles.bottomLine]}>
              <div className={styles.contentText}>목격 일시</div>
            </Col>
            <Col span={12} className={[styles.line, styles.bottomLine]}>
              <div className={styles.contentText}>
                {data.report_when.split('.')[0]}.{data.report_when.split('.')[1]}.{data.report_when.split('.')[2]}{' '}
                {data.report_when.split('.')[3]}시경
              </div>
            </Col>
            <Col span={21} style={{ marginTop: '40px' }} />

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
          </Row>
        </Col>
        <Col span={1} />
        {/* 오른쪽 박스 */}
        <Col span={12} className={styles.contentBox}>
          <Col span={24} className={[styles.line, styles.bottomLine]} style={{ backgroundColor: '#f6f5f9' }}>
            <div className={styles.contentText}>내용</div>
          </Col>
          <Col span={24} className={styles.bottomLine}>
            <div className={[styles.reportBox]}>{data.report_content}</div>
          </Col>
        </Col>
      </Row>
    </div>
  );
}
