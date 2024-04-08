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

  const eventDate = new Date(
    parseInt(event.dateStart[0]),
    parseInt(event.dateStart[1]),
    parseInt(event.dateStart[2]),
  )

  const eventStartTime = () => {
    const hour = event.dateStart[3]
    const min = event.dateStart[4] === '0' ? '00' : event.dateStart[4]
    return hour + ':' + (min.length === 1 ? min + '0' : min)
  }
  const eventEndTime = () => {
    const hour = event.dateEnd[3]
    const min = event.dateEnd[4] === '0' ? '00' : event.dateEnd[4]
    return hour + ':' + (min.length === 1 ? min + '0' : min)
  }
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
                  {eventDate.getDay() +
                    '.' +
                    eventDate.getMonth() +
                    '.' +
                    eventDate.getFullYear() +
                    ', ' +
                    eventStartTime()}
                </li>
                <li>
                  <LuCalendarX />
                  {eventDate.getDay() +
                    '.' +
                    eventDate.getMonth() +
                    '.' +
                    eventDate.getFullYear() +
                    ', ' +
                    eventEndTime()}
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
