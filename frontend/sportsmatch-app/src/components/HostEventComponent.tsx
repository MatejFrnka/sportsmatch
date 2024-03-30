import { FormEvent, useEffect, useState } from 'react'
import '../styles/HostEventComponent.css'
import {
  PlaceControllerService,
  SportControllerService,
  SportDTO,
  PlaceDTO,
  EventsControllerService,
  EventDTO,
} from '../generated/api'

function HostEventComponent() {
  const [matchTitle, setMatchTitle] = useState('')
  const [selectSport, setSelectedSport] = useState('')
  const [sportsOptions, setSportsOptions] = useState<SportDTO[]>([])
  const [selectRank, setSelectedRank] = useState('')
  const rankOptions = Array.from({ length: 20 }, (_, index) => ({
    value: index * 500,
    label: `${index * 500 + 1} - ${(index + 1) * 500}`,
  }))
  const [selectOppGender, setSelectedOppGender] = useState('')
  const genderOptions = ['Male', 'Female']
  const [selectLocation, setSelectedLocation] = useState('')
  const [locationsOptions, setLocationOptions] = useState<PlaceDTO[]>([])

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
  }, [SportControllerService, PlaceControllerService])

  const handleHostEvent = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    const event: EventDTO = {
      dateStart: string;
      dateEnd: string;
      location: string;
      minElo: number;
      maxElo: number;
      title: string;
      player1Id?: number;
      sport: string;
  };
    EventsControllerService.addEvent(event)

  //   const formData = {
  //     matchTitle,
  //     selectSport,
  //     selectRank,
  //     selectOppGender,
  //     selectLocation,
  //     selectDateAndTimeStart
  //     selectDateAndTimeEnd
  //   }

  //   try {
  //     const response = await fetch('/api/v1/event/', {
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //       },
  //       body: JSON.stringify(formData),
  //     })
  //     if (!response.ok) {
  //       throw new Error('Failed to create event')
  //     }
  //     console.log('Event created successfully!')
  //   } catch (error) {
  //     console.error('Error creating event: ', error)
  //   }
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
    setSelectedLocation(event.target.value)
  }

  return (
    <div className={'centered-container'}>
      <div className="wrapper-host-event">
        <form className="form-host-event" onSubmit={handleHostEvent}>
          <div className="host-event-input-box">
            <label htmlFor="match-title"></label>
            <input
              type="text"
              placeholder="Match Title"
              value={matchTitle}
              onChange={(e) => setMatchTitle(e.target.value)}
              required
            />
          </div>
          <div className="host-event-input-box">
            <label htmlFor="sports"></label>
            <select value={selectSport} onChange={handleSportSelection}>
              <option value="" disabled>Select sport</option>
              {sportsOptions.map((sport, index) => (
                <option key={index} value={sport.name}>
                  {sport.name}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="rank"></label>
            <select value={selectRank} onChange={handleRankSelection}>
              <option value="" disabled>Select a lowest rank</option>
              {rankOptions.map((option, index) => (
                <option key={index} value={option.value}>
                  {option.label}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="gender"></label>
            <select value={selectOppGender} onChange={handleOppGenderSelection}>
              <option value="" disabled>Select opponent gender</option>
              {genderOptions.map((gender, index) => (
                <option key={index} value={gender}>
                  {gender}
                </option>
              ))}
            </select>
          </div>
          <div className="host-event-input-box">
            <label htmlFor="location"></label>
            <select value={selectLocation} onChange={handleLocationSelection}>
              <option value="" disabled>Select location</option>
              {locationsOptions.map((location, index) => (
                <option key={index} value={location.name}>
                  {location.name}
                </option>
              ))}
            </select>
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
