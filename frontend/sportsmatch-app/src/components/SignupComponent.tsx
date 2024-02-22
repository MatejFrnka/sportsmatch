import '../styles/SignupComponent.css'
import { FaLock, FaMailBulk } from 'react-icons/fa'
import { useState } from 'react'

function SignupComponent() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: ''
  });

  const handleSubmit = async (event : any) => {
    event.preventDefault();

    try {
      const response = await fetch('http://example.com/api/v1/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        console.log('Registration successful');

      } else {
        console.error('Registration failed');

      }
    } catch (error) {
      console.error('Error during registration:', error);

    }
  };

  const handleChange = (event : any) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value
    });
  };
  return (
    <div className="signup-wrapper">
      <form onSubmit={handleSubmit}>
        <h1>Sign up</h1>
        <div className="signup-input-box">
          <label htmlFor="email"></label>
          <input type="email" name="email" placeholder="E-mail address" value={formData.email} onChange={handleChange} required />
          <FaMailBulk className="signup-icon" />
        </div>
        <div className="signup-input-box">
          <label htmlFor="password"></label>
          <input type="password" name="password" placeholder="password" value={formData.password} onChange={handleChange} required />
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
