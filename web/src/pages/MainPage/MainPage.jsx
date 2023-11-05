import { Outlet } from 'react-router-dom';
import styles from './MainPage.module.css';
import AccordianMenu from './AccordianMenu';
import { useEffect, useMemo, useState } from 'react';
import { getUserInfo } from '../../api/UserAPI/userAPI';

export default function MainPage() {
  const [userInfo, setUserInfo] = useState({});

  useMemo(() => {
    getUserInfo().then((res) => {
      console.log(res);
      setUserInfo(res);
    });
  }, []);

  return (
    <div className={styles.MainPage}>
      <div className={styles.menubar}>
        <div className={styles.teacherInfo}>
          <p className={styles.teacherInfoP1}>{userInfo.school ? userInfo.school.name : ''}</p>
          <p className={styles.teacherInfoP2}>직급 : 교직원{userInfo.type}</p>
          <p className={styles.teacherInfoP3}>
            {userInfo.schoolClass ? userInfo.schoolClass.grade : ''}학년{' '}
            {userInfo.schoolClass ? userInfo.schoolClass.classNum : ''}반
          </p>
          <p className={styles.teacherInfoP4}>{userInfo.name} 선생님</p>
        </div>
        <div>
          <AccordianMenu />
        </div>
      </div>
      <div className={styles.outlet}>
        <Outlet />
      </div>
    </div>
  );
}
