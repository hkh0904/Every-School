import { Navigate, Route, Routes } from 'react-router-dom';
import MainPage from '../pages/MainPage/MainPage';
import ManageMyclassPage from '../pages/ManageMyclass/ManageMyclassPage';
import ManageParentsPage from '../pages/ManageParents/ManageParentsPage';
import ReportHistoryPage from '../pages/ReportHistory/ReportHistoryPage';
import ReportDetailPage from '../pages/ReportDetail/ReportDetailPage';
import RegisterNotiPage from '../pages/Notification/RegisterNotiPage';
import RegisterPayPage from '../pages/Notification/RegisterPayPage';
import ManageClassPage from '../pages/ManageClassPage/ManageClassPage';
import ConsultApprovePage from '../pages/ConsultApprovePage/ConsultApprovePage';
import ConsultHistory from '../pages/ConsultHistory/ConsultHistory';
import LoginPage from '../pages/LoginAndSignup/LoginPage';
import SignupPage from '../pages/LoginAndSignup/SignupPage';
import PublicRouter from './PublicRouter';
import PrivateRouter from './PrivateRouter';
import MyPage from '../pages/MyPage/MyPage';
import BadComplainHistoryPage from '../pages/BadComplainHistory/BadComplainHistory';

function RouteLink() {
  return (
    <>
      <Routes>
        {/* 로그인 없이 가능한 주소 */}
        <Route Component={PublicRouter}>
          {/* 회원 */}
          <Route path='/login' element={<LoginPage />} />
          <Route path='/signup' element={<SignupPage />} />
        </Route>

        {/* 로그인으로 보호받는 주소 */}
        <Route Component={PrivateRouter}>
          <Route path='/' element={<MainPage />}>
            {/* 학급 관리 */}
            <Route path='' element={<Navigate to='/manage/myclass' replace />} />
            <Route path='manage/parents' element={<ManageParentsPage />} />
            <Route path='manage/myclass' element={<ManageMyclassPage />} />
            <Route path='manage/class' element={<ManageClassPage />} />
            {/* 신고페이지 */}
            <Route path='report/history' element={<ReportHistoryPage />} />
            <Route path='report/detail' element={<ReportDetailPage />} />
            {/* 고지서 등록 */}
            <Route path='docs/register-noti' element={<RegisterNotiPage />} />
            <Route path='docs/register-payment' element={<RegisterPayPage />} />
            {/* 상담 */}
            <Route path='consult/approve' element={<ConsultApprovePage />} />
            <Route path='consult/history' element={<ConsultHistory />} />
            {/* 악성 민원 */}
            <Route path='badcomplain/history' element={<BadComplainHistoryPage />} />
            {/* 개인정보 수정 */}
            <Route path='mypage' element={<MyPage />} />
          </Route>
        </Route>
      </Routes>
    </>
  );
}

export default RouteLink;
