import { useRef, useState } from 'react';
import styles from './RefuseModal.module.css';
import { rejectConsulting } from './../../api/ConsultingAPI/consultingAPI';

export default function RefuseModal({ setIsModalOpen, rejectNum }) {
  const outside = useRef();
  const [reason, setReason] = useState('');

  const handleModalClick = (e) => {
    if (e.target === outside.current) {
      setIsModalOpen(false);
    }
  };

  const handleInputChange = (e) => {
    console.log(e.target.value);
    setReason(e.target.value);
  };

  return (
    <div className={styles.modalBg} ref={outside} onClick={handleModalClick}>
      <div className={styles.RefuseModal}>
        <p className={styles.refuseMsg}>거절하시겠습니까?</p>
        <div>
          <input
            className={styles.refuseInput}
            type='text'
            placeholder='사유를 입력해주세요(15자 이내)'
            value={reason}
            onChange={handleInputChange}
          />
        </div>
        <div className={styles.modalBtn}>
          <p
            className={styles.refuseBtn}
            onClick={() => {
              setIsModalOpen(false);
              rejectConsulting(rejectNum, reason);
            }}
          >
            거절하기
          </p>
          <p className={styles.cancelBtn} onClick={() => setIsModalOpen(false)}>
            취소
          </p>
        </div>
      </div>
    </div>
  );
}
