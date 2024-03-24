import {
  OpenAPI,
  EventsControllerService,
  ApiError,
  EventDTO,
} from '../generated/api'
import '../styles/JoinEvent.css'
import '../styles/JoinEvent.css'

interface JoinEventProps {
  isInRank: boolean // a boolean to check if user's rank meets the event's requirement
  event: EventDTO
  toggle: () => void
}

export default function JoinEventComponent(p: JoinEventProps) {
  const handleJoinEvent = async () => {
    try {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      await EventsControllerService.joinEvent(p.event.id as number)
      p.toggle
    } catch (error) {
      console.error(error as ApiError)
    }
  }

  const date = new Date(
    parseInt(p.event.dateStart[0]),
    parseInt(p.event.dateStart[1]),
    parseInt(p.event.dateStart[2]),
  )

  const eventTime = () => {
    const hour = p.event.dateStart[3]
    const min = p.event.dateStart[4] == '0' ? '00' : p.event.dateStart[4]
    return hour + ':' + min
  }
  return (
    <>
      {p.isInRank ? (
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
                  p.event.location +
                  ' on ' +
                  date.toLocaleDateString('en', { month: 'long' }) +
                  ' ' +
                  date.getDay() +
                  ', ' +
                  date.getFullYear() +
                  ', at ' +
                  eventTime() +
                  '?'}
              </p>
            </div>
          </div>
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
      ) : (
        <div className="container-sm join-event-wrapper">
          <div className="row">
            <div className="col-12">
              <h1 className="joint-event-title">Hang On a Sec!</h1>
            </div>
            <div className="row">
              <div className="col-12">
                <p className="join-event-message">
                  Your rank is too low/high to join <b>{p.event.sport}</b> at{' '}
                  {p.event.location}
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
      )}
    </>
  )
}
