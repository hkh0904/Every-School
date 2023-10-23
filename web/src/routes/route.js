import { Route, Routes } from 'react-router-dom';
import MainPage from '../pages/MainPage/MainPage';
import ManageMyclassPage from '../pages/ManageMyclass/ManageMyclassPage';
import ManageParentsPage from '../pages/ManageParents/ManageParentsPage';
import ReportHistoryPage from '../pages/ReportHistory/ReportHistoryPage';
import RegisterNotiPage from '../pages/Notification/RegisterNotiPage';
import RegisterPayPage from '../pages/Notification/RegisterPayPage';
import ManageClassPage from '../pages/ManageClassPage/ManageClassPage';

function RouteLink() {
  return (
    <>
      <Routes>
        {/* 예시 주소 */}

        {/* <Route path="/recommend/festival/:id" element={<FestivalDetail />} /> */}

        {/* 로그인 없이 가능한 주소 */}
        {/* <Route Component={PublicRouter}>
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
        </Route> */}

        {/* 로그인으로 보호받는 주소 */}
        <Route path='/' element={<MainPage />}>
          {/* 학급 관리 */}
          <Route path='manage/parents' element={<ManageParentsPage />} />
          <Route path='manage/myclass' element={<ManageMyclassPage />} />
          <Route path='manage/class' element={<ManageClassPage />} />
          {/* 신고페이지 */}
          <Route path='report/history' element={<ReportHistoryPage />} />
          {/* 고지서 등록 */}
          <Route path='docs/register-noti' element={<RegisterNotiPage />} />
          <Route path='docs/register-payment' element={<RegisterPayPage />} />
        </Route>

        {/* <Route Component={PrivateRouter}>
          <Route path="/mypage/:id" element={<Mypage />} />
        </Route> */}
      </Routes>
    </>
  );
}

export default RouteLink;
