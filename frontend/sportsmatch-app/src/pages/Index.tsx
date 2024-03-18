import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import { SearchBar } from '../components/SearchBar'
import { useEffect, useMemo, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import '../styles/Index.css'

export default function MainPage() {
  interface Event {
    id: number
    maxElo: number
    minElo: number
    dateEnd: string
    dateStart: string
    location: string
    title: string
    sport: string
    playerOne: string
    playerTwo: string
  }

  const sampleEvents: Event[] = useMemo(
    () => [
      {
        id: 1,
        maxElo: 2000,
        minElo: 1200,
        dateEnd: '2024-05-02',
        dateStart: '2024-05-01',
        location: 'Prague, Stadium A',
        title: 'Badminton match',
        sport: 'Badminton',
        playerOne: 'johndoe87',
        playerTwo: 'jess_ward',
      },
      {
        id: 2,
        maxElo: 2000,
        minElo: 1200,
        dateEnd: '2024-05-02',
        dateStart: '2024-05-01',
        location: 'Prague, Stadium A',
        title: 'Boxing match',
        sport: 'Boxing',
        playerOne: 'johndoe87',
        playerTwo: 'jess_ward',
      },
      {
        id: 3,
        maxElo: 2000,
        minElo: 1200,
        dateEnd: '2024-05-02',
        dateStart: '2024-05-01',
        location: 'Prague, Stadium A',
        title: 'Table Tennis match',
        sport: 'Table Tennis',
        playerOne: 'johndoe87',
        playerTwo: 'jess_ward',
      },
      {
        id: 4,
        maxElo: 2000,
        minElo: 1200,
        dateEnd: '2024-05-02',
        dateStart: '2024-05-01',
        location: 'Prague, Stadium A',
        title: 'Squash match',
        sport: 'Squash',
        playerOne: 'johndoe87',
        playerTwo: 'jess_ward',
      },
      {
        id: 5,
        maxElo: 2000,
        minElo: 1200,
        dateEnd: '2024-05-02',
        dateStart: '2024-05-01',
        location: 'Prague, Stadium A',
        title: 'Tennis match',
        sport: 'Tennis',
        playerOne: 'johndoe87',
        playerTwo: 'jess_ward',
      },
    ],
    [],
  )

  const location = useLocation()
  const [searchQuery, setSearchQuery] = useState('') // no implementation yet
  const [filteredEvent, setFilteredEvent] = useState(sampleEvents)
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [clearFilters, setClearFilters] = useState(false)
  const navigate = useNavigate()

  const handleSportSelectionChange = (selectedSports: string[]) => {
    setSelectedSports(selectedSports)
  }

  const clear = () => {
    navigate(location.pathname, { state: undefined })
    setSelectedSports([])
    setClearFilters(true)
  }

  const filterBySelectedSports = (
    events: Event[],
    selectedSports: string[],
  ) => {
    if (selectedSports.length === 0) return events

    return events.filter((event) => selectedSports.includes(event.sport))
  }

  const filterByLocationState = (events: Event[], locationState: any) => {
    if (!locationState || !Array.isArray(locationState)) return events

    const sportNames = locationState.map((sport) => sport.name)
    return events.filter((event) => sportNames.includes(event.sport))
  }

  // handles the event filtering
  useEffect(() => {
    // using sample data should be replace from backend
    let filteredEvents = sampleEvents

    // Apply filters
    filteredEvents = filterBySelectedSports(filteredEvents, selectedSports)
    filteredEvents = filterByLocationState(filteredEvents, location.state)

    // Set filtered events
    setFilteredEvent(filteredEvents)
  }, [selectedSports, location.state, sampleEvents])

  console.log('selected sport: ', selectedSports)
  console.log('location state: ', location.state)
  console.log('clear state: ', clearFilters)
  console.log('query: ', searchQuery)

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
