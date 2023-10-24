import React from 'react';
import styles from './NavBar.module.css';

export default function NavBar() {
  return (
    <div className={styles.navbar}>
      <div className={styles.logo}>
        <img className={styles.logoImg1} src={process.env.PUBLIC_URL + '/assets/main/logo.png'} alt='' />
        <img className={styles.logoImg2} src={process.env.PUBLIC_URL + '/assets/main/logo_text.png'} alt='' />
      </div>
      <div className={styles.menu}>
        <p>알림</p>
        <p>로그아웃</p>
      </div>
    </div>
  );
}
