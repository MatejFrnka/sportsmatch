import '../styles/SignupComponent.css'
import { FaLock, FaMailBulk } from 'react-icons/fa'

function SignupComponent() {
  return (
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
          <a href="login">Allready have an account? Log in here.</a>
        </div>

        <button type="submit">Sign up</button>
      </form>
    </div>
  )
}

export default SignupComponent
