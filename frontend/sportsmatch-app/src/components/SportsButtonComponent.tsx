import { useCallback, useEffect, useState } from 'react'
import '../styles/NewUserComponent.css'
import { useNavigate } from 'react-router-dom'
import { SportControllerService, SportDTO } from '../generated/api'

function SportsButtonComponent({
  onSportSelectionChange,
  clearFilters,
}: {
  onSportSelectionChange: (selectedSports: string[]) => void
  clearFilters?: boolean
}) {
  const [selectedButtonSports, setselectedButtonSports] = useState<string[]>([])

  useEffect(() => {
    if (clearFilters) {
      setselectedButtonSports([])
    }
  }, [clearFilters])

  const handleCheckboxChange = (buttonText: string) => {
    setselectedButtonSports((prevState) => {
      if (prevState.includes(buttonText)) {
        return prevState.filter((button) => button !== buttonText)
      } else {
        return [...prevState, buttonText]
      }
    })
  }

  const memoizedOnSportSelectionChange = useCallback(onSportSelectionChange, [
    onSportSelectionChange,
  ])

  useEffect(() => {
    memoizedOnSportSelectionChange(selectedButtonSports)
  }, [selectedButtonSports, memoizedOnSportSelectionChange])

  const navigate = useNavigate()
  const handleMoreSportsButton = () => {
    navigate('/allsports', { state: { selectedButtonSports } })
  }

  const [sports, setSports] = useState<SportDTO[]>([])

  useEffect(() => {
    const fetchSports = async () => {
      setSports(
        (await SportControllerService.getSports({
          page: 0,
          size: 5,
        })) as SportDTO[],
      )
    }
    fetchSports()
  }, [])

  return (
    <div className="wrapper">
      <p>Select your sports</p>
      <div className="sports-container">
        {sports.map((s, index) => (
          <label key={index}>
            <input
              type="checkbox"
              className="sports-checkbox"
              checked={selectedButtonSports.includes(s.name!)}
              onChange={() => handleCheckboxChange(s.name!)}
            />
            <span className="sports-button">{s.emoji + ' ' + s.name}</span>
          </label>
        ))}
        <label>
          <input
            className="more-sports-button"
            type="button"
            value=".&nbsp;&nbsp;.&nbsp;&nbsp;."
            onClick={handleMoreSportsButton}
          />
        </label>
        {/* <button id="more-sports-button">.&nbsp;&nbsp;.&nbsp;&nbsp;.</button> */}
      </div>
    </div>
  )
}

export default SportsButtonComponent
