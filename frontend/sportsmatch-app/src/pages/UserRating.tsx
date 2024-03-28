import RatingCard from '../components/RatingCard'
import UserCard from '../components/UserCard'
import { UserDTO } from '../generated/api'
import '../styles/UserRating.css'

/*
sampleUser will be replace by : /api/v1/rating/{username}/summary
that returns
Average rating
Number of 1, 2, 3, 4 and 5 star reviews

also include
rank, win and loss from user Entity
*/
const sampleUser: UserDTO = {
  name: `Jane Doe`,
  elo: 887,
}

export default function UserRating() {
  return (
    <>
      <div className="row">
        <div className="col">
          <UserCard user={sampleUser} />
        </div>
      </div>
      <div className="row">
        <div className="col">
          <div className="row">
            <div className="col">
              <RatingCard />
            </div>
          </div>
          <div className="row load-btn">
            <div className="col">
              <button>Load More</button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
