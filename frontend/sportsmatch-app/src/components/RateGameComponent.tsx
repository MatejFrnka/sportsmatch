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

interface Props {
  toggle: () => void
}

export default function RateGameComponent(p: Props) {
  const [myEvent, setMyEvent] = useState<EventDTO>()

  useEffect(() => {
    const init = async () => {
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
    }
    init()
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

  const [userScore, setUserScore] = useState(0)
  const [opponentScore, setOpponentScore] = useState(0)
  const [userTextRating, setUserTextRating] = useState('')

  const [matchRating, setMatchRating] = useState<number>()
  const [opponentRating, setOpponentRating] = useState<number>()

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    const rating: RatingDTO = {
      userTextRating: userTextRating,
      userStarRating: opponentRating as number,
      eventStarRating: matchRating as number,
      myScore: userScore,
      opponentScore: opponentScore,
    }
    console.log(rating)

    try {
      await RatingControllerService.addRating(rating)
      p.toggle()
    } catch (error) {
      console.log(error as ApiError)
      p.toggle()
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
          <h1 className="rate-game-label">How was your game?</h1>
        </div>
        <div className="row">
          <div className="col event-details">
            {myEvent ? (
              <>
                <span>
                  {dayOfTheWeek(
                    parseInt(myEvent!.dateStart[0]!),
                    parseInt(myEvent!.dateStart[1]!),
                    parseInt(myEvent!.dateStart[2]!),
                  )}
                  , {myEvent!.dateStart[3]!}:{myEvent!.dateStart[4]} -{' '}
                  {myEvent!.dateEnd[3]!}:{myEvent!.dateEnd[4]}
                </span>
                <br />
                <span>{myEvent ? myEvent?.location : ''}</span>{' '}
              </>
            ) : (
              <div></div>
            )}
          </div>
        </div>
        <div className="row">
          <div className="col">
            <p className="score-input-label">Enter the scores</p>
          </div>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="row">
            <div className="score-input-panel">
              <div className="user-picture">
                <Avatar src={userProfilePicture} />
              </div>
              <div className="user-score-rating">
                <div className="score-input-user">
                  <input
                    type="number"
                    className="form-control"
                    id="user-score"
                    placeholder="?"
                    onChange={handleUserScoreChange}
                    required
                  />
                </div>
              </div>
              <div className="divider">:</div>
              <div className="opponent-score-rating">
                <div className="score-input-user">
                  <input
                    type="number"
                    className="form-control"
                    id="opponent-score"
                    placeholder="?"
                    onChange={handleOpponentScoreChange}
                    required
                  />
                </div>
              </div>
              <div className="opponent-picture">
                <Avatar src={opponentProfilePicture} />
              </div>
              <div className="user-name">you</div>
              <div className="opponent-name">
                {myEvent ? myEvent.player2Name : ''}
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
          <div className="row">
            <p className="textarea-label">
              Your experience with {myEvent ? myEvent.player2Name : ''}
            </p>
          </div>
          <div className="row">
            <div className="col-12">
              <textarea
                id="text-user-rating"
                value={userTextRating}
                rows={5}
                cols={35}
                onChange={(e) => setUserTextRating(e.target.value)}
              />
            </div>
          </div>
          <button type="submit">Submit</button>
        </form>
      </div>
    </>
  )
}
