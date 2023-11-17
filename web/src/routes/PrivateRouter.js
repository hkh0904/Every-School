import { Outlet, Navigate } from 'react-router-dom';

function PrivateRouter() {
  const token = sessionStorage.getItem('token');

  return token ? <Outlet /> : <Navigate to='/login' />;
}
export default PrivateRouter;
