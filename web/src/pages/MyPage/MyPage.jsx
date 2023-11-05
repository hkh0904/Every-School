import { useEffect, useState } from 'react';
import styles from './MyPage.module.css';
import { getUserInfo } from '../../api/UserAPI/userAPI';

export default function MyPage() {
  const [userInfo, setUserInfo] = useState({
    name: '이지혁',
    gender: 'M',
    email: 'illu@ssafy.com',
    birth: '2000-01-01',
    school: '휘낭시에초등학교',
    jobLevel: '담당 선생님',
    grade: '3학년 2반'
  });
  useEffect(() => {
    const fetchData = async () => {
      try {
        const myInfo = await getUserInfo();
        console.log(myInfo);
        setUserInfo(myInfo);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

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
              <p>{userInfo.name}</p>
            </div>
            <hr />
            <div className={styles.userInformationContentBox}>
              <p>성별</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>이메일</p>
              <p>{userInfo.email}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>생년월일</p>
              <p>{userInfo.birth}</p>
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
              <p>{userInfo.school.name}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>직급</p>
              <p>담당선생님</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>담당 학급</p>
              <p>
                {userInfo.schoolClass?.grade}학년{userInfo.schoolClass?.classNum}반
              </p>
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
