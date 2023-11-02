import React, { useState, useEffect } from 'react';
import { onChangeEmail, onChangePassword, onChangePasswordConfirm } from './SignupFunc';
import { useNavigate } from 'react-router-dom';
import styles from './Signup.module.css';
import { emailAuthNum, emailAuthNumCheck, userSignup } from '../../api/UserAPI/userAPI';

function Signup() {
  const [userName, setUserName] = useState('');
  const [email, setEmail] = useState('');
  const [emailAuth, setEmailAuth] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');

  const [emailMessage, setEmailMessage] = useState('');
  const [passwordMessage, setPasswordMessage] = useState('');
  const [passwordConfirmMessage, setPasswordConfirmMessage] = useState('');

  const [emailBtn, setEmailBtn] = useState(false);
  const [isPassword, setIsPassword] = useState(false);
  const [isPasswordConfirm, setIsPasswordConfirm] = useState(false);
  const [authorizedEmail, setAuthorizedEmail] = useState(false);
  const [showEmailAuth, setShowEmailAuth] = useState(false);

  const [remainingTime, setRemainingTime] = useState(0);
  const [timerRunning, setTimerRunning] = useState(false);
  const [isTimeout, setIsTimeout] = useState(false);
  const [timeoutMessage, setTimeoutMessage] = useState('인증 시간이 만료되었습니다. 다시 인증해주세요.');

  const [year, setYear] = useState('');
  const [month, setMonth] = useState('');
  const [day, setDay] = useState('');

  const navigate = useNavigate();

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
    startTimer();
    setShowEmailAuth(true);
    emailAuthNum(email);
  }

  async function clickAuthCheckBtn(e) {
    e.preventDefault();
    const response = await emailAuthNumCheck(email, emailAuth);
    console.log(response);
    if (response === 'OK') {
      alert('이메일 인증이 성공하였습니다.');
      setShowEmailAuth(false);
      setAuthorizedEmail(true);
    }
  }

  const startTimer = () => {
    setRemainingTime(180);
    setTimerRunning(true);
  };

  const handleYearChange = (e) => {
    setYear(e.target.value);
  };

  const handleMonthChange = (e) => {
    const value = e.target.value;
    setMonth(value.padStart(2, '0'));
  };

  const handleDayChange = (e) => {
    const value = e.target.value;
    setDay(value.padStart(2, '0'));
  };

  const renderYearOptions = () => {
    const currentYear = new Date().getFullYear();
    const years = [];
    for (let i = currentYear; i >= currentYear - 100; i--) {
      years.push(
        <option key={i} value={i}>
          {i}
        </option>
      );
    }
    return years;
  };

  const renderMonthOptions = () => {
    const months = [];
    for (let i = 1; i <= 12; i++) {
      months.push(
        <option key={i} value={i}>
          {i}
        </option>
      );
    }
    return months;
  };

  const renderDayOptions = () => {
    const days = [];
    for (let i = 1; i <= 31; i++) {
      days.push(
        <option key={i} value={i}>
          {i}
        </option>
      );
    }
    return days;
  };

  const data = {
    userCode: 1003,
    email,
    password: passwordConfirm,
    name: userName,
    birth: `${year}-${month}-${day}`
  };

  return (
    <div className={styles.signupBox}>
      <div className={styles.box}>
        <form className={styles.form}>
          <p className={styles.title}>회원가입</p>
          <div>
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
                disabled={authorizedEmail}
                onChange={(e) => onChangeEmail(e, setEmail, setEmailMessage, setEmailBtn)}
              />
              <button
                className={`${styles.checkbtn}`}
                onClick={(e) => {
                  clickAuthBtn(e);
                }}
                disabled={timerRunning}
              >
                인증하기
              </button>
            </div>
            <p className={`${styles.message} ${emailBtn ? styles.correct : styles.message}`}> {emailMessage} </p>
          </div>

          {showEmailAuth && (
            <div className={styles.emailCertification}>
              <input
                className={`${styles.checkinput}`}
                id='emailAuthNum'
                value={emailAuth}
                onChange={(e) => {
                  setEmailAuth(e.target.value);
                }}
                placeholder='인증번호를 입력해주세요.'
              />
              <button
                className={`${styles.checkbtn}`}
                onClick={(e) => {
                  clickAuthCheckBtn(e);
                }}
                disabled={!emailBtn}
              >
                인증번호 확인
              </button>
            </div>
          )}
          {showEmailAuth && timerRunning && emailBtn && (
            <p className={`${styles.message} ${styles.timer}`}>
              남은 시간: {Math.floor(remainingTime / 60)}분 {remainingTime % 60}초
            </p>
          )}

          {showEmailAuth && isTimeout && !timerRunning && <p className={`${styles.message}`}> {timeoutMessage} </p>}

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
          <div>
            <p className={styles.label}>생년월일</p>
            <div className={styles.dateSelectorWrapper}>
              <select
                value={year}
                onChange={handleYearChange}
                className={`${styles.dateSelector} ${styles.selectyear}`}
              >
                <option value='' className={styles.defaultOption}>
                  연
                </option>
                {renderYearOptions()}
              </select>

              <select
                value={month}
                onChange={handleMonthChange}
                className={`${styles.dateSelector} ${styles.selectmonth}`}
              >
                <option value='' className={styles.defaultOption}>
                  월
                </option>
                {renderMonthOptions()}
              </select>

              <select value={day} onChange={handleDayChange} className={`${styles.dateSelector} ${styles.selectday}`}>
                <option value='' className={styles.defaultOption}>
                  일
                </option>
                {renderDayOptions()}
              </select>
            </div>
          </div>
          <button
            className={styles.signupBtn}
            onClick={async (e) => {
              if (
                isPassword &&
                isPasswordConfirm &&
                authorizedEmail &&
                userName !== '' &&
                year !== '' &&
                month !== '' &&
                day !== ''
              ) {
                const signup = await userSignup(e, data);
                if (signup === 'CREATED') {
                  navigate('/login');
                }
              } else if (!isPassword | !isPasswordConfirm) {
                e.preventDefault();
                alert('비밀번호를 다시 확인해주세요.');
              } else if (!authorizedEmail) {
                e.preventDefault();
                alert('이메일을 확인해주세요.');
              } else if (userName === '') {
                e.preventDefault();
                alert('이름을 입력해주세요.');
              } else if ((year === '') | (month === '') | (day === '')) {
                e.preventDefault();
                alert('생년월일을 입력해주세요.');
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
