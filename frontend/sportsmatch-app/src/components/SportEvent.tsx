import { EventDTO } from '../generated/api'
import '../styles/SportEvent.css'
import { LuMapPin, LuMedal, LuCalendarCheck, LuCalendarX } from 'react-icons/lu'

const formatDate = (dateArray: string) => {
  const [year, month, day, hours, minutes] = dateArray
  const formattedDate = `${day.toString().padStart(2, '0')}-${month
    .toString()
    .padStart(2, '0')}-${year} ${hours}:${minutes.toString().padStart(2, '0')}`
  return formattedDate
}

function SportEvent({ event }: { event: EventDTO }) {
  return (
    <>
      <div className="container-fluid">
        <div className="event">
          <div className="row">
            <div className="col left">
              <ul>
                <li data-testid="luMapPin">
                  <LuMapPin /> {event.placeDTO?.name}
                </li>
                <li data-testid="luMedal">
                  <LuMedal /> {event.minElo} - {event.maxElo}
                </li>
                <li data-testid="luCalendarCheck">
                  <LuCalendarCheck />
                  {formatDate(event.dateStart)}
                </li>
                <li data-testid="luCalendarX">
                  <LuCalendarX />
                  {formatDate(event.dateEnd)}
                </li>
              </ul>
            </div>
            <div className="col right">
              <h3>{event.title}</h3>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default SportEvent
