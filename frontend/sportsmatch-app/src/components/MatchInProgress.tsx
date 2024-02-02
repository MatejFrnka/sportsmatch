import '../styles/MatchInProgress.css'

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
      <div className="container-sm">
        <div className="host">
          <div className="row">
            <div className="col position-relative">
              <a
                href="test/1"
                className="overlap position-absolute top-0 end-0"
              >
                <img src="./assets/Filter.png" alt="settings" />
              </a>
              <h1>
                Matchmaking
                <br /> in progress
              </h1>
              <ul>
                <li>⚔️ Awaiting opponent...</li>
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
