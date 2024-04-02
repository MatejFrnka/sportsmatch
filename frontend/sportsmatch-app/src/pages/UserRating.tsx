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
        const ratingsResponse = await RatingControllerService.getAllByUser(
          userId,
          {
            page,
            size,
            sort: ['createdAt,desc'],
          },
        )
        setRatings(ratingsResponse)
      } catch (error) {
        console.error(error as ApiError)
      }
    }

    fetchData()
  }, [userId, page])

  const loadMore = async () => {
    try {
      // Fetch ratings data for subsequent pages
      const nextPage = page + 1
      const ratingsResponse = await RatingControllerService.getAllByUser(
        userId,
        {
          page: nextPage,
          size,
          sort: ['createdAt,desc'],
        },
      )
      setRatings((prevRatings) => [...prevRatings, ...ratingsResponse])
      setPage(nextPage)
      console.log('Ratings Response for Page', nextPage, ratingsResponse)
    } catch (error) {
      console.error(error as ApiError)
    }
  }

  // console.log('User', user)
  // console.log('Summary', summary)
  // console.log('Current Page', page)
  // console.log(page)
  // console.log('Ratings', ratings)

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
