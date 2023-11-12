import React, { useRef, useState } from 'react';
import styles from './ChangePassword.module.css';
import { changePassword } from '../../api/UserAPI/userAPI';
import { Password } from '@mui/icons-material';

const ChangePassword = ({ setIsModalOpen }) => {
  const outside = useRef();
  const [curPassword, setCurPassword] = useState('');
  const [password, setPassword] = useState('');
  const [samePassword, setSamePassword] = useState('');

  const curPasswordChange = (e) => {
    setCurPassword(e.target.value);
  };
  const passwordChange = (e) => {
    setPassword(e.target.value);
  };
  const samePasswordChange = (e) => {
    setSamePassword(e.target.value);
  };
  const handleModalClick = (e) => {
    if (e.target === outside.current) {
      setIsModalOpen(false);
    }
  };
  return (
    <div className={styles.refModal}>
      <div className={styles.modalBg} onClick={handleModalClick}>
        <div className={styles.RefuseModal}>
          <p className={styles.refuseMsg}>비밀번호 변경</p>
          <div className={styles.inputbox}>
            <input
              className={styles.refuseInput}
              type='password'
              placeholder='현재 비밀번호를 입력해주세요'
              value={curPassword}
              onChange={curPasswordChange}
            />
            <input
              className={styles.refuseInput}
              type='password'
              placeholder='비밀번호를 입력해주세요'
              value={password}
              onChange={passwordChange}
            />
            <input
              className={styles.refuseInput}
              type='password'
              placeholder='새로운 비밀번호를 입력해주세요'
              value={samePassword}
              onChange={samePasswordChange}
            />
          </div>
          <div className={styles.modalBtn}>
            <p
              className={styles.refuseBtn}
              onClick={async () => {
                console.log('모달 닫기');
                const passwordRegExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
                if (!passwordRegExp.test(password)) {
                  alert('숫자, 영문, 특수문자 조합으로 8자리를 만들어주세요');
                } else if (password !== samePassword) {
                  alert('비밀번호가 같지 않습니다');
                } else {
                  const patchpassword = await changePassword(curPassword, password);
                  console.log(patchpassword);
                  if (patchpassword === 'OK') {
                    alert('비밀번호가 변경되었습니다.');
                    setIsModalOpen(false);
                  } else {
                    alert(
                      '비밀번호 변경이 실패했습니다\n현재 비밀번호를 확인해주세요\n증상이 계속된다면 관리자에게 문의하세요'
                    );
                  }
                }
              }}
            >
              변경하기
            </p>
            <p className={styles.cancelBtn} onClick={() => setIsModalOpen(false)}>
              취소
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChangePassword;
