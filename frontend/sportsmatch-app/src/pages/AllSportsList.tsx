import React, { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../App.css'
import '../styles/Sport.css'
import { useNavigate } from 'react-router-dom'
import { TbSearch } from 'react-icons/tb'
import Navbar from '../components/Navbar'

export function AllSportsList() {
  const sampleSports: SportDTO[] = [
    {
      name: 'Badminton',
      emoji: '🏸',
      backgroundUImageURL: './assets/sport-component-badminton.png',
    },
    {
      name: 'Tennis',
      emoji: '🎾',
      backgroundUImageURL: './assets/sport-component-tennis.png',
    },
    {
      name: 'Boxing',
      emoji: '🥊',
      backgroundUImageURL: './assets/sport-component-boxing.png',
    },
    {
      name: 'Table Tennis',
      emoji: '🏓',
      backgroundUImageURL: './assets/sport-component-table-tennis.png',
    },
    {
      name: 'Squash',
      emoji: '🥎',
      backgroundUImageURL: './assets/sport-component-squash.png',
    },
  ]

  const url = '/test/3'

  interface SportState {
    sport: SportDTO
    selected: boolean
  }

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
    navigate(url, { state: selectedSports })
  }

  const sportList: React.ReactElement[] = sportsState
    .filter((s) =>
      s.sport.name?.toLowerCase().includes(searchQuery.toLowerCase()),
    )
    .map((currentSport, index) => {
      return (
        <div
          key={index}
          className={`checkbox-wrapper text-center
        `}
          style={{
            backgroundImage: `url(${currentSport.sport.backgroundUImageURL})`,
          }}
        >
          <div className="row">
            <div
              className={`col ${currentSport.selected ? 'selected' : 'unselected'}`}
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
          </div>
        </div>
      )
    })

  const renderSearchBar = (): React.ReactElement => {
    return (
      <div className="container">
        <div className="row">
          <div className="col">
            <TbSearch className="search-icon" />
            <input
              type="text"
              placeholder="Find your sports"
              className="input-search"
              value={searchQuery}
              onChange={handleSearch}
            />
          </div>
        </div>
      </div>
    )
  }

  return (
    <>
      <div className="container-sm  sports-page-wrapper">
        <div className="container-sm">
          <div className="row">
            <div className="col">
              <Navbar />
            </div>
          </div>
        </div>
        {renderSearchBar()}
        <div className="container position-relative">{sportList}</div>
        <div className="container submit-button">
          <div className="row-cols-1">
            <div className="col">
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
