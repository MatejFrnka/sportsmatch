import SportEvent from '../components/SportEvent'

function Home() {
  const sampleEvent = {
    id: 1,
    maxElo: 2000,
    minElo: 1200,
    dateEnd: '2024-05-01',
    dateStart: '2024-05-01',
    location: 'Prague, Stadium A',
    title: 'Badminton match',
    sport: 'Badminton',
    playerOne: 'johndoe87',
    playerTwo: 'jess_ward',
  }

  return (
    <>
      <SportEvent event={sampleEvent} />
    </>
  )
}

export default Home
