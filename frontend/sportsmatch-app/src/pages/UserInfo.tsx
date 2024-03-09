import { SportDTO } from '../generated/api'
import '../styles/UserInfo.css'
import { ChangeEvent, useState } from 'react'
import Sport from '../components/Sport'

export default function UserInfo() {
  const [selectedGender, setSelectedGender] = useState<string>('')
  const [username, setUsername] = useState<string>('')
  const [dateOfBirth, setDateOfBirth] = useState<string>('')

  const handleUsernameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value)
  }

  const handleDateOfBirth = (e: ChangeEvent<HTMLInputElement>) => {
    setDateOfBirth(e.target.value)
  }

  const handleSelectGender = (gender: string) => {
    setSelectedGender(gender)
  }

  const [selectedSports, setSelectedSports] = useState<string[]>([])

  const handleSelectSport = (sportName: string) => {
    setSelectedSports((prevSelected) => {
      if (prevSelected.includes(sportName)) {
        return prevSelected.filter((sport) => sport !== sportName)
      } else {
        return [...prevSelected, sportName]
      }
    })
  }

  console.log(selectedGender)

  const sampleSports: SportDTO[] = [
    {
      name: 'Badminton',
      emoji: '🏸',
      backgroundUImageURL: './assets/sport-component-badminton.png',
    },
    {
      name: 'Tennis',
      emoji: '🎾',
      backgroundUImageURL: './assets/sport-component-tennis.png',
    },
    {
      name: 'Boxing',
      emoji: '🥊',
      backgroundUImageURL: './assets/sport-component-boxing.png',
    },
    {
      name: 'Table Tennis',
      emoji: '🏓',
      backgroundUImageURL: './assets/sport-component-table-tennis.png',
    },
    {
      name: 'Squash',
      emoji: '🥎',
      backgroundUImageURL: './assets/sport-component-squash.png',
    },
  ]

  return (
    <>
      <div className="row">
        <div className="col">
          <form action="">
            <div className="row user-input">
              <div className="col">
                <input
                  type="text"
                  name="username"
                  placeholder="Enter Username"
                  autoComplete="username"
                  onChange={handleUsernameChange}
                />
                <input
                  type="text"
                  name="dateOfBirth"
                  placeholder="Enter Date of Birth (dd-MM-yyyy)"
                  onChange={handleDateOfBirth}
                />
              </div>
            </div>
            <div className="row gender">
              <p>Select your gender</p>
              <div
                className="col d-flex justify-content-center"
                onClick={() => handleSelectGender('male')}
              >
                <label
                  className={`gender-card ${selectedGender === 'male' ? 'selected-gen' : 'unselected-gen'}`}
                >
                  <input
                    type="radio"
                    name="gender"
                    value="male"
                    checked={selectedGender === 'male'}
                    onChange={() => handleSelectGender('male')}
                  />
                  <img src="../pictures/man.svg" alt="man" />
                  <p>Male</p>
                </label>
              </div>
              <div
                className="col d-flex justify-content-center"
                onClick={() => handleSelectGender('female')}
              >
                <label
                  className={`gender-card ${selectedGender === 'female' ? 'selected-gen' : 'unselected-gen'}`}
                >
                  <input
                    type="radio"
                    name="gender"
                    value="female"
                    checked={selectedGender === 'female'}
                    onChange={() => handleSelectGender('female')}
                  />
                  <img src="../pictures/woman.svg" alt="man" />
                  <p>Female</p>
                </label>
              </div>
            </div>
            <div className="row sports">
              <div className="col">
                <p>Choose your sports</p>
              </div>
            </div>
            <div className="row sport-btn row-cols-3 gy-2 gx-2">
              {sampleSports.map((sport, index) => (
                <div className="col" key={index}>
                  <Sport
                    key={sport.name}
                    emoji={sport.emoji}
                    name={sport.name}
                    selected={
                      sport.name !== undefined &&
                      selectedSports.includes(sport.name)
                    }
                    onSelect={() =>
                      sport.name !== undefined && handleSelectSport(sport.name)
                    }
                  />
                </div>
              ))}
            </div>
            <input className="submit" type="submit" value="Save profile" />
          </form>
        </div>
      </div>
    </>
  )
}
