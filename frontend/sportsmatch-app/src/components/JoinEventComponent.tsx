import {
  OpenAPI,
  EventsControllerService,
  ApiError,
  EventDTO,
} from '../generated/api'
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
      p.toggle()
    } catch (error) {
      console.log(error as ApiError)
      p.toggle()
    }
  }

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
      )}
    </>
  )
}
