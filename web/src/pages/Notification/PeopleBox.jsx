import styles from './PeopleBox.module.css'
import PeopleTable from './PeopleTable';

function PeopleBox() {
  return (
    <div className={styles.peopleBox}>
      <div className={styles.selectPeople}>
        <p className={styles.peopleTitle}>대상</p>
        <div className={styles.allSelect}>
          <p>전체 선택</p>
          <input type="checkbox" name="" id="" />
        </div>
      </div>
      <div className={styles.table}>
        <PeopleTable/>
      </div>
    </div>
  );
}

export default PeopleBox