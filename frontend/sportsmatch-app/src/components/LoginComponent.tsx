import { useState, FormEvent } from 'react'
import '../styles/LoginComponent.css'
import { FaMailBulk, FaLock, FaGoogle, FaFacebook } from 'react-icons/fa'
import { LoginService } from '../generated/api'

function LoginComponent() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    try {
      const response = await LoginService.login({ email: email, password: password })

      console.log(response.data)
    } catch (error) {
      console.error('The username or password is invalid', error)
    }
  }

  return (
    <div className="wrapper">
      <form onSubmit={handleSubmit}>
        <h1>Log in</h1>
        <div className="input-box">
          <label htmlFor="email"></label>
          <input
            type="email"
            placeholder="E-mail addres"
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

async function sendForm(
  e: FormEvent<HTMLFormElement>,
  email: string,
  password: string,
) {
  e.preventDefault()

  try {
    const response = await LoginService.login({ email: email, password: password })
    console.log(response.data)
  } catch (error) {
    console.error('The username or password is invalid', error)
  }
}

export default LoginComponent
