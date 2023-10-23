import { Outlet } from 'react-router-dom';
import styles from './MainPage.module.css'
import AccordianMenu from './AccordianMenu';

function MainPage(){
  return (
    <div className={styles.MainPage}>
      <div className={styles.menubar}>
        <div className={styles.teacherInfo}>
          <p>학교명</p>
          <p>직급 : </p>
          <p>몇학년 몇반</p>
          <p>선생님 이름</p>
        </div>
        <div>
          <AccordianMenu/>
        </div>
      </div>
      <div className={styles.outlet}>
        <Outlet/>
      </div>
    </div>
  )
}

export default MainPage;