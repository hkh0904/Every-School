import { useRef } from 'react';
import styles from './RefuseModal.module.css';

export default function RefuseModal({ setIsModalOpen }) {
  const outside = useRef();

  const handleModalClick = (e) => {
    if (e.target === outside.current) {
      setIsModalOpen(false);
    }
  };

  return (
    <div className={styles.modalBg} ref={outside} onClick={handleModalClick}>
      <div className={styles.RefuseModal}>
        <p className={styles.refuseMsg}>거절하시겠습니까?</p>
        <div>
          <input className={styles.refuseInput} type='text' placeholder='사유를 입력해주세요(15자 이내)' />
        </div>
        <div className={styles.modalBtn}>
          <p className={styles.refuseBtn} onClick={() => setIsModalOpen(false)}>
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
