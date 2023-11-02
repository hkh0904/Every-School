import React, { useState, useEffect } from 'react';
import { onChangeEmail, onChangePassword, onChangePasswordConfirm } from './SignupFunc';
import { useNavigate } from 'react-router-dom';
import { clickSignup } from './AuthApi';
import styles from './Signup.module.css';
import { emailAuthNum } from '../../api/UserAPI/userAPI';

function Signup() {
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [emailAuth, setEmailAuth] = useState('');
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

  const [emailMessage, setEmailMessage] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');

  const [isEmail, setIsEmail] = useState(false);
  const [emailBtn, setEmailBtn] = useState(false);
  const [isPassword, setIsPassword] = useState(false);
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);
  const [authorizedEmail, setAuthorizedEmail] = useState(false);
  const [showEmailAuth, setShowEmailAuth] = useState(false);

  const [remainingTime, setRemainingTime] = useState(0);
  const [timerRunning, setTimerRunning] = useState(false);
  const [isTimeout, setIsTimeout] = useState(false);
  const [timeoutMessage, setTimeoutMessage] = useState('인증 시간이 만료되었습니다. 다시 인증해주세요.');

  const navigate = useNavigate();

  const startTimer = () => {
    setRemainingTime(180);
    setTimerRunning(true);
  };

  useEffect(() => {
    let interval;
    if (timerRunning && remainingTime > 0) {
      interval = setInterval(() => {
        setRemainingTime((prevTime) => prevTime - 1);
      }, 1000);
    } else if (remainingTime === 0) {
      setTimerRunning(false);
      setIsTimeout(true);
    }
    return () => {
      clearInterval(interval);
    };
  }, [timerRunning, remainingTime]);

  function clickAuthBtn(e) {
    e.preventDefault();
    startTimer(); // 인증버튼을 클릭했을 때 타이머 시작
    emailAuthNum(email);
    setShowEmailAuth(true);
  }

  const data = {
    userName,
    email,
    id,
    password: passwordConfirm
  };

  return (
    <div className={styles.signupBox}>
      <div className={styles.box}>
        <form className={styles.form}>
          <p className={styles.title}>회원가입</p>
          <label className={styles.label} htmlFor='email'>
            이메일{' '}
          </label>
          <div className={styles.emailCertification}>
            <input
              className={`${styles.checkinput}`}
              type='text'
              id='email'
              value={email}
              placeholder='이메일 계정'
              onChange={(e) => onChangeEmail(e, setEmail, setEmailMessage, setEmailBtn)}
              disabled={isEmail}
            />
            <button
              className={`${styles.checkbtn}`}
              onClick={(e) => {
                clickAuthBtn(e);
              }}
              disabled={!emailBtn && !isEmail}
            >
              인증받기
            </button>
          </div>
          <p className={`${styles.message} ${isEmail ? styles.correct2 : styles.message}`}> {emailMessage} </p>

          {showEmailAuth && (
            <div className={styles.emailCertification}>
              <input
                className={`${styles.checkinput}`}
                id='emailAuthNum'
                value={emailAuth}
                onChange={(e) => {}}
                placeholder='인증번호를 입력해주세요.'
              />
              <button
                className={`${styles.checkbtn}`}
                onClick={(e) => {
                  clickAuthBtn();
                }}
                disabled={!emailBtn && !isEmail}
              >
                인증번호 확인
              </button>
            </div>
          )}
          {timerRunning && !isTimeout && (
            <p className={`${styles.message} ${styles.timer}`}>
              남은 시간: {Math.floor(remainingTime / 60)}분 {remainingTime % 60}초
            </p>
          )}

          {isTimeout && isEmail && <p className={`${styles.message}`}> {timeoutMessage} </p>}

          <div>
            <label className={styles.label} htmlFor='userName'>
              이름{' '}
            </label>
            <input
              className={styles.input}
              type='text'
              id='userName'
              value={userName}
              placeholder='이름 입력'
              onChange={(e) => setUserName(e.target.value)}
            />
          </div>
          <div>
            <label className={styles.label} htmlFor='password'>
              비밀번호
            </label>
            <input
              className={styles.input}
              type='password'
              id='password'
              value={password}
              placeholder='비밀번호 입력'
              onChange={(e) => onChangePassword(e, setPassword, setPasswordMessage, setIsPassword)}
            />
            <p className={styles.message}> {passwordMessage} </p>
          </div>
          <div>
            <label className={styles.label} htmlFor='passwordConfirm'></label>
            <input
              className={styles.pwdCheckInput}
              type='password'
              id='passwordConfirm'
              value={passwordConfirm}
              placeholder='비밀번호 확인'
              onChange={(e) =>
                onChangePasswordConfirm(
                  e,
                  password,
                  setPasswordConfirm,
                  setPasswordConfirmMessage,
                  setIsPasswordConfirm
                )
              }
            />
            <p className={styles.message}> {passwordConfirmMessage} </p>
          </div>
          <button
            className={styles.signupBtn}
            onClick={async (e) => {
              if (isPassword && isPasswordConfirm && isEmail && authorizedEmail) {
                const signup = await clickSignup(e, data);
                if (signup.data === 'success') {
                  navigate('/account/login');
                }
              } else if (!isPassword | !isPasswordConfirm) {
                e.preventDefault();
                alert('비밀번호를 다시 확인해주세요.');
              } else if (!isEmail || !authorizedEmail) {
                e.preventDefault();
                alert('이메일을 확인해주세요.');
              }
            }}
          >
            가입하기
          </button>
        </form>
      </div>
    </div>
  );
}

export default Signup;
