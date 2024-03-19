import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import { SearchBar } from '../components/SearchBar'
import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import '../styles/Index.css'
import {
  ApiError,
  EventDTO,
  EventsControllerService,
  OpenAPI,
  RequestEventDTO,
  SportDTO,
} from '../generated/api'

export default function MainPage() {
  const [searchQuery, setSearchQuery] = useState<string>('') // no implementation yet
  const [filteredEvent, setFilteredEvent] = useState<EventDTO[]>([])
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [clearFilters, setClearFilters] = useState<boolean>(false)
  const location = useLocation()
  const navigate = useNavigate()

  const handleSportSelectionChange = (selectedButtonSports: string[]) => {
    setSelectedSports(selectedButtonSports)
  }

  useEffect(() => { // trigers when location.state change
    if (location.state) {
      const sports: SportDTO[] = location.state
      const sportNames = sports.map((sport) => sport.name || '')
      setSelectedSports(sportNames)
    }
  }, [location.state])

  const clear = () => {
    navigate(location.pathname, { state: undefined })
    setSelectedSports([])
    setClearFilters(true)
  }

  useEffect(() => { // trigers when selectedSports changed
    const fetchData = async () => {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      try {
        const requestEventDTO: RequestEventDTO = {
          sportsName: selectedSports,
        }
        const response =
          await EventsControllerService.getNearbyEvents(requestEventDTO)
        if (!Array.isArray(response) && response.length > 0) {
          throw new Error('Failed to fetch event data')
        }
        const data: EventDTO[] = response as EventDTO[]
        setFilteredEvent(data) // set filtered events based on api response
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    fetchData() // call the method
  }, [selectedSports])

  console.log(location.state)
  console.log(selectedSports)

  return (
    <div className="container-fluid">
      <div className={'row'}>
        <div className="slide">
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <SportsButtonComponent
            onSportSelectionChange={handleSportSelectionChange}
            clearFilters={clearFilters}
          />
        </div>
      </div>
      <div className="row clear">
        <div className="col">
          <button onClick={clear}>Clear Filter</button>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <p className="mainPage-p">Nearby</p>
        </div>
      </div>
      <div className="row">
        <div className="col search">
          <SearchBar
            onChange={(query: string) => {
              setSearchQuery(query)
            }}
          />
        </div>
      </div>
      <div className="row">
        <div className="col">
          <div className="nearby-events-container">
            {filteredEvent.map((event, index) => (
              <div className="nearby-events" key={index}>
                <SportEvent event={event} />
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
