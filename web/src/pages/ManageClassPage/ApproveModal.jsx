import { useRef } from 'react';
import styles from './ApproveModal.module.css';
import CloseIcon from '@mui/icons-material/Close';

export default function ApproveModal({ setIsModalOpen }) {
  const outside = useRef();

  const handleModalClick = (e) => {
    if (e.target === outside.current) {
      setIsModalOpen(false);
    }
  };

  return (
    <div className={styles.refModal}>
      <div className={styles.modalBg} ref={outside} onClick={handleModalClick}>
        <div className={styles.RefuseModal}>
          <div className={styles.titleBox}>
            <h3 className={styles.refuseMsg}>신청 승인하기</h3>
            <CloseIcon
              onClick={() => {
                setIsModalOpen(false);
              }}
            ></CloseIcon>
          </div>

          <div className={styles.refuseBox}>
            <img src={`${process.env.PUBLIC_URL}/assets/approve/approve.png`} alt='' />
            <div>
              <p>
                <b>이름</b>
              </p>
            </div>
            <div>
              <p>
                <b>자녀 이름</b>
              </p>
            </div>
            <div className={styles.parentBox}>
              <div>
                <p>
                  <b>자녀와의 관계</b>
                </p>
              </div>
              <div>
                <p>
                  <b>생년월일</b>
                </p>
              </div>
            </div>
            <div className={styles.modalBtn}>
              <p className={styles.acceptBtn} onClick={() => setIsModalOpen(false)}>
                승인하기
              </p>
              <p className={styles.refuseBtn} onClick={() => setIsModalOpen(false)}>
                거절하기
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
