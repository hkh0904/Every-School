import styles from './DetailBox.module.css'


function DetailBox() {
  return (
    <div className={styles.detailBox}>
      <p className={styles.detailTitle}>작성하기</p>
      <input type="text" placeholder='제목 작성하기' />
      <br />
      <textarea name="" id="" cols="30" rows="10">내용 작성하기</textarea>
      <br />
      <input type="file" name="file" id="" />
    </div>
  );
}

export default DetailBox