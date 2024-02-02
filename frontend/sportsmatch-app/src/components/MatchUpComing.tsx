import '../styles/MatchUpComing.css'

interface MatchUpComingProps {
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

function InProgress({ event }: MatchUpComingProps) {
  return (
    <>
      <div className="container-sm">
        <div className="upcoming">
          <div className="row">
            <div className="col position-relative">
              <a
                href="test/1"
                className="overlap position-absolute top-0 end-0"
              >
                <img src="./assets/Filter.png" alt="settings" />
              </a>
              <h1>
                Upcoming
                <br /> match
              </h1>
              <ul>
                <li>⚔️ {event.playerTwo}</li>
                <li>📍{event.location}</li>
                <li>
                  🏅{event.minElo} - {event.maxElo}
                </li>
                <li>
                  {'🟢' + event.dateStart} {'🔴' + event.dateEnd}
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
