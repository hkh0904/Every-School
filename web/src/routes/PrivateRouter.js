import { Outlet, Navigate } from 'react-router-dom';

function PrivateRouter() {
  const token = sessionStorage.getItem('token');
  if (token === null) {
    window.alert('로그인이 필요합니다.');
  }
  return token ? <Outlet /> : <Navigate to='/login' />;
}
export default PrivateRouter;
