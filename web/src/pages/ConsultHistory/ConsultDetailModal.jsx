import React, { useEffect, useRef, useState } from 'react';
import styles from './ConsultDetailModal.module.css';
import { getConsultDetail, sendCompliteConsult } from '../../api/ConsultingAPI/consultingAPI';

const ConsultDetailModal = ({ setIsModalOpen, consultId }) => {
  const outside = useRef();
  const [detail, setDetail] = useState({
    consultId: 1,
    schoolYear: 2023,
    type: '전화상담',
    status: '상담 완료',
    title: '10301 하예솔 학생 박연진 어머님',
    message: '우리 예솔이가 아나운서 되고 싶다고 하네요.',
    resultContent: '',

    rejectedReason: '',
    consultDateTime: [2023, 10, 12, 14, 0],
    createdDate: [2023, 10, 1, 13, 27]
  });
  const [reason, setReason] = useState('');
  const [message, setMessage] = useState('');
  const handleModalClick = (e) => {
    if (e.target === outside.current) {
      setIsModalOpen(false);
    }
  };

  const handleInputChange = (e) => {
    console.log(e.target.value);
    setReason(e.target.value);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const newCsltData = await getConsultDetail(consultId);
        setDetail({
          consultId: 1,
          schoolYear: 2023,
          type: '전화상담',
          status: '상담 완료',
          title: '10301 하예솔 학생 박연진 어머님',
          message: '우리 예솔이가 아나운서 되고 싶다고 하네요.',
          resultContent: '',
          rejectedReason: 'ㅎㅎㅎㅎ',
          consultDateTime: [2023, 10, 12, 14, 0],
          createdDate: [2023, 10, 1, 13, 27]
        });
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className={styles.modalbackground} ref={outside} onClick={handleModalClick}>
      <div className={styles.RefuseModal}>
        {detail.rejectedReason === '' ? (
          <p className={styles.refuseMsg}>상담 내역</p>
        ) : (
          <p className={styles.refuseMsg}>상담 거절 내역</p>
        )}

        <div className={styles.body}>
          <p>
            <b>상태</b> : {detail.status}
          </p>
          <p>
            <b>상담 종류</b> : {detail.type}
          </p>
          <p>
            <b>상담 신청 시간</b> : {detail.consultDateTime?.slice(0, 3).join('.')}{' '}
            {detail.consultDateTime ? detail.consultDateTime[3] : null}:00
          </p>
          <p>
            <b>신청자</b> : {detail.title}
          </p>
          <p>
            <b>상담 내용</b> : {detail.message}
          </p>
          {detail.resultContent !== '' ? (
            <p>
              <b>상담 처리 내역</b> : {detail.resultContent}
            </p>
          ) : null}
          {detail.rejectedReason !== '' ? (
            <p>
              <b>상담 거절 사유</b> : {detail.rejectedReason}
            </p>
          ) : null}

          {detail.resultContent === '' && detail.rejectedReason === '' ? (
            <div className={styles.bottominputbox}>
              <p className={styles.pTag}>상담 처리 내역</p>
              <textarea type='text' value={message} onChange={(e) => setMessage(e.target.value)} />
              <div className={styles.csltcomplitebutton}>
                <p
                  className={styles.donebutton}
                  onClick={async () => {
                    if (message.length === 0) {
                      alert('내용을 적어주세요');
                    } else {
                      const response = await sendCompliteConsult(detail.consultId, message);
                      if (response.code == 200) {
                        alert('완료 되었습니다.');
                        setIsModalOpen(false);
                        window.location.reload();
                      } else {
                        alert('오류로 인해 저장하지 못했습니다..');
                      }
                    }
                  }}
                >
                  상담 완료
                </p>
                <p
                  className={styles.backbutton}
                  onClick={() => {
                    setIsModalOpen(false);
                  }}
                >
                  취소
                </p>
              </div>
            </div>
          ) : (
            <div className={styles.buttonBox}>
              <p
                onClick={() => {
                  setIsModalOpen(false);
                }}
              >
                확인
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default ConsultDetailModal;
