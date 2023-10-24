// 로그인 페이지

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './Login.module.css';
import { NavLink } from 'react-router-dom';
import { clickLogin } from './SignupFunc';
import { useDispatch } from 'react-redux';
import { changeLoginId, changeLoginInfo } from '../../slices/UserSlice';

function Login() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');

  const dispatch = useDispatch();

  const data = { id, password };

  const navigate = useNavigate();

  return (
    <div className={styles.loginBox}>
      <div className={`${styles.box}`}>
        <form className={styles.form}>
          <img className={styles.logoGif} src={process.env.PUBLIC_URL + '/assets/main/weblogo.gif'} alt='' />
          <div>
            <label className={styles.label} htmlFor='id'>
              아이디
            </label>
            <input
              className={styles.emailInput}
              type='text'
              id='id'
              value={id}
              placeholder='이메일 입력'
              onChange={(e) => {
                setId(e.target.value);
              }}
            />
          </div>
          <div>
            <label className={styles.label} htmlFor='password'>
              비밀번호
            </label>
            <input
              className={styles.pwdInput}
              type='password'
              id='password'
              value={password}
              placeholder='비밀번호 입력'
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            />
          </div>
          <p className={styles.forget}>로그인 정보를 잊으셨나요?</p>
          <button
            className={styles.loginBtn}
            variant='contained'
            onClick={async (e) => {
              const loginRes = await clickLogin(e, data);
              if (loginRes === 1) {
                navigate('/loginmain');
                const ACCESS_TOKEN = localStorage.getItem('accessToken');
                dispatch(changeLoginInfo(ACCESS_TOKEN));
                dispatch(changeLoginId(id));
              }
            }}
          >
            로그인
          </button>
          <div className={styles.join}>
            <span>아직 계정이 없으신가요? </span>
            <NavLink className={styles.clickjoin} to='/signup'>
              선생님 회원가입
            </NavLink>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Login;
