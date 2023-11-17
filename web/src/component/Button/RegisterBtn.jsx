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
      onClick={async (e) => {
        if (type === 'noti') {
          const response = await clickNotiRegister(e, data);
          console.log(response);
          if (response === 1) {
            alert('등록이 완료되었습니다.');
            window.location.reload();
          } else {
            alert('입력 정보를 확인해주세요.');
          }
        } else {
          const response = await clickPayRegister(e, data);
          if (response === 1) {
            alert('등록이 완료되었습니다.');
            window.location.reload();
          } else {
            alert('입력 정보를 확인해주세요.');
          }
        }
      }}
    >
      <p>등록하기</p>
    </div>
  );
}
