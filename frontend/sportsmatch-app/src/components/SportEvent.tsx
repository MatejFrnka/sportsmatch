import { EventDTO } from '../generated/api'
import '../styles/SportEvent.css'
import { LuMapPin, LuMedal, LuCalendarCheck, LuCalendarX } from 'react-icons/lu'

// interface SportEventProps {
//   event: {
//     id: number
//     maxElo: number
//     minElo: number
//     dateEnd: string
//     dateStart: string
//     location: string
//     title: string
//     sport: string
//     playerOne: string
//     playerTwo?: string
//   }
// }

function SportEvent({ event }: { event: EventDTO }) {
  return (
    <>
      <div className="container-fluid">
        <div className="event">
          <div className="row">
            <div className="col left">
              <ul>
                <li data-testid="luMapPin">
                  <LuMapPin /> {event.location}
                </li>
                <li data-testid="luMedal">
                  <LuMedal /> {event.minElo} - {event.maxElo}
                </li>
                <li data-testid="luCalendarCheck">
                  <LuCalendarCheck />
                  {event.dateStart}
                </li>
                <li data-testid="luCalendarX">
                  <LuCalendarX />
                  {event.dateEnd}
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
