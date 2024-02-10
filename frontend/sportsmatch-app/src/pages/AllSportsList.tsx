import { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../App.css'
import '../styles/Sport.css'

import { TbSearch } from 'react-icons/tb'

export function AllSportsList() {
  const sampleSports: SportDTO[] = [
    { name: 'Badminton' },
    { name: 'Tennis' },
    { name: 'Boxing' },
  ]

  interface SportState {
    sport: SportDTO
    selected: boolean
  }

  const [sportsState, setSportsState] = useState<SportState[]>(
    sampleSports.map((sport) => ({ sport, selected: false })),
  )

  const handleSportSelection = (index: number) => {
    const updatedSportsState = [...sportsState]
    updatedSportsState[index].selected = !updatedSportsState[index].selected
    setSportsState(updatedSportsState)
  }

  const sportList = sportsState.map((currentSport, index) => {
    return (
      <div
        key={index}
        className={`container checkbox-wrapper text-center 
        ${currentSport.selected ? 'selected' : 'unselected'}`}
        style={{
          backgroundImage: `url(./assets/sport-component-boxing.jpg)`,
        }}
      >
        <label>
          <input
            className="checkbox"
            type="checkbox"
            checked={currentSport.selected}
            onChange={() => {
              handleSportSelection(index)
            }}
          />
          <span>{currentSport.sport.name}</span>
        </label>
      </div>
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
              Selected sports{' '}
              {sportsState.filter((sport) => sport.selected).length}
            </button>
          </div>
        </form>
      </div>
    </>
  )
}
