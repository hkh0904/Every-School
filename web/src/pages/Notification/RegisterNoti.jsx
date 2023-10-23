import React from 'react'
import styles from './RegisterNoti.module.css'
import DetailBox from './DetailBox'
import PeopleBox from './PeopleBox'

const RegisterNoti = () => {
  return (
    <div className={styles.registerNoti}>
      <div className={styles.title}>
        <p>가정통신문 등록</p>
      </div>
      <div className={styles.selectBox}>
        <PeopleBox/>
        <DetailBox/>
      </div>
    </div>
  )
}

export default RegisterNoti