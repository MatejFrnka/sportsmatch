import "../styles/LoginComponent.css";
import { FaLock, FaMailBulk } from "react-icons/fa";

function SignupComponent() {
  return (
    <div className="wrapper">
      <form action="">
        <h1>Signup</h1>
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
        </div>

        <button type="submit">Signup</button>
      </form>
    </div>
  );
}

export default SignupComponent;
