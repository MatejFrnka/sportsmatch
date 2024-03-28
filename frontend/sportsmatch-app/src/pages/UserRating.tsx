import { useEffect, useState } from 'react'
import RatingCard from '../components/RatingCard'
import UserCard from '../components/UserCard'
import { RatingDTO, UserDTO } from '../generated/api'
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

const sampleData : RatingDTO[] = [
  {
    name: 'Alex S.',
    userTextRating: "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aliquam erat volutpat.",
    userStarRating: 4,
    eventStarRating: 5,
    myScore: 10,
    opponentScore: 5,
  },
  {
    name: 'John D.',
    userTextRating: "Suspendisse potenti. Nullam porta enim eu tortor. Sed ante.",
    userStarRating: 3,
    eventStarRating: 4,
    myScore: 8,
    opponentScore: 6,
  },
  {
    name: 'Bella S.',
    userTextRating: "Fusce ultrices fringilla metus. Nam commodo suscipit quam.",
    userStarRating: 5,
    eventStarRating: 4,
    myScore: 12,
    opponentScore: 4,
  },
  {
    name: 'Robert D.',
    userTextRating: "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam libero vitae velit.",
    userStarRating: 2,
    eventStarRating: 3,
    myScore: 7,
    opponentScore: 8,
  },
  {
    name: 'Anna A.',
    userTextRating: "Quisque egestas diam in arcu. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus.",
    userStarRating: 4,
    eventStarRating: 5,
    myScore: 9,
    opponentScore: 5,
  }
];


export default function UserRating() {

  const [ ratings, setRatings ] = useState<RatingDTO[]>([])
  const [ currentPage, setCurrentPage] = useState<number>(1)
  const itemsPerPage = 3; //simulate pagination

  const loadMore = () => {
    const nextPage = currentPage + 1;
    const startIndex = (nextPage - 1) * itemsPerPage;
    const endIndex = nextPage * itemsPerPage;
    const newRatings = sampleData.slice(startIndex, endIndex);
    
    if (newRatings.length > 0) {
      setRatings(prevRatings => [...prevRatings, ...newRatings]);
      setCurrentPage(nextPage);
    } else {
      // Handle no more data to load
      console.log("No more data to load");
    }
  };

  useEffect(() => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = currentPage * itemsPerPage;
    const initialData = sampleData.slice(startIndex, endIndex);
    setRatings(initialData);
  }, []); 

  /*

  useEffect(() => {
    const response = async () => {
      side effect implementation here
    }, [dependency here]);

  */

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
              {ratings.map((rating, index) =>(
                <RatingCard rating={rating} key={index}/>
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
