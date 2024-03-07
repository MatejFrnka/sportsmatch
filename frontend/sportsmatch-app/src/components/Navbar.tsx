import '../styles/Navbar.css'
import { Link } from 'react-router-dom'

function Navbar() {
  return (
    <nav className={'nav'}>
      <div className="logo container-sm">
        <div className="row">
          <Link to="/">
            <img
              className="logo-png"
              src="/assets/logo.png"
              alt="SportMingle logo"
            />
          </Link>
        </div>
      </div>
    </nav>
  )
}

export default Navbar
