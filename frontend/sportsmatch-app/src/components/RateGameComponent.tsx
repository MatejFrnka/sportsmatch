import {
  ApiError,
  EventDTO,
  OpenAPI,
  RatingControllerService,
  RatingDTO,
} from '../generated/api'
import '../styles/RateGameComponent.css'
import Avatar from './Avatar'
import Rating from './Rating'
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'

export default function RateGameComponent() {
  const [myEvent, setMyEvent] = useState<EventDTO>()

  useEffect(() => {
    ;(async () => {
      OpenAPI.TOKEN = localStorage.getItem('token')!
      try {
        const response = await RatingControllerService.checkRating()
        setMyEvent(response[0] as EventDTO)
      } catch (error) {
        const code = (error as ApiError).status
        if (code == 401) {
          localStorage.removeItem('token')
        }
      }
    })()
  }, [])

  const dayOfTheWeek = (year: number, month: number, day: number) => {
    const daysOfTheWeek = [
      'Sunday',
      'Monday',
      'Tuesday',
      'Wednesday',
      'Thursday',
      'Friday',
      'Saturday',
    ]
    const date = new Date(year, month - 1, day)
    const dateIndex = date.getDay()
    return daysOfTheWeek[dateIndex]
  }

  const userProfilePicture = 'pictures/unknown-user-placeholder.png'
  const opponentProfilePicture = 'pictures/unknown-user-placeholder.png'

  const [userScore, setUserScore] = useState<number>()
  const [opponentScore, setOpponentScore] = useState<number>()

  const [matchRating, setMatchRating] = useState<number>(0)
  const [opponentRating, setOpponentRating] = useState<number>(0)

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    const rating: RatingDTO = {
      userTextRating: 'some rating',
      userStarRating: opponentRating,
      eventStarRating: matchRating,
      myScore: 0,
      opponentScore: 0,
    }
  }

  const handleUserScoreChange = (e: ChangeEvent<HTMLInputElement>) => {
    const value = parseInt(e.target.value) || 0
    setUserScore(value)
  }
  const handleOpponentScoreChange = (e: ChangeEvent<HTMLInputElement>) => {
    const value = parseInt(e.target.value) || 0
    setOpponentScore(value)
  }
  return (
    <>
      <div className="container-sm rate-game-window-wrapper">
        <div className="row">
          <h1>How was your game?</h1>
        </div>
        <div className="row">
          <span>
            {dayOfTheWeek(
              parseInt(myEvent!.dateStart[0]!),
              parseInt(myEvent!.dateStart[1]!),
              parseInt(myEvent!.dateStart[2]!),
            )}
            , {myEvent!.dateStart[3]!}:{myEvent!.dateStart[4]} -{' '}
            {myEvent!.dateEnd[3]!}:{myEvent!.dateEnd[4]}
          </span>
          <span>{myEvent?.location}</span>
        </div>
        <div className="row">
          <p>Enter the scores</p>
        </div>
        <form action="">
          <div className="row">
            <div className="score-input-panel">
              <div className="user-picture">
                <Avatar src={userProfilePicture} />
              </div>
              <div className="user-score">
                <div className="score-input-user">
                  <input
                    type="number"
                    className="form-control"
                    id="user-score"
                    value={userScore}
                    placeholder="?"
                    onChange={handleUserScoreChange}
                    required
                  />
                </div>
              </div>
              <div className="divider">:</div>
              <div className="opponent-score">
                <div className="score-input-user">
                  <input
                    type="number"
                    className="form-control"
                    id="opponent-score"
                    placeholder="?"
                    value={opponentScore}
                    onChange={handleOpponentScoreChange}
                    required
                  />
                </div>
              </div>
              <div className="opponent-picture">
                <Avatar src={opponentProfilePicture} />
              </div>
            </div>
          </div>
          <div className="row star-rating">
            <div className="col-8">
              <p className="star-rating-label">Was Match Balance</p>
              <p className="star-rating-label">Was your opponent nice</p>
            </div>
            <div className="col-4">
              <Rating
                count={5}
                value={0}
                size={15}
                edit={true}
                onChange={(value) => setMatchRating(value)}
              />
              <Rating
                count={5}
                value={0}
                size={15}
                edit={true}
                onChange={(value) => setOpponentRating(value)}
              />
            </div>
          </div>
        </form>
      </div>
    </>
  )
}
