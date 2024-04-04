import { useEffect, useState } from 'react'
import { EventHistoryDTO } from '../generated/api'
import '../styles/EventHistoryItem.css'
import Avatar from './Avatar'

interface EventHistoryProps {
  eventHistoryDTO: EventHistoryDTO
}

function EventHistoryItem({ eventHistoryDTO }: EventHistoryProps) {
  const [eventStatus, setEventStatus] = useState<string>('')

  useEffect(() => {
    const matchResult =
      eventHistoryDTO.userScore! > eventHistoryDTO.opponentScore!
        ? 'VICTORY'
        : eventHistoryDTO.userScore! == eventHistoryDTO.opponentScore!
          ? 'DRAW'
          : 'LOSS'
    if (eventHistoryDTO.status?.includes('MATCH')) {
      setEventStatus(matchResult)
    } else if (eventHistoryDTO.status?.includes('WAITING_FOR_RATING')) {
      const result = matchResult + '(UNCONFIRMED)'
      setEventStatus(result)
    } else if (eventHistoryDTO.status?.includes('MISMATCH')) {
      setEventStatus('SCORE MISMATCH')
    }
  }, [
    eventHistoryDTO.opponentScore,
    eventHistoryDTO.status,
    eventHistoryDTO.userScore,
  ])

  return (
    <>
      <div className="container-fluid">
        <div className={'row'}>
          <div className="event-history-item">
            <div className="match-status">{eventStatus}</div>
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
      </div>
    </>
  )
}

export default EventHistoryItem
