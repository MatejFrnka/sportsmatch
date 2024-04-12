import { useState, useEffect } from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import { OpenAPI, ExSecuredEndpointService, ApiError } from '../generated/api'

const PrivateRoute = () => {
  const requestedUrl = window.location.pathname
  const [isLoggedIn, setLoggedIn] = useState<boolean>(false)

  useEffect(() => {
    const init = async () => {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      try {
        await ExSecuredEndpointService.getUserMainPage()
        setLoggedIn(true)
      } catch (error) {
        const code = (error as ApiError).status
        if (code === 401 || code === 403) {
          localStorage.removeItem('token')
          setLoggedIn(false)
        }
      }
    }
    init()
  }, [])

  if (isLoggedIn) {
    return <Outlet />
  } else {
    return <Navigate to={'/login'} state={requestedUrl} />
  }
}

export default PrivateRoute
