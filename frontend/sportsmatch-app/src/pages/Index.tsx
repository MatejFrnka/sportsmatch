import SportsButtonComponent from '../components/SportsButtonComponent'
import SportEvent from '../components/SportEvent'
import { SearchBar } from '../components/SearchBar'
import '../styles/Index.css'

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
    <div className="container-fluid">
      <div className={'row'}>
        <div className="slide">
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
      </div>
      <div className="row">
        <SearchBar onChange={(a) => {}} />
      </div>
      <div className="row">
        <SportsButtonComponent />
      </div>
      <div className="row">
        <p className="mainPage-p">Nearby</p>
        <div className="nearby-events-container">
          {events.map((event, index) => (
            <div className="nearby-events" key={index}>
              <SportEvent event={event} />
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
