import { Outlet } from 'react-router-dom';
import styles from './MainPage.module.css';
import AccordianMenu from './AccordianMenu';

export default function MainPage() {
  return (
    <div className={styles.MainPage}>
      <div className={styles.menubar}>
        <div className={styles.teacherInfo}>
          <p className={styles.teacherInfoP1}>학교명</p>
          <p className={styles.teacherInfoP2}>직급 : </p>
          <p className={styles.teacherInfoP3}>몇학년 몇반</p>
          <p className={styles.teacherInfoP4}>선생님 이름</p>
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
