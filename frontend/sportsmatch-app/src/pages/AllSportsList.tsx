import { useState } from 'react'
import { SportComponent } from '../components/SportComponent'
import { SportDTO } from '../generated/api'
import 'bootstrap/dist/css/bootstrap.min.css'
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
        <div className="input-group mb-3">
          <span className="input-group-text">
            <TbSearch className="search-icon" />
            <input
              type="text"
              placeholder="Find your sports"
              className="input-search"
            />
          </span>
        </div>
      </div>
    )
  }

  return (
    <>
      <div className="body">
        <div>{renderSearchBar()}</div>
        <div className="container">{sportList}</div>

        <div>{selectedSports.length}</div>
      </div>
    </>
  )
}
