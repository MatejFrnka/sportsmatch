import '../styles/Match.css'
import {
  LuSwords,
  LuMapPin,
  LuMedal,
  LuCalendarCheck,
  LuCalendarX,
  LuSettings2,
} from 'react-icons/lu'
import { Link } from 'react-router-dom'

interface InProgressProps {
  event: {
    id: number
    maxElo: number
    minElo: number
    dateEnd: string
    dateStart: string
    location: string
    title: string
    sport: string
    playerOne: string
    playerTwo?: string
  }
}

function InProgress({ event }: InProgressProps) {
  return (
    <>
      <div className="container-fluid">
        <div className="match">
          <div className="row">
            <div className="col position-relative">
              <Link
                to="/test/1"
                className="overlap position-absolute top-0 end-0"
              >
                <LuSettings2 />
              </Link>
              {event.playerTwo === null ? (
                <h1>
                  Matchmaking
                  <br /> in progress
                </h1>
              ) : (
                <h1>
                  Upcoming
                  <br /> match
                </h1>
              )}
              <ul>
                <li>
                  <LuSwords />{' '}
                  {event.playerTwo === null
                    ? 'Awaiting opponent...'
                    : event.playerTwo}
                </li>
                <li>
                  <LuMapPin />
                  {event.location}
                </li>
                <li>
                  <LuMedal />
                  {event.minElo} - {event.maxElo}
                </li>
                <li>
                  <LuCalendarCheck />
                  {event.dateStart}
                </li>
                <li>
                  <LuCalendarX />
                  {event.dateEnd}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default InProgress
