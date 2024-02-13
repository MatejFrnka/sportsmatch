import React, { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../App.css'
import '../styles/Sport.css'
import { useNavigate } from 'react-router-dom'
import { TbSearch } from 'react-icons/tb'

export function AllSportsList() {
  const sampleSports: SportDTO[] = [
    { name: 'Badminton' },
    { name: 'Tennis' },
    { name: 'Boxing' },
    { name: 'Table Tennis' },
    { name: 'Squash' },
    { name: 'Bowling' },
  ]

  interface SportState {
    sport: SportDTO
    selected: boolean
  }
  const backgroundImageUrl = './assets/sport-component-boxing.jpg'

  const navigate = useNavigate()

  const [sportsState, setSportsState] = useState<SportState[]>(
    sampleSports.map((sport) => ({ sport, selected: false })),
  )
  const [searchQuery, setSearchQuery] = useState('')

  const handleSearch = (e: { target: { value: string } }) => {
    const query = e.target.value
    setSearchQuery(query)
  }

  const handleSportSelection = (sport: SportState) => {
    const index = sportsState.indexOf(sport)
    const updatedSportsState = [...sportsState]
    updatedSportsState[index].selected = !updatedSportsState[index].selected
    setSportsState(updatedSportsState)
  }

  const handleFinishSelection = () => {
    const selectedSports = sportsState
      .filter((s) => s.selected)
      .map((s) => s.sport)
    navigate('/test/3', { state: selectedSports })
  }

  const sportList: React.ReactElement[] = sportsState
    .filter((s) =>
      s.sport.name?.toLowerCase().includes(searchQuery.toLowerCase()),
    )
    .map((currentSport, index) => {
      return (
        <div
          key={index}
          className={`row checkbox-wrapper text-center 
        ${currentSport.selected ? 'selected' : 'unselected'}`}
          style={{
            backgroundImage: `url(${backgroundImageUrl})`,
          }}
        >
          <label>
            <input
              className="checkbox"
              type="checkbox"
              checked={currentSport.selected}
              onChange={() => {
                handleSportSelection(currentSport)
              }}
            />
            <span>{currentSport.sport.name}</span>
          </label>
        </div>
      )
    })

  const renderSearchBar = (): React.ReactElement => {
    return (
      <div className="container">
        <TbSearch className="search-icon" />
        <input
          type="text"
          placeholder="Find your sports"
          className="input-search"
          value={searchQuery}
          onChange={handleSearch}
        />
      </div>
    )
  }

  return (
    <>
      <div className="container-sm">
        <div className="allsports-page">
          {renderSearchBar()}
          <div className="container">
            {sportList}
            <div className="row button-wrapper">
              <button type="submit" onClick={handleFinishSelection}>
                Selected sports{' '}
                {sportsState.filter((sport) => sport.selected).length}
              </button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
