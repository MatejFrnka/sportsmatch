import { useState, useEffect } from 'react'
import { Navigate, Outlet } from 'react-router-dom'
import { OpenAPI, ExSecuredEndpointService, ApiError } from '../generated/api'

const PrivateRoute = () => {
  const requestedUrl = window.location.pathname
  const [isAuthorized, setAuthorized] = useState<boolean>(true)

  useEffect(() => {
    if (!localStorage.getItem('token')) {
      setAuthorized(false)
    }
  }, [])

  useEffect(() => {
    const init = async () => {
      if (localStorage.getItem('token')) {
        OpenAPI.TOKEN = localStorage.getItem('token')!
        try {
          await ExSecuredEndpointService.getUserMainPage()
        } catch (error) {
          const code = (error as ApiError).status
          if (code === 401) {
            localStorage.removeItem('token')
            setAuthorized(false)
          }
        }
      }
    }
    init()
  }, [])

  if (isAuthorized) {
    return <Outlet />
  } else {
    return <Navigate to={'/login'} state={requestedUrl} />
  }
}

export default PrivateRoute
