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
import { EventDTO } from '../generated/api/models/EventDTO'
import { useEffect, useState } from 'react'
import { ExSecuredEndpointService, OpenAPI, UserDTO } from '../generated/api'

interface InProgressProps {
  event: EventDTO
}

function InProgress({ event }: InProgressProps) {
  const [currentUser, setCurrentUser] = useState<UserDTO>()
  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const fetchUserInfo = async () => {
      setCurrentUser(
        (await ExSecuredEndpointService.getUserMainPage()) as UserDTO,
      )
    }
    fetchUserInfo()
  }, [])

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
              {event.player2Id === null ? (
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
                  {event.player2Id === null
                    ? 'Awaiting opponent...'
                    : event.player1Name === currentUser?.name
                      ? event.player2Name
                      : event.player1Name}
                </li>
                <li>
                  <LuMapPin />
                  {event.placeDTO?.name}
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
