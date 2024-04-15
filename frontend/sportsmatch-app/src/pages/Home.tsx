import Match from '../components/Match'
import EventHistoryItem from '../components/EventHistoryItem'
import { useEffect, useState } from 'react'
import {
  EventDTO,
  EventHistoryDTO,
  EventsControllerService,
  OpenAPI,
} from '../generated/api'
import { Link } from 'react-router-dom'
import '../styles/UserPage.css'

function Home() {
  const [eventsHistory, setEventsHistory] = useState<EventHistoryDTO[]>([])
  const [upcomingMatch, setUpcomingMatch] = useState<EventDTO[]>([])

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const fetchUpcomingMatch = async () => {
      setUpcomingMatch(await EventsControllerService.getUpcomingMatches())
    }
    fetchUpcomingMatch()
  }, [])

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const fetchEvents = async () => {
      const response = await EventsControllerService.getEventsHistory(0, 3, [
        'event.dateEnd,desc',
      ])
      if (response && response.length > 0) {
        setEventsHistory(response)
      }
    }
    fetchEvents()
  }, [])

  console.log(eventsHistory)
  console.log(upcomingMatch)
  return (
    <>
      <div className="container-sm">
        <div className="user-page">
          {upcomingMatch.length === 0 ? <hr /> : <></>}
          <div className="row">
            {upcomingMatch.length === 0 ? (
              <div className="match-wrapper">
                <div className="col">
                  <p>No upcoming match</p>
                  <Link to="#">Find or Host a Match</Link>
                </div>
              </div>
            ) : (
              <Match event={upcomingMatch[0]} />
            )}
          </div>
          <div className="view-wrapper">
            {upcomingMatch.length === 0 ? <hr /> : <></>}
            <div className="row">
              <div className="col">
                <p>History</p>
              </div>
              <div className="col view">
                <Link to="#">View all</Link>
              </div>
            </div>
          </div>
          <div className="history-wrapper">
            <div className="row">
              <div className="col">
                {eventsHistory.length === 0 ? (
                  <p>No match history</p>
                ) : (
                  eventsHistory.map((e, index) => (
                    <EventHistoryItem key={index} eventHistoryDTO={e} />
                  ))
                )}
              </div>
            </div>
          </div>
          <div className="nearby-wrapper">
            <p>Nearby</p>
          </div>
        </div>
      </div>
    </>
  )
}

export default Home
