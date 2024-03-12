import { Navigate, Outlet } from 'react-router-dom'

const PrivateRoute = () => {
  const requestedUrl = window.location.pathname

  if (localStorage.getItem('token')) {
    return <Outlet />
  } else {
    return <Navigate to={'/login'} state={requestedUrl} />
  }
}

export default PrivateRoute
