import React, { useEffect, useState } from 'react'
import { SportControllerService, SportDTO } from '../generated/api'
import '../App.css'
import '../styles/Sport.css'
import { SearchBar } from '../components/SearchBar'

interface AllSportsProps {
  selectedButtonSports: string[] // contains selected buttons from the parent element
  toggle: () => void
  onSelect: (sports: string[]) => void // passes back selected buttons to the parent element
}

export function AllSportsList(p: AllSportsProps) {
  // fetching sports from the backend
  const [allSports, setAllSports] = useState<SportDTO[]>([])
  useEffect(() => {
    const fetchSports = async () => {
      setAllSports(
        (await SportControllerService.getSports(0, 999)) as SportDTO[],
      )
    }
    fetchSports()
  }, [])

  // handling the searchbar
  const [searchQuery, setSearchQuery] = useState('')
  const [selectedSports, setSelectedSports] = useState<string[]>(
    p.selectedButtonSports,
  )
  // handling cliking on the sport button
  const handleSportSelection = (buttonText: string) => {
    setSelectedSports((prevState) => {
      if (prevState.includes(buttonText)) {
        return prevState.filter((button) => button !== buttonText)
      } else {
        return [...prevState, buttonText]
      }
    })
  }

  // handling passing the selected sports to the parent and closses the window
  const handleFinishSelection = (sports: string[]) => {
    p.onSelect(sports)
    p.toggle()
  }

  // rendering sports filtered by the query from the searchbar
  const sportList: React.ReactElement[] = allSports
    .filter((s) => s.name?.toLowerCase().includes(searchQuery.toLowerCase()))
    .map((currentSport, index) => {
      return (
        <div
          key={index}
          className={`checkbox-wrapper text-center
        `}
          style={{
            backgroundImage: `url(${currentSport.backgroundUImageURL})`,
          }}
        >
          <div className="row">
            <div
              className={`col ${
                selectedSports.includes(currentSport.name!)
                  ? 'selected'
                  : 'unselected'
              }`}
            >
              <label>
                <input
                  className="checkbox"
                  type="checkbox"
                  checked={selectedSports.includes(currentSport.name!)}
                  onChange={() => {
                    handleSportSelection(currentSport.name!)
                  }}
                />
                <span>{currentSport.name}</span>
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
            <button
              type="submit"
              onClick={() => handleFinishSelection(selectedSports)}
            >
              Selected sports {selectedSports.length}
            </button>
          </div>
        </div>
      </div>
    </>
  )
}
