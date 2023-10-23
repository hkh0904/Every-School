import PeopleTable from './PeopleTable';
import styles from './PeopleBox.module.css'
import { useState } from 'react';


function PeopleBox() {
  const [selectAll, setSelectAll] = useState(false);

  const handleSelectAll = () => {
    setSelectAll(!selectAll);
  };



  return (
    <div className={styles.peopleBox}>
      <div className={styles.selectPeople}>
        <p className={styles.peopleTitle}>대상</p>
        <div className={styles.allSelect}>
          <p>전체 선택</p>
          <input type="checkbox" name="" id=""
          checked={selectAll} 
          onChange={handleSelectAll} />
        </div>
      </div>
      <div className={styles.table}>
        <PeopleTable selectAll={selectAll}/>
      </div>
    </div>
  );
}

export default PeopleBox