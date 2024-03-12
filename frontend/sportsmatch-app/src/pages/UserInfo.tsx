import {
  OpenAPI,
  SportDTO,
  UserControllerService,
  UserInfoDTO,
} from '../generated/api'
import '../styles/UserInfo.css'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import Sport from '../components/Sport'
import { useNavigate } from 'react-router-dom'

export default function UserInfo() {
  const [selectedGender, setSelectedGender] = useState<string>('')
  const [username, setUsername] = useState<string>('')
  const [dateOfBirth, setDateOfBirth] = useState<string>('')
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [errorMessage, setErrorMessage] = useState('')
  const [isAlertVisible, setIsAlertVisible] = useState<boolean>(false)
  const navigate = useNavigate()

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

  useEffect(() => {
    setIsAlertVisible(errorMessage !== '')
  }, [errorMessage])

  const handleUsernameChange = (e: ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value)
  }

  const handleDateOfBirthChange = (e: ChangeEvent<HTMLInputElement>) => {
    const inputDate = e.target.value

    // Remove any non-numeric characters
    const numericValue = inputDate.replace(/\D/g, '')

    let formattedDate = ''
    if (numericValue.length > 0) {
      formattedDate += numericValue.slice(0, 2)
      if (numericValue.length > 2) {
        formattedDate += '-' + numericValue.slice(2, 4)
        if (numericValue.length > 4) {
          formattedDate += '-' + numericValue.slice(4, 8)
        }
      }
    }

    setDateOfBirth(formattedDate)
  }

  const handleSelectGender = (gender: string) => {
    setSelectedGender(gender)
  }

  const handleSelectSport = (sportName: string) => {
    setSelectedSports((prevSelected) => {
      if (prevSelected.includes(sportName)) {
        return prevSelected.filter((sport) => sport !== sportName)
      } else {
        return [...prevSelected, sportName]
      }
    })
  }

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    OpenAPI.TOKEN = localStorage.getItem('token')!

    try {
      // find selected sport in sampleSports (SportDTO[])
      const sportsDTO: SportDTO[] = selectedSports.map((sportName: string) => {
        const sportDTO: SportDTO | undefined = sampleSports.find(
          (sport: SportDTO) => sport.name === sportName,
        )
        if (!sportDTO) {
          throw new Error(`Sport with name ${sportName} not found`)
        }
        return sportDTO
      })

      const userInfoDTO: UserInfoDTO = {
        userName: username,
        dateOfBirth: dateOfBirth,
        gender: selectedGender,
        sports: sportsDTO,
      }

      const response = await UserControllerService.updateInfo(userInfoDTO)

      console.log('User info updated successfully', response)
      navigate('/')
    } catch (error) {
      console.error('Error updating user info:', error)
      setErrorMessage('Failed to update user info. Please try again.')
    }
  }

  console.log(Response)

  return (
    <>
      <div className="row">
        <div className="col">
          {isAlertVisible && (
            <div className="alert alert-danger" role="alert">
              {errorMessage}
            </div>
          )}
          <form onSubmit={handleSubmit}>
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
                  onChange={handleDateOfBirthChange}
                  value={dateOfBirth}
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
                  className={`gender-card 
                  ${selectedGender === 'male' ? 'selected-gen' : 'unselected-gen'}`}
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
                  className={`gender-card 
                  ${selectedGender === 'female' ? 'selected-gen' : 'unselected-gen'}`}
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
