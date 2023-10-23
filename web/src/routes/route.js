import { Route, Routes } from 'react-router-dom';
import MainPage from '../pages/MainPage/MainPage';
import RegisterNoti from '../pages/Notification/RegisterNoti';
import ManageMyclass from '../pages/ManageMyclass/ManageMyclass';
import ManageParents from '../pages/ManageParents/ManageParents'
import ReportManage from '../pages/ReportManage/ReportManage';
import ReportHistory from '../pages/ReportHistory/ReportHistory';

function RouteLink() {
  // const { currentUser } = useContext(AuthContext);

  // const ProtectedRoute = ({ children }) => {
  //   if (!currentUser) {
  //     return <Navigate to="/login" />;
  //   }

  //   return children;
  // };
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
        <Route path="/" element={<MainPage />}>
          <Route path='manage/myclass' element={<ManageMyclass/>}/>
        </Route>
        <Route path="/" element={<MainPage />}>
          <Route path='manage/parents' element={<ManageParents/>}/>
        </Route>
        <Route path="/" element={<MainPage />}>
          <Route path='report/manage' element={<ReportManage/>}/>
        </Route>
        <Route path="/" element={<MainPage />}>
          <Route path='report/history' element={<ReportHistory/>}/>
        </Route>
        <Route path="/" element={<MainPage />}>
          <Route path='docs/register-noti' element={<RegisterNoti/>}/>
        </Route>
        {/* <Route Component={PrivateRouter}>
          <Route path="/mypage/:id" element={<Mypage />} />
        </Route> */}
      </Routes>
    </>
  );
}

export default RouteLink;
