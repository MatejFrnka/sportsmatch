import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import LoadingSpinner from '../components/LoadingSpinner'
import { SearchBar } from '../components/SearchBar'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../styles/Index.css'
import { ApiError, EventDTO, EventsControllerService } from '../generated/api'
import useModal from '../hooks/UseModal'
import Modal from '../components/Modal'
import JoinEventComponent from '../components/JoinEventComponent'

export default function MainPage() {
  const [searchQuery, setSearchQuery] = useState<string>('')
  const [filteredEvent, setFilteredEvent] = useState<EventDTO[]>([])
  const [selectedSports, setSelectedSports] = useState<string[]>([])
  const [clearFilters, setClearFilters] = useState<boolean>(false)
  const [selectedEvent, setSelectedEvent] = useState<EventDTO>()
  const navigate = useNavigate()
  const { isOpen, toggle } = useModal()
  const [page, setPage] = useState<number>(0)
  const size = 3

  // handle sports name selected from sportButtoncomponent
  const handleSportSelectionChange = (selectedButtonSports: string[]) => {
    setSelectedSports(selectedButtonSports)
  }

  // setting the page to 0 when selected sports change to clear the filteredEvent
  useEffect(() => {
    setPage(0)
  }, [selectedSports])

  const clear = () => {
    setPage(0)
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
          searchQuery,
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
  }, [selectedSports, page, searchQuery])

  // handle join event pop up after cliking on the event
  const handleEventSelection = (e: EventDTO) => {
    if (isOpen) {
      toggle()
    }
    setSelectedEvent(e)
    toggle()
  }

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
          <JoinEventComponent toggle={toggle} event={selectedEvent!} />
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
