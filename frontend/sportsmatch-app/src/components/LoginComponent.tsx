import '../styles/LoginComponent.css'
import { FaMailBulk, FaLock, FaGoogle, FaFacebook } from 'react-icons/fa'

function LoginComponent() {
  return (
    <div className="wrapper">
      <form action="">
        <h1>Log in</h1>
        <div className="input-box">
          <label htmlFor="email"></label>
          <input type="email" placeholder="E-mail addres" required />
          <FaMailBulk className="icon" />
        </div>
        <div className="input-box">
          <label htmlFor="password"></label>
          <input type="password" placeholder="password" required />
          <FaLock className="icon" />
        </div>
        <div className="remember-forgot">
          <label>
            <input type="checkbox" />
            Remember me{' '}
          </label>
          <a href="#">Forgot password</a>
        </div>

        <button type="submit">Log in</button>
        <div className="register-link">
          <p>
            Dont have an account <a href="signup">Register</a>
          </p>
        </div>
        <p className="alt-login-text">Or log in using</p>

        <div className="alt-login">
          <div className="facebook">
            <FaFacebook className="fb-icon" />
          </div>
          <div className="google">
            <FaGoogle className="g-icon" />
          </div>
        </div>
      </form>
    </div>
  )
}

export default LoginComponent
