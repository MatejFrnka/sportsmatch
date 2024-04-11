import { FormEvent, useEffect, useState } from 'react'
import '../styles/HostEventComponent.css'
import {
  PlaceControllerService,
  SportControllerService,
  SportDTO,
  PlaceDTO,
  EventsControllerService,
  HostEventDTO,
} from '../generated/api'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'
import { format } from 'date-fns'
import { useNavigate } from 'react-router-dom'

function HostEventComponent() {
  const [matchTitle, setMatchTitle] = useState('')
  const [selectSport, setSelectedSport] = useState('')
  const [sportsOptions, setSportsOptions] = useState<SportDTO[]>([])
  const [selectRank, setSelectedRank] = useState('')
  const rankOptions = Array.from({ length: 20 }, (_, index) => ({
    value: index * 500 + 1,
    label: `${index * 500 + 1} - ${(index + 1) * 500}`,
  }))
  const [selectOppGender, setSelectedOppGender] = useState('')
  const genderOptions = ['Male', 'Female']
  const [selectLocation, setSelectedLocation] = useState<number>()
  const [locationsOptions, setLocationOptions] = useState<PlaceDTO[]>([])
  const [selectStartDateAndTime, setStartDateAndTime] = useState<Date | null>(
    null,
  )
  const [selectEndDateAndTime, setEndDateAndTime] = useState<Date | null>(null)
  const navigate = useNavigate()

  useEffect(() => {
    SportControllerService.getSports({
      page: 1,
      size: 1000,
    }).then((response) => {
      setSportsOptions(response)
    })
    PlaceControllerService.searchPlaces('').then((response) =>
      setLocationOptions(response),
    )
  })

  const handleHostEvent = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    console.log(selectLocation)

    const [minElo, maxElo] = selectRank
      .split(' - ')
      .map((str) => parseInt(str.trim(), 10))

    const formattedStartDate = selectStartDateAndTime
      ? format(selectStartDateAndTime, "yyyy-MM-dd'T'HH:mm:ss")
      : ''
    const formattedEndDate = selectEndDateAndTime
      ? format(selectEndDateAndTime, "yyyy-MM-dd'T'HH:mm:ss")
      : ''
    const event: HostEventDTO = {
      dateStart: formattedStartDate,
      dateEnd: formattedEndDate,
      locationId: selectLocation,
      minElo: minElo,
      maxElo: maxElo,
      title: matchTitle,
      sport: selectSport,
    }
    EventsControllerService.addEvent(event).then((response) => {
      navigate('/app')
    })
  }

  const handleSportSelection = (
    event: React.ChangeEvent<HTMLSelectElement>,
  ) => {
    setSelectedSport(event.target.value)
  }
  const handleRankSelection = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedRank(event.target.value)
  }
  const handleOppGenderSelection = (
    event: React.ChangeEvent<HTMLSelectElement>,
  ) => {
    setSelectedOppGender(event.target.value)
  }
  const handleLocationSelection = (
    event: React.ChangeEvent<HTMLSelectElement>,
  ) => {
    setSelectedLocation(parseInt(event.target.value))
  }
  const handleStartDateSelection = (date: Date | null) => {
    setStartDateAndTime(date)
  }
  const handleEndDateSelection = (date: Date | null) => {
    setEndDateAndTime(date)
  }

  return (
    <div className={'centered-container'}>
      <div className="wrapper-host-event">
        <form className="form-host-event" onSubmit={handleHostEvent}>
          <div className="host-event-input-box">
            <label htmlFor="match-title"></label>
            <input
              id="match-title"
              type="text"
              placeholder="Match Title"
              value={matchTitle}
              onChange={(e) => setMatchTitle(e.target.value)}
              required
            />
          </div>
          <div className="host-event-input-box">
            <label htmlFor="sports"></label>
            <select
              id="sports"
              value={selectSport}
              onChange={handleSportSelection}
            >
              <option value="" disabled>
                Select sport
              </option>
              {sportsOptions.map((sport, index) => (
                <option key={index} value={sport.name}>
                  {sport.name}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="rank"></label>
            <select id="rank" value={selectRank} onChange={handleRankSelection}>
              <option value="" disabled>
                Select a rank
              </option>
              {rankOptions.map((option, index) => (
                <option key={index} value={option.label}>
                  {option.label}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="gender"></label>
            <select
              id="gender"
              value={selectOppGender}
              onChange={handleOppGenderSelection}
            >
              <option value="" disabled>
                Select opponent gender
              </option>
              {genderOptions.map((gender, index) => (
                <option key={index} value={gender}>
                  {gender}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="location"></label>
            <select
              id="location"
              value={selectLocation}
              onChange={handleLocationSelection}
            >
              <option value="" disabled selected>
                Select location
              </option>
              {locationsOptions.map((location, index) => (
                <option key={index} value={location.id}>
                  {location.name}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <div className="date-picker">
              <label htmlFor="date-start"></label>
              <DatePicker
                id="date-start"
                placeholderText="Starting date"
                selected={selectStartDateAndTime}
                onChange={handleStartDateSelection}
                showTimeSelect
                dateFormat="yyyy-MM-dd HH:mm:ss"
              />
            </div>
          </div>
          <div className="host-event-input-box">
            <div className="date-picker">
              <label htmlFor="date-end"></label>
              <DatePicker
                id="date-end"
                placeholderText="Ending date"
                selected={selectEndDateAndTime}
                onChange={handleEndDateSelection}
                showTimeSelect
                dateFormat="yyyy-MM-dd HH:mm:ss"
              />
            </div>
          </div>
          <div className="create-event">
            <button type="submit">Host Event</button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default HostEventComponent
