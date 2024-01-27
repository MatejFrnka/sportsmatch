import "../styles/LoginComponent.css";
import { FaMailBulk, FaLock } from "react-icons/fa";

function LoginComponent() {
  return (
    <div className="wrapper">
      <form action="">
        <h1>Login</h1>
        <div className="input-box">
          <input type="email" placeholder="E-mail addres" required />
          <FaMailBulk className="icon" />
        </div>
        <div className="input-box">
          <input type="password" placeholder="password" required />
          <FaLock className="icon" />
        </div>
        <div className="remember-forgot">
          <label>
            <input type="checkbox" />
            Remember me{" "}
          </label>
          <a href="#">Forgot password</a>
        </div>

        <button type="submit">Login</button>

        <div className="register-link">
          <p>
            Don't have an account <a href="#">Register</a>
          </p>
        </div>
      </form>
    </div>
  );
}

export default LoginComponent;
