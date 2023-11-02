import React from 'react';
import styles from './NavBar.module.css';
import { useNavigate } from 'react-router';

export default function NavBar() {
  const navigate = useNavigate();

  function clickLogin() {
    sessionStorage.clear();
    navigate('/login');
  }

  return (
    <div className={styles.navbar}>
      <div className={styles.logo}>
        <img className={styles.logoImg1} src={process.env.PUBLIC_URL + '/assets/main/logo.png'} alt='' />
        <img className={styles.logoImg2} src={process.env.PUBLIC_URL + '/assets/main/logo_text.png'} alt='' />
      </div>
      <div className={styles.menu}>
        <p>알림</p>
        <p onClick={clickLogin}>로그아웃</p>
      </div>
    </div>
  );
}
