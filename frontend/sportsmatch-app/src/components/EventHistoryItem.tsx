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
            <div className="user-avatar">
              <Avatar
                src="C:\Users\Johna\IdeaProjects\untitled\greenfox\sportsmatch\pictures\jeffrey-keenan-pUhxoSapPFA-unsplash.jpg"
                alt="User"
              />
            </div>
            <div className="user-score">{eventHistoryDTO.userScore}</div>
            <div className="user-name">You</div>
          </div>
          <div className="colon">
            <p>:</p>
          </div>
          <div className="opponent-side">
            <div className="opponent-score">
              {eventHistoryDTO.opponentScore}
            </div>
            <div className="opponent-avatar">
              <Avatar
                src="C:\Users\Johna\IdeaProjects\untitled\greenfox\sportsmatch\pictures\michael-dam-mEZ3PoFGs_k-unsplash.jpg"
                alt="Opponent"
              />
            </div>
            <div className="opponent-name">
              {eventHistoryDTO.opponent?.name}
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default EventHistoryItem
