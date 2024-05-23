import { useEffect, useState } from 'react'
import {
  OpenAPI,
  EventsControllerService,
  ApiError,
  EventDTO,
  ExSecuredEndpointService,
  UserDTO,
} from '../generated/api'
import '../styles/JoinEvent.css'
import { Link } from 'react-router-dom'

interface JoinEventProps {
  event: EventDTO
  toggle: () => void
}

export default function JoinEventComponent(p: JoinEventProps) {
  const handleJoinEvent = async () => {
    try {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      await EventsControllerService.joinEvent(p.event.id as number)
      p.toggle()
    } catch (error) {
      console.log(error as ApiError)
      p.toggle()
    }
  }

  const [userIsInRank, setUserIsInRank] = useState(false)
  const [currentUser, setCurrentUser] = useState<UserDTO>({})

  // retrieving users rank
  useEffect(() => {
    if (localStorage.getItem('token')) {
      const fetchUsersRank = async () => {
        OpenAPI.TOKEN = localStorage.getItem('token')!
        try {
          const response = await ExSecuredEndpointService.getUserMainPage()
          if (response) {
            setCurrentUser(response as UserDTO)
          }
        } catch (error) {
          console.error(error as ApiError)
        }
      }
      fetchUsersRank()
    }
  }, [])

  // checking user's rank
  useEffect(() => {
    if (
      currentUser.elo! >= p.event.minElo &&
      currentUser.elo! <= p.event.maxElo
    ) {
      setUserIsInRank(true)
    } else {
      setUserIsInRank(false)
    }
  }, [currentUser, p.event.minElo, p.event.maxElo])

  const getDateAndTime = (type: string) => {
    const dateStart: string[] = p.event.dateStart.split(' ')
    if (type === 'date') {
      return dateStart[0]
    } else if (type === 'time') {
      return dateStart[1]
    } else {
      return null
    }
  }

  return (
    <>
      {userIsInRank ? (
        <div className="container-sm join-event-wrapper">
          <div className="row">
            <div className="col-12">
              <h1 className="joint-event-title">Are you sure?</h1>
            </div>
          </div>
          <div className="row">
            <div className="col-12">
              <p className="text-center">
                You want to join <b>{p.event.sport}</b>
                {' at ' +
                  p.event.placeDTO?.name +
                  ' on ' +
                  getDateAndTime('date') +
                  ', at ' +
                  getDateAndTime('time') +
                  '?'}
              </p>
            </div>
          </div>
          <div className="row">
            <div className="col">
              <div className="row">
                <div className="col-12">
                  <button
                    className="join-event-btn-orange"
                    onClick={handleJoinEvent}
                  >
                    Join
                  </button>
                </div>
              </div>
              <div className="row">
                <div className="col-12">
                  <button className="join-event-btn-grey" onClick={p.toggle}>
                    Cancel
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : currentUser.name ? (
        <div className="container-sm join-event-wrapper">
          <div className="row">
            <div className="col-12">
              <h1 className="joint-event-title">Hang On a Sec!</h1>
            </div>
            <div className="row">
              <div className="col-12">
                <p className="join-event-message">
                  Your rank is too low/high to join <b>{p.event.sport}</b> at{' '}
                  {p.event.placeDTO?.name}
                </p>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-12">
              <button className="join-event-btn-grey" onClick={p.toggle}>
                Look for another
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div className="container-sm join-event-wrapper">
          <div className="row">
            <div className="col-12">
              <h1 className="joint-event-title">Hang On a Sec!</h1>
            </div>
            <div className="row">
              <div className="col-12">
                <Link to="/login">Please login to join</Link>
              </div>
            </div>
          </div>
          <div className="row">
            <div className="col-12">
              <button className="join-event-btn-grey" onClick={p.toggle}>
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  )
}
