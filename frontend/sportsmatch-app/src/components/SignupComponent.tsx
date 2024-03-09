import '../styles/SignupComponent.css'
import { FaLock, FaMailBulk } from 'react-icons/fa'
import React, { FormEvent, useState } from 'react'
import { OpenAPI, RegisterService } from '../generated/api'
import { useNavigate } from 'react-router-dom'

function SignupComponent() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: '',
  })
  const [errorMessage, setErrorMessage] = useState('')
  const navigate = useNavigate()

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    try {
      const response = await RegisterService.register({
        email: formData.email,
        password: formData.password,
      })

      console.log('Registration successful')
      OpenAPI.TOKEN = response.token
      navigate('/newuser')
    } catch (error) {
      console.error('Register Error', error)
      setErrorMessage('The email address or password is invalid.')
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  return (
    <div className="signup-wrapper">
      <form onSubmit={handleSubmit}>
        <h1>Sign up</h1>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        <div className="signup-input-box">
          <label htmlFor="email"></label>
          <input
            type="email"
            name="email"
            placeholder="E-mail address"
            value={formData.email}
            onChange={handleChange}
            required
          />
          <FaMailBulk className="signup-icon" />
        </div>
        <div className="signup-input-box">
          <label htmlFor="password"></label>
          <input
            type="password"
            name="password"
            placeholder="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <FaLock className="signup-icon" />
        </div>
        <div className="signup-input-box">
          <label htmlFor="confirm-password"></label>
          <input
            type="password"
            name="confirmPassword"
            placeholder="confirm password"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
          <FaLock className="signup-icon" />
        </div>
        <div className="signup-remember-forgot">
          <a href="login">Already have an account? Log in here.</a>
        </div>

        <button type="submit">Sign up</button>
      </form>
    </div>
  )
}

export default SignupComponent
