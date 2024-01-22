import './App.css';
import SportEvent from './components/SportEvent';

function App() {

  
  const sampleEvent = {
    dateStart: "21-Jan-24",
    dateEnd: "21-Jan-24",
    location: "sports center, prague",
    minElo: 1500,
    maxElo: 1800,
    sport: "Tennis",
    playerOne: "playerOneName",
  }

  return (
    <>
      <SportEvent Event={sampleEvent} />
    </>
  );
}

export default App;
