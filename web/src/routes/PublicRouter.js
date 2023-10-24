import { Navigate, Outlet } from 'react-router-dom';

function PublicRouter() {
  const token = sessionStorage.getItem('accessToken');
  return token ? <Navigate to='/' /> : <Outlet />;
}

export default PublicRouter;
