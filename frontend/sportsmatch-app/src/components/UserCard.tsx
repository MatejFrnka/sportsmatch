import Stars from './Stars'
import ProgressBar from 'react-bootstrap/ProgressBar'
import { UserDTO, UserRatingStatsDTO } from '../generated/api'
import '../styles/UserCard.css'

interface UserCardProp {
  user: UserDTO
  summary: UserRatingStatsDTO
}

export default function UserCard({ user, summary }: UserCardProp) {
  const starRatingCounts = summary.starRatingCounts || {}
  const totalRatings = Object.values(starRatingCounts).reduce(
    (acc, count) => acc + count,
    0,
  )
  const calculatePercentage = (count: number, total: number): number => {
    return total === 0 ? 0 : (count / total) * 100
  }
  return (
    <>
      <div className="row">
        <div className="col">
          <div className="user-card">
            <div className="row">
              <div className="col profile-details">
                <img src="/assets/michael-dam-mEZ3PoFGs_k-unsplash.jpg" />
                <ul>
                  <li>
                    <h4>{user.name}</h4>
                  </li>
                  {/* <li>Prague, CZ</li> */}
                  <li>
                    {user.win}W {user.loss}L
                  </li>
                </ul>
              </div>
              <div className="col-4 elo">
                <h6>ELO: {user.elo}</h6>
              </div>
            </div>

            <div className="row">
              <hr className="profile-border"></hr>
              <div className="col">
                <h6>Ratings & Reviews</h6>
              </div>
            </div>
            <div className="row">
              <div className="col-3 d-flex justify-content-center">
                <h1>{summary.averageRating}</h1>
                <p>out of 5</p>
              </div>
              <div className="col g-0">
                <div className="row g-0">
                  <div className="col-4 stars">
                    <div className="row star">
                      <div className="col">
                        <Stars numberOfStars={5} />
                      </div>
                    </div>
                    <div className="row star">
                      <div className="col">
                        <Stars numberOfStars={4} />
                      </div>
                    </div>
                    <div className="row star">
                      <div className="col">
                        <Stars numberOfStars={3} />
                      </div>
                    </div>
                    <div className="row star">
                      <div className="col">
                        <Stars numberOfStars={2} />
                      </div>
                    </div>
                    <div className="row star">
                      <div className="col">
                        <Stars numberOfStars={1} />
                      </div>
                    </div>
                  </div>
                  <div className="col">
                    <div className="row">
                      <div className="col progbar">
                        {summary.starRatingCounts &&
                          Object.entries(summary.starRatingCounts)
                            .reverse()
                            .map(([rating, count]) => (
                              <ProgressBar
                                key={rating}
                                now={calculatePercentage(count, totalRatings)}
                              />
                            ))}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
