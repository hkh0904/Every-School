import styles from './MyPage.module.css';

export default function MyPage() {
  const dummy = {
    name: '이지혁',
    gender: 'M',
    email: 'illu@ssafy.com',
    birth: '2000-01-01',
    school: '휘낭시에초등학교',
    jobLevel: '담당 선생님',
    grade: '3학년 2반'
  };
  return (
    <div className={styles.MyPage}>
      <div className={styles.title}>
        <p>개인 정보</p>
      </div>
      <div className={styles.userInformation}>
        <div className={styles.userInformationCard}>
          <div className={styles.userInformationTitle}>
            <p>개인 프로필</p>
          </div>
          <div className={styles.userInformationContent}>
            <div className={styles.userInformationContentBox}>
              <p>이름</p>
              <p>{dummy.name}</p>
            </div>
            <hr />
            <div className={styles.userInformationContentBox}>
              <p>성별</p>
              <p>{dummy.gender}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>이메일</p>
              <p>{dummy.email}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>생년월일</p>
              <p>{dummy.birth}</p>
            </div>
          </div>
        </div>
        <div className={styles.userInformationCard}>
          <div className={styles.userInformationTitle}>
            <p>학교 정보</p>
          </div>
          <div className={styles.userInformationContent}>
            <div className={styles.userInformationContentBox}>
              <p>학교</p>
              <p>{dummy.school}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>직급</p>
              <p>{dummy.jobLevel}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>담당 학급</p>
              <p>{dummy.grade}</p>
            </div>
          </div>
        </div>
        <div className={styles.userInformationCard}>
          <div className={styles.userInformationTitle}>
            <p>개인 정보</p>
          </div>
          <div className={styles.userInformationContent}>
            <div className={styles.userInformationContentButton}>
              <p>개인 정보 처리 방침</p>
              <p>자세히 보기</p>
            </div>
            <hr />

            <div className={styles.userInformationContentButton}>
              <p>비밀번호</p>
              <p>변경하기</p>
            </div>
            <hr />

            <div className={styles.userInformationContentButton}>
              <p>회원탈퇴</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
