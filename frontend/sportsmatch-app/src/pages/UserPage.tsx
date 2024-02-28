import '../styles/UserPage.css'
import Match from '../components/Match'
import EventHistoryItem from '../components/EventHistoryItem'
import SportEvent from '../components/SportEvent'
import { Link } from 'react-router-dom'

export default function UserPage() {
  const sampleUserWithEvent = {
    id: 1,
    username: `johndoe87`,
    event: {
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
  }

  const sampleAllEventsDTO = [
    {
      id: 1,
      name: 'Event 1',
      date: '2024-02-10',
      location: 'Location 1',
      userScore: 1,
      opponentScore: 2,
    },
    {
      id: 2,
      name: 'Event 1',
      date: '2024-02-10',
      location: 'Location 1',
      userScore: 1,
      opponentScore: 2,
    },
    {
      id: 3,
      name: 'Event 1',
      date: '2024-02-10',
      location: 'Location 1',
      userScore: 1,
      opponentScore: 2,
    },
  ]

  const { event } = sampleUserWithEvent

  return (
    <>
      <div className="container-sm">
        <div className="user-page">
          {event === null ? <hr /> : <></>}
          <div className="row">
            {event === null ? (
              <div className="match-wrapper">
                <div className="col">
                  <p>No upcoming match</p>
                  <Link to="#">Find or Host a Match</Link>
                </div>
              </div>
            ) : (
              <Match event={event} />
            )}
          </div>
          <div className="view-wrapper">
            {event === null ? <hr /> : <></>}
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
                {sampleAllEventsDTO === null ? (
                  <p>No match history</p>
                ) : (
                  sampleAllEventsDTO.map((e) => (
                    <EventHistoryItem key={e.id} eventHistoryDTO={e} />
                  ))
                )}
              </div>
            </div>
          </div>
          <div className="nearby-wrapper">
            <p>Nearby</p>
          </div>
          <SportEvent event={sampleUserWithEvent.event} />
          <SportEvent event={sampleUserWithEvent.event} />
        </div>
      </div>
    </>
  )
}
