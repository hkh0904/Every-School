import { clickNotiRegister, clickPayRegister } from '../../api/BoardAPI/boardApi';
import styles from './RegisterBtn.module.css';

export default function EditBtn({ title, content, fileName, type }) {
  const data = {
    title: title,
    content: content,
    fileName: fileName
  };

  return (
    <div
      className={styles.RegisterBtn}
      onClick={(e) => {
        alert('열심히 구현중입니다!');
      }}
    >
      <p>수정하기</p>
    </div>
  );
}
