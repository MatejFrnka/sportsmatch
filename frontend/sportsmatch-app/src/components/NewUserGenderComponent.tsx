import '../styles/NewUserComponent.css'

function NewUserGenderComponent() {
  return (
    <div className="wrapper">
      <form action="">
        <div className="gender">
          <p>Select your gender</p>
          <div className="male-female">
            <a href="">
              <div className="parent-male">
                <img className="img-male" src="./assets/male.png" alt="male" />
                <div className="male"></div>
              </div>
            </a>
            <a href="">
              <div className="parent-female">
                <img
                  className="img-female"
                  src="./assets/female.png"
                  alt="female"
                />
                <div className="female"></div>
              </div>
            </a>
          </div>
        </div>
      </form>
    </div>
  )
}

export default NewUserGenderComponent