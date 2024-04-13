import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import LoadingSpinner from '../components/LoadingSpinner'
import { SearchBar } from '../components/SearchBar'
import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import '../styles/Index.css'
import {
  ApiError,
  EventDTO,
  EventsControllerService,
  ExSecuredEndpointService,
  OpenAPI,
  RequestEventDTO,
  SportDTO,
} from '../generated/api'
import useModal from '../hooks/UseModal'
import Modal from '../components/Modal'
import JoinEventComponent from '../components/JoinEventComponent'

export default function MainPage() {
  const [searchQuery, setSearchQuery] = useState<string>('') // no implementation yet
  const [filteredEvent, setFilteredEvent] = useState<EventDTO[]>([])
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [clearFilters, setClearFilters] = useState<boolean>(false)
  const [selectedEvent, setSelectedEvent] = useState<EventDTO>()
  const [usersRank, setUsersRank] = useState(0)
  const [userIsInRank, setUserIsInRank] = useState(false)
  const location = useLocation()
  const navigate = useNavigate()
  const { isOpen, toggle } = useModal()

  // handle sports name selected from sportButtoncomponent
  const handleSportSelectionChange = (selectedButtonSports: string[]) => {
    setSelectedSports(selectedButtonSports)
  }

  // handles sports name from location.state of allSportsList
  useEffect(() => {
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

  useEffect(() => {
    const fetchData = async () => {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      try {
        const requestEventDTO: RequestEventDTO = {
          sportsName: selectedSports,
        }
        const response = await EventsControllerService.getNearbyEvents(
          requestEventDTO,
          0,
          20,
          undefined,
        )
        if (!Array.isArray(response)) {
          throw new Error('Failed to fetch event data')
        }
        const data: EventDTO[] = response as EventDTO[]
        // set filtered events based on api response
        console.log(data)
        setFilteredEvent(data)
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    // call the method
    fetchData()
  }, [selectedSports])

  // handle join event pop up after cliking on the event
  const handleEventSelection = (e: EventDTO) => {
    window.scrollTo(0, 0)
    if (isOpen) {
      toggle()
    }
    setSelectedEvent(e)
    if (usersRank >= e.minElo && usersRank <= e.maxElo) {
      setUserIsInRank(true)
    } else {
      setUserIsInRank(false)
    }
    toggle()
  }

  console.log(`all sport selected:`, location.state)
  console.log(`sport button selected:`, selectedSports)
  console.log(`query`, searchQuery)

  // retrieving users rank
  useEffect(() => {
    const fetchUsersRank = async () => {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      try {
        const response = await ExSecuredEndpointService.getUserMainPage()
        if (response) {
          setUsersRank(response.elo)
        }
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    fetchUsersRank()
  })

  const handleLetsPlay = () => {
    navigate('/app')
  }

  return (
    <>
      <div className="container-fluid">
        <div className={'row'}>
          <div className="slide">
            <div className="slide-content">
              <h2>Find, Match, Play Now!</h2>
              <p>Your Next Challenge Awaits.</p>
              <label htmlFor="play-btn"></label>
              <button onClick={handleLetsPlay}>Let’s play!</button>
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
        <Modal isOpen={isOpen} toggle={toggle} preventClosing={true}>
          <JoinEventComponent
            toggle={toggle}
            isInRank={userIsInRank}
            event={selectedEvent!}
          />
        </Modal>
        <div className="row">
          <div className="col">
            <div className="nearby-events-container">
              {filteredEvent.length === 0 ? (
                <LoadingSpinner />
              ) : (
                filteredEvent.map((event, index) => (
                  <div
                    className="nearby-events"
                    key={index}
                    onClick={() => handleEventSelection(event)}
                  >
                    <SportEvent event={event} />
                  </div>
                ))
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
