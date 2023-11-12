import { useEffect, useState } from 'react';
import styles from './MyPage.module.css';
import { getUserInfo } from '../../api/UserAPI/userAPI';
import ChangePassword from './ChangePassword';

export default function MyPage() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [userInfo, setUserInfo] = useState({});
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
              <p>{userInfo.length !== 0 ? userInfo?.name : null}</p>
            </div>
            <hr />
            <div className={styles.userInformationContentBox}>
              <p>성별</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>이메일</p>
              <p>{userInfo.length !== 0 ? userInfo?.email : null}</p>
            </div>
            <hr />

            <div className={styles.userInformationContentBox}>
              <p>생년월일</p>
              <p>{userInfo.length !== 0 ? userInfo?.birth : null}</p>
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

              <p>{userInfo.length !== 0 ? userInfo?.school?.name : null}</p>
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
                {userInfo.length !== 0 ? userInfo?.schoolClass?.grade : null}학년{' '}
                {userInfo.length !== 0 ? userInfo?.schoolClass?.classNum : null}반
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
              <p
                className={styles.pwdchangetext}
                onClick={() => {
                  setIsModalOpen(true);
                }}
              >
                변경하기
              </p>
            </div>
            <hr />

            <div className={styles.userInformationContentButton}>
              <p>회원탈퇴</p>
            </div>
          </div>
        </div>
      </div>
      {isModalOpen ? <ChangePassword setIsModalOpen={setIsModalOpen} /> : null}
    </div>
  );
}
