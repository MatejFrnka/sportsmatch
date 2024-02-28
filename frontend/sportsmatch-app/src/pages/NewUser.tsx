import NewUserComponent from '../components/NewUserComponent'
import NewUserInputComponent from '../components/NewUserInputComponent'
import NewUserGenderComponent from '../components/NewUserGenderComponent'
import SportsButtonComponent from '../components/SportsButtonComponent'
import NewUserRegistrationButtonComponent from '../components/NewUserRegistrationButtonComponent'
import '../styles/NewUserComponent.css'

export default function NewUser() {
  return (
    <div className={'centered-container'}>
      <div className="wrapper-frame">
        <div className="components-container">
          <NewUserComponent />
          <NewUserInputComponent />
          <NewUserGenderComponent />
          <SportsButtonComponent />
          <NewUserRegistrationButtonComponent />
        </div>
      </div>
    </div>
  )
}
