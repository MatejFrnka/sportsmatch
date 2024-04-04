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
  const size = 20

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userDataPromise = UserControllerService.getUser(userId)
        const summaryDataPromise = RatingControllerService.getSummary(userId)
        const ratingDataPromise = RatingControllerService.getAllByUser(
          userId,
          page,
          size,
        )

        const [userDataResponse, summaryResponse, ratingResponse] =
          await Promise.all([
            userDataPromise,
            summaryDataPromise,
            ratingDataPromise,
          ])

        const userData = userDataResponse as UserDTO
        const summaryData = summaryResponse as UserRatingStatsDTO
        const ratingData = ratingResponse as UserRatingDTO[]

        setUser(userData)
        setSummary(summaryData)

        if (page === 0) {
          setRatings(ratingData)
        } else {
          setRatings((previousPage) => [...previousPage, ...ratingData])
        }
      } catch (error) {
        console.error(error as ApiError)
      }
    }

    fetchData()
    // console.log('Current', page)
    // console.log('User', user)
    // console.log('Summary', summary)
    // console.log(ratings)
  }, [userId, page])

  const loadMore = () => {
    const nextPage = page + 1
    setPage(nextPage)
  }

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
