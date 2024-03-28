import { RatingDTO } from '../generated/api'
import '../styles/RatingCard.css'
import Stars from './Stars'

interface RatingCardProps {
  name: string
  rating: RatingDTO
}

export default function RatingCard({ name, rating }: RatingCardProps) {
  const {userTextRating, userStarRating} = rating
  return (
    <>
      <div className="rated-wrapper">
        <div className="row rated">
          <div className="col-2">
            <img
              className="rated-img"
              src="\pictures\jeffrey-keenan-pUhxoSapPFA-unsplash.jpg"
            />
          </div>
          <div className="col">
            <div className="row">
              <div className="col rated-name">
                <h5>{name}</h5>
              </div>
            </div>
            <div className="row rated-star">
              <div className="col">
                <Stars numberOfStars={userStarRating} isSolid={true} />
              </div>
            </div>
            <p>{userTextRating}</p>
          </div>
        </div>
      </div>
    </>
  )
}
