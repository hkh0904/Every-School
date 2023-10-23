import { Route, Routes } from 'react-router-dom';
import MainPage from '../pages/MainPage/MainPage';
import RegisterNoti from '../pages/Notification/RegisterNoti';
import ManageClassPage from '../pages/ManageClassPage/ManageClassPage';

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
          <Route path="docs/register-noti" element={<RegisterNoti />} />

          <Route path="manage/class" element={<ManageClassPage />} />
        </Route>
        {/* <Route Component={PrivateRouter}>
          <Route path="/mypage/:id" element={<Mypage />} />
        </Route> */}
      </Routes>
    </>
  );
}

export default RouteLink;
