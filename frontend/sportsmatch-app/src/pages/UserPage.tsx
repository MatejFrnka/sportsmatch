import '../styles/UserPage.css'

export default function UserPage() {
  return (
    <>
      <div className="container-sm">
        <div className="user-page">
          <div className="row">
            <hr />
            <div className="match-wrapper">
              <div className="col">
                <p>No upcoming match</p>
                <a href="#">Find or Host a Match</a>
              </div>
            </div>
          </div>
          <div className="view-wrapper">
            <div className="row">
              <hr />
              <div className="col">
                <p>History</p>
              </div>
              <div className="col view">
                <a href="#">View all</a>
              </div>
            </div>
          </div>
          <div className="history-wrapper">
            <div className="row">
              <div className="col">
                <p>No match history</p>
              </div>
            </div>
          </div>
          <div className="nearby-wrapper">
            <p>Nearby</p>
          </div>
          <div className="event-wrapper">
            <div className="row">
              <div className="col"></div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
