import { clickNotiRegister, clickPayRegister } from '../../api/BoardAPI/boardApi';
import styles from './RegisterBtn.module.css';

export default function RegisterBtn({ title, content, fileName, type }) {
  const data = {
    title: title,
    content: content,
    fileName: fileName
  };

  return (
    <div
      className={styles.RegisterBtn}
      onClick={(e) => {
        if (type === 'noti') {
          clickNotiRegister(e, data);
        } else {
          clickPayRegister(e, data);
        }
      }}
    >
      <p>등록하기</p>
    </div>
  );
}
