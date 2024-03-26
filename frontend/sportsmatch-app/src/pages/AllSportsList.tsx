import React, { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../App.css'
import '../styles/Sport.css'
import { useNavigate } from 'react-router-dom'
import { SearchBar } from '../components/SearchBar'

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

  return (
    <>
      <div className="container-fluid  sports-page-wrapper">
        <SearchBar
          onChange={(query: string) => {
            setSearchQuery(query)
          }}
        />
        {sportList}
        <div className="row">
          <div className="col">
            <button type="submit" onClick={handleFinishSelection}>
              Selected sports{' '}
              {sportsState.filter((sport) => sport.selected).length}
            </button>
          </div>
        </div>
      </div>
    </>
  )
}

export const sampleSports: SportDTO[] = [
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