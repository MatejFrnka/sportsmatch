import { EventHistoryDTO } from '../generated/api'
import '../styles/EventHistoryItem.css'
import Avatar from './Avatar'

interface EventHistoryProps {
  eventHistoryDTO: EventHistoryDTO
}

function EventHistoryItem({ eventHistoryDTO }: EventHistoryProps) {
  return (
    <>
      <div className="container-sm">
        <div className="event-history-item">
          <div className="match-status">VICTORY (uncorfirmed)</div>
          <div className="user-side">
            <div className="profile">
              <div className="user-avatar">
                <Avatar src="pictures/michael-dam-mEZ3PoFGs_k-unsplash.jpg" />
              </div>
              <div className="user-name">You</div>
            </div>
            <div className="user-score score">
              <span>{eventHistoryDTO.userScore}</span>
            </div>
          </div>
          <div className="colon">
            <span>:</span>
          </div>
          <div className="opponent-side">
            <div className="opponent-score score">
              <span>{eventHistoryDTO.opponentScore}</span>
            </div>
            <div className="profile">
              <div className="opponent-avatar">
                <Avatar src="pictures/jeffrey-keenan-pUhxoSapPFA-unsplash.jpg" />
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
