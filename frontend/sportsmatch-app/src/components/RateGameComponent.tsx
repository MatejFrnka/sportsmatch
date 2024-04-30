import {
  ApiError,
  EventDTO,
  ExSecuredEndpointService,
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
  const [opponentName, setOpponentName] = useState<string>('')

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const init = async () => {
      try {
        const response = await ExSecuredEndpointService.getUserMainPage()
        const userName = response.name as string
        if (myEvent && userName.includes(myEvent.player1Name!)) {
          setOpponentName(myEvent.player2Name!)
        } else if (myEvent) {
          setOpponentName(myEvent.player1Name!)
        }
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    init()
  })

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const init = async () => {
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

  const userProfilePicture = '/assets/unknown-user-placeholder.png'
  const opponentProfilePicture = '/assets/unknown-user-placeholder.png'

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
      eventId: myEvent!.id!,
    }

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

  const getDateAndTime = (type: string, date: string) => {
    const dateStart: string[] = date.split(' ')
    if (type === 'date') {
      return dateStart[0]
    } else if (type === 'time') {
      return dateStart[1]
    } else {
      return null
    }
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
                  {'on ' +
                    getDateAndTime('date', myEvent.dateStart) +
                    ' at ' +
                    getDateAndTime('time', myEvent.dateStart) +
                    ' - ' +
                    getDateAndTime('time', myEvent.dateEnd)}
                </span>
                <br />
                <span>{myEvent.placeDTO?.name}</span>{' '}
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
              <div className="user-name-score-panel">you</div>
              <div className="opponent-name-score-panel">{opponentName}</div>
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
              Your experience with {opponentName}
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
