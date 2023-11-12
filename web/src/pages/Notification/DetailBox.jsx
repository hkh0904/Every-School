import styles from './DetailBox.module.css';

export default function DetailBox({ setTitle, setContent, setFileName, fileName }) {
  const handleFileInputChange = (event) => {
    const newFiles = event.target.files;
    console.log(newFiles);
    if (newFiles.length > 0) {
      setFileName(Array.from(newFiles));
    }
  };

  return (
    <div className={styles.detailBox}>
      <p className={styles.detailTitle}>작성하기</p>
      <div className={styles.inputTitle}>
        <input type='text' placeholder='제목 작성하기' onChange={(e) => setTitle(e.target.value)} />
      </div>
      <div className={styles.inputDesc}>
        <textarea
          name=''
          id=''
          cols='30'
          rows='10'
          placeholder='내용 작성하기'
          onChange={(e) => setContent(e.target.value)}
        ></textarea>
      </div>
      <div className={styles.inputFile}>
        <input
          className={styles.fileHolder}
          value={fileName.map((file) => file.name).join(', ') || '첨부파일'}
          placeholder='첨부파일'
          readOnly
        />
        <label htmlFor='file'>파일선택</label>
        <input className={styles.fileSelect} type='file' multiple={true} id='file' onChange={handleFileInputChange} />
      </div>
    </div>
  );
}
