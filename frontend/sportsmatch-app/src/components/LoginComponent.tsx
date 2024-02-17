import { useState, FormEvent } from 'react'
import '../styles/LoginComponent.css'
import { FaMailBulk, FaLock, FaGoogle, FaFacebook } from 'react-icons/fa'
import { LoginService, OpenAPI } from '../generated/api'
import { useNavigate } from 'react-router-dom'

function LoginComponent() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [errorMessage, setErrorMessage] = useState('')
  const navigate = useNavigate()

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    try {
      const response = await LoginService.login({
        email: email,
        password: password,
      })

      
      console.log(response.data)
      OpenAPI.TOKEN = response.token // this line tells OpenAPI to authenticate with this header
      navigate("/");
    } catch (error) {
      console.error('Login Error', error)
      setErrorMessage('The email address or password is invalid.')
    }
  }

  return (
    <div className="wrapper">
      <form onSubmit={handleSubmit}>
        <h1>Log in</h1>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <div className="input-box">
          <label htmlFor="email"></label>
          <input
            type="email"
            placeholder="E-mail address"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <FaMailBulk className="icon" />
        </div>
        <div className="input-box">
          <label htmlFor="password"></label>
          <input
            type="password"
            placeholder="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
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
