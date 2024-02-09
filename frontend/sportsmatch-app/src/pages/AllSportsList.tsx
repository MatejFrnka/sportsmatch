import { useState } from 'react'
import { SportComponent } from '../components/SportComponent'
import { SportDTO } from '../generated/api'
import '../App.css'

import { TbSearch } from 'react-icons/tb'

export function AllSportsList() {
  const sampleSport: SportDTO[] = [
    { name: 'Badminton' },
    { name: 'Tennis' },
    { name: 'Boxing' },
  ]

  const [selectedSports, setSelectedSports] = useState<SportDTO[]>([])

  const sportList = sampleSport.map((currentSport, index) => {
    return (
      <SportComponent
        key={index}
        sport={currentSport}
        onChange={() => setSelectedSports([...selectedSports, currentSport])}
      />
    )
  })

  const renderSearchBar = (): JSX.Element => {
    return (
      <div className="container">
        <span className="input-group-text">
          <TbSearch className="search-icon" />
          <input
            type="text"
            placeholder="Find your sports"
            className="input-search"
          />
        </span>
      </div>
    )
  }

  return (
    <>
      <div className="body">
        <div>{renderSearchBar()}</div>
        <form action="">
          <div className="container">{sportList}</div>
          <div className="container">
            <button type="submit">
              Selected sports {selectedSports.length}
            </button>
          </div>
        </form>
      </div>
    </>
  )
}
