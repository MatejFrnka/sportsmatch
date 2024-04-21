import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import LoadingSpinner from '../components/LoadingSpinner'
import { SearchBar } from '../components/SearchBar'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../styles/Index.css'
import {
  ApiError,
  EventDTO,
  EventsControllerService,
  ExSecuredEndpointService,
  OpenAPI,
} from '../generated/api'
import useModal from '../hooks/UseModal'
import Modal from '../components/Modal'
import JoinEventComponent from '../components/JoinEventComponent'

export default function MainPage() {
  const [searchQuery, setSearchQuery] = useState<string>('')
  const [filteredEvent, setFilteredEvent] = useState<EventDTO[]>([])
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [clearFilters, setClearFilters] = useState<boolean>(false)
  const [selectedEvent, setSelectedEvent] = useState<EventDTO>()
  const [usersRank, setUsersRank] = useState(0)
  const [userIsInRank, setUserIsInRank] = useState(false)
  const navigate = useNavigate()
  const { isOpen, toggle } = useModal()
  const [page, setPage] = useState<number>(0)
  const size = 3

  // handle sports name selected from sportButtoncomponent
  const handleSportSelectionChange = (selectedButtonSports: string[]) => {
    setSelectedSports(selectedButtonSports)
  }

  const clear = () => {
    setPage(0)
    setFilteredEvent([])
    setSelectedSports([])
    setClearFilters(true)
    setTimeout(() => {
      setClearFilters(false)
    }, 100)
  }

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await EventsControllerService.getNearbyEvents(
          selectedSports,
          0,
          0,
          page,
          size,
        )
        if (!Array.isArray(response)) {
          throw new Error('Failed to fetch event data')
        }
        const data: EventDTO[] = response as EventDTO[]
        // set filtered events based on api response
        if (page === 0) {
          setFilteredEvent(data as EventDTO[])
        } else {
          setFilteredEvent((previousPage) => [
            ...previousPage,
            ...(data as EventDTO[]),
          ])
        }
        //setFilteredEvent(data as EventDTO[])
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    // call the method
    fetchData()
  }, [selectedSports, page])

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

  const loadMore = () => {
    const nextPage = page + 1
    setPage(nextPage)
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
              placeholder="Find your place"
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
                filteredEvent
                  .filter((e) =>
                    e.placeDTO.name
                      .toLowerCase()
                      .includes(searchQuery.toLowerCase()),
                  )
                  .map((event, index) => (
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
        {filteredEvent.length > 0 && (
          <div className="row load-btn">
            <div className="col">
              <button onClick={loadMore}>Load More Events</button>
            </div>
          </div>
        )}
      </div>
    </>
  )
}
