import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import Navbar from '../components/Navbar'
import { SearchBar } from '../components/SearchBar'

export default function MainPage() {
  const events = [
    {
      id: 1,
      maxElo: 2000,
      minElo: 1200,
      dateEnd: '2024-05-02',
      dateStart: '2024-05-01',
      location: 'Prague, Stadium A',
      title: 'Badminton match',
      sport: 'Badminton',
      playerOne: 'johndoe87',
      playerTwo: 'jess_ward',
    },
    {
      id: 1,
      maxElo: 2000,
      minElo: 1200,
      dateEnd: '2024-05-02',
      dateStart: '2024-05-01',
      location: 'Prague, Stadium A',
      title: 'Badminton match',
      sport: 'Badminton',
      playerOne: 'johndoe87',
      playerTwo: 'jess_ward',
    },
    {
      id: 1,
      maxElo: 2000,
      minElo: 1200,
      dateEnd: '2024-05-02',
      dateStart: '2024-05-01',
      location: 'Prague, Stadium A',
      title: 'Badminton match',
      sport: 'Badminton',
      playerOne: 'johndoe87',
      playerTwo: 'jess_ward',
    },
    {
      id: 1,
      maxElo: 2000,
      minElo: 1200,
      dateEnd: '2024-05-02',
      dateStart: '2024-05-01',
      location: 'Prague, Stadium A',
      title: 'Badminton match',
      sport: 'Badminton',
      playerOne: 'johndoe87',
      playerTwo: 'jess_ward',
    },
  ]
  return (
    <div className="container-sm">
      <Navbar />
      <SearchBar onChange={(a) => {}} />
      <SportsButtonComponent />
      <p className="mainPage-p">Nearby</p>
      <div className="nearby-events-container">
        {events.map((event, index) => (
          <div className="nearby-events" key={index}>
            <SportEvent event={event} />
          </div>
        ))}
      </div>
    </div>
  )
}
