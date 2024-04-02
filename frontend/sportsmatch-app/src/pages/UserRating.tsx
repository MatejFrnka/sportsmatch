import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import {
  ApiError,
  RatingControllerService,
  UserControllerService,
  UserDTO,
  UserRatingDTO,
  UserRatingStatsDTO,
} from '../generated/api'
import RatingCard from '../components/RatingCard'
import UserCard from '../components/UserCard'
import '../styles/UserRating.css'

export default function UserRating() {
  const { id } = useParams<{ id: string }>()
  const userId = parseInt(id!, 10)
  const [user, setUser] = useState<UserDTO>()
  const [summary, setSummary] = useState<UserRatingStatsDTO>()
  const [ratings, setRatings] = useState<UserRatingDTO[]>([])
  const [page, setPage] = useState<number>(0)
  const size = 1

  useEffect(() => {
    fetchData()
    console.log('Ratings:', ratings)
  }, [page])

  const fetchData = async () => {
    try {
      // Fetch user data
      const userResponse = await UserControllerService.getUser(userId)
      const userData: UserDTO = userResponse as UserDTO
      setUser(userData)

      // Fetch summary data
      const summaryResponse = await RatingControllerService.getSummary(userId)
      const summaryData: UserRatingStatsDTO =
        summaryResponse as UserRatingStatsDTO
      setSummary(summaryData)

      // Fetch ratings data

      const ratingResponse = await RatingControllerService.getAllByUser(
        userId,
        page,
        size,
      )

      const ratingData: UserRatingDTO[] = ratingResponse as UserRatingDTO[]
      setRatings((previousPage) => [...previousPage, ...ratingData])
    } catch (error) {
      console.error(error as ApiError)
    }
  }

  const loadMore = () => {
    const nextPage = page + 1
    setPage(nextPage)
  }

  // console.log('Current Page:', page)
  // console.log('User', user)
  // console.log('Summary', summary)
  // todo update UserDTO add win and loss count

  return (
    <>
      <div className="row">
        <div className="col">
          {user && summary && <UserCard user={user} summary={summary} />}
        </div>
      </div>
      <div className="row">
        <div className="col">
          <div className="row">
            <div className="col">
              {ratings.length === 0 ? (
                <div className="row">
                  <div className="col no-ratings-msg">
                    <h2>no ratings available</h2>
                  </div>
                </div>
              ) : (
                ratings.map((rating, index) => (
                  <RatingCard rating={rating} key={index} />
                ))
              )}
            </div>
          </div>
          {ratings.length > 0 && (
            <div className="row load-btn">
              <div className="col">
                <button onClick={loadMore}>Load More</button>
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  )
}
