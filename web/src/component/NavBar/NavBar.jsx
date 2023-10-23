import React from 'react';
import styles from './NavBar.module.css';

function NavBar() {
  return (
    <div className={styles.navbar}>
      <div className={styles.logo}>
        <img src={process.env.PUBLIC_URL + "assets/main/logo.png"} alt="" />
        <img src={process.env.PUBLIC_URL + "assets/main/logo_text.png"} alt="" />
      </div>
      <div className={styles.menu}>
        <p>알림</p>
        <p>로그아웃</p>
      </div>
    </div>
  );
};

export default NavBar;
