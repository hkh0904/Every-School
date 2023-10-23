import styles from './RegisterNotiPage.module.css'
import DetailBox from './DetailBox'
import PeopleBox from './PeopleBox'
import RegisterBtn from '../../component/Button/RegisterBtn'

export default function RegisterPayPage () {
  return (
    <div className={styles.registerNoti}>
      <div className={styles.title}>
        <p>고지서 등록</p>
      </div>
      <div className={styles.selectBox}>
        <PeopleBox/>
        <DetailBox/>
      </div>
      <div className={styles.register}>
        <p>등록하기 버튼을 누르면 가정통신문이 전송됩니다.</p>
        <RegisterBtn/>
      </div>
    </div>
  )
}