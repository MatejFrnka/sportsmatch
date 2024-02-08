import '../styles/NewUserComponent.css'
import { FaRegUser, FaRegCalendarAlt } from 'react-icons/fa'

function NewUserInputComponent() {
  return (
    <div className="newUser-wrapper">
      <form action="">
        <h1>Profile</h1>
        <div className="newUser-input-box">
          <input type="text" placeholder="Enter Username" required />
          <FaRegUser className="newUser-icon" />
        </div>
        <div className="newUser-input-box">
          <input type="text" placeholder="Enter Date of Birth" required />
          <FaRegCalendarAlt className="newUser-icon" />
        </div>
      </form>
    </div>
  )
}

export default NewUserInputComponent
