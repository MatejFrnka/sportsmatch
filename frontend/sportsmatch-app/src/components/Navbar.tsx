import '../styles/Navbar.css'
import { Link } from 'react-router-dom'
import Avatar from './Avatar'
import { useEffect, useState } from 'react'
import { NavDropdown } from 'react-bootstrap'
import { OpenAPI, ExSecuredEndpointService, ApiError } from '../generated/api'

function Navbar() {
  const loggedInUserImgUrl = '/assets/michael-dam-mEZ3PoFGs_k-unsplash.jpg'
  const loggedOutUserImgUrl = '/assets/unknown-user-placeholder.png'

  const [isLoggedIn, setLoggedIn] = useState<boolean>(false)
  const [userId, setUserId] = useState<number>()

  useEffect(() => {
    if (localStorage.getItem('token')) {
      const init = async () => {
        OpenAPI.TOKEN = localStorage.getItem('token')!
        try {
          const user = await ExSecuredEndpointService.getUserMainPage()
          console.log(user)
          setUserId(user.id)
          setLoggedIn(true)
        } catch (error) {
          const code = (error as ApiError).status
          if (code === 401) {
            localStorage.removeItem('token')
            setLoggedIn(false)
          }
        }
      }
      init()
    }
  }, [])

  return (
    <nav className={'nav'}>
      <div className="logo container-sm">
        <div className="row">
          <div className="col-10">
            <Link to="/">
              <img
                className="logo-png"
                src="/assets/logo.png"
                alt="SportMingle logo"
              />
            </Link>
          </div>
          <div className="col-2 align-items-center avatar-icon">
            {isLoggedIn ? (
              <NavDropdown
                title={
                  <div className="user-image">
                    <Avatar src={loggedInUserImgUrl} />
                  </div>
                }
                id="collapsible-nav-dropdown"
              >
                <NavDropdown.Item href="/">Home</NavDropdown.Item>
                {/* <NavDropdown.Item href="/profile">Profile</NavDropdown.Item> */}
                <NavDropdown.Item href={`/user/${userId}/ratings`}>
                  Profile
                </NavDropdown.Item>
              </NavDropdown>
            ) : (
              <NavDropdown
                title={
                  <div className="user-image">
                    <Avatar src={loggedOutUserImgUrl} />
                  </div>
                }
                id="collapsible-nav-dropdown"
              >
                <NavDropdown.Item href="/login">Login</NavDropdown.Item>
                <NavDropdown.Item href="/signup">Sign up</NavDropdown.Item>
              </NavDropdown>
            )}
          </div>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
