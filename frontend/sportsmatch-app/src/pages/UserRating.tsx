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
  const size = 2

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
        setRatings((prevRatings) => [...prevRatings, ...ratingsResponse])
      } catch (error) {
        console.error(error as ApiError)
      }
    }

    fetchData()
  }, [userId, page])

  const loadMore = () => {
    setPage((prevPage) => prevPage + 1)
  }

  // console.log('User',user)
  // console.log('Summary',summary)
  // console.log('Current Page',page)
  // console.log('Ratings',ratings)

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
              {ratings.map((rating, index) => (
                <RatingCard rating={rating} key={index} />
              ))}
            </div>
          </div>
          <div className="row load-btn">
            <div className="col">
              <button onClick={loadMore}>Load More</button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
