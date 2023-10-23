import { useState } from 'react';
import styles from './DetailBox.module.css'


export default function DetailBox() {
  const [fileName, setFileName] = useState("");
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");


  const handleFileInputChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setFileName(file.name);
    }
  };

  return (
    <div className={styles.detailBox}>
      <p className={styles.detailTitle}>작성하기</p>
      <div className={styles.inputTitle}>
        <input type="text" placeholder='제목 작성하기'
        onChange={(e) => setTitle(e.target.value)}/>
      </div>
      <div className={styles.inputDesc}>
        <textarea name="" id="" cols="30" rows="10" placeholder='내용 작성하기'
        onChange={(e) => setContent(e.target.value)}></textarea>
      </div>
      <div className={styles.inputFile}>
        <input
          className={styles.fileHolder}
          value={fileName || "첨부파일"}
          placeholder="첨부파일"
          readOnly
        />
        <label htmlFor="file">파일선택</label>
        <input
          className={styles.fileSelect}
          type="file"
          id="file"
          onChange={handleFileInputChange}
        />
      </div>
    </div>
  );
}
