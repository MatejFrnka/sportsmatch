import '../styles/SignupComponent.css'
import { FaLock, FaMailBulk } from 'react-icons/fa'
import { Link } from 'react-router-dom'

function SignupComponent() {
  return (
    <div className="centered-container">
      <div className="signup-wrapper">
        <form action="">
          <h1>Sign up</h1>
          <div className="signup-input-box">
            <label htmlFor="email"></label>
            <input type="email" placeholder="E-mail addres" required />
            <FaMailBulk className="signup-icon" />
          </div>
          <div className="signup-input-box">
            <label htmlFor="password"></label>
            <input type="password" placeholder="password" required />
            <FaLock className="signup-icon" />
          </div>
          <div className="signup-input-box">
            <label htmlFor="confirm-password"></label>
            <input
              type="confirm-password"
              placeholder="confirm password"
              required
            />
            <FaLock className="signup-icon" />
          </div>
          <div className="signup-remember-forgot">
            <Link to="/login">Already have an account? Log in here.</Link>
          </div>

          <button type="submit">Sign up</button>
        </form>
      </div>
    </div>
  )
}

export default SignupComponent
