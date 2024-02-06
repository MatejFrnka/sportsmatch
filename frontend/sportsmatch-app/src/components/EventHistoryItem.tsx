import {EventHistoryDTO} from '../generated/api'
import '../styles/EventHistoryItem.css'
import Avatar from './Avatar'

interface EventHistoryProps {
  eventHistoryDTO: EventHistoryDTO
}

function EventHistoryItem({eventHistoryDTO}: EventHistoryProps) {
  return (
    <>
      <div className="container-sm">
        <div className="event-history-item">
          <div className="match-status">VICTORY (uncorfirmed)</div>
          <div className="user-side">
            <div>
              <div className="user-avatar">
                <Avatar src="" alt="User"/>
              </div>
              <div className="user-name">You</div>
            </div>
            <div className="user-score">{eventHistoryDTO.userScore}</div>
          </div>

          <div className="colon">
            <p>:</p>
          </div>
          <div className="opponent-side">
            <div className="opponent-score">
              {eventHistoryDTO.opponentScore}
            </div>
            <div>
              <div className="opponent-avatar">
                <Avatar src="" alt="Opponent" />
              </div>
              <div className="opponent-name">
                {eventHistoryDTO.opponent?.name}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default EventHistoryItem
