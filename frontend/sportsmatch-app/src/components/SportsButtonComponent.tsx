import { useCallback, useEffect, useState } from 'react'
import '../styles/NewUserComponent.css'
import { useNavigate } from 'react-router-dom'

function SportsButtonComponent({
  onSportSelectionChange,
  clearFilters,
}: {
  onSportSelectionChange: (selectedSports: string[]) => void
  clearFilters: boolean
}) {
  const [selectedButtons, setSelectedButtons] = useState<string[]>([])

  useEffect(() => {
    if (clearFilters) {
      setSelectedButtons([])
    }
  }, [clearFilters])

  const handleCheckboxChange = (buttonText: string) => {
    setSelectedButtons((prevState) => {
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
    memoizedOnSportSelectionChange(selectedButtons)
  }, [selectedButtons, memoizedOnSportSelectionChange])

  const navigate = useNavigate()
  const handleMoreSportsButton = () => {
    navigate('/allsports', { state: { selectedButtons } })
  }

  return (
    <div className="wrapper">
      <p>Select your sports</p>
      <div className="sports-container">
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Tennis')}
            onChange={() => handleCheckboxChange('Tennis')}
          />
          <span className="sports-button">🥎 Tennis</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Gym')}
            onChange={() => handleCheckboxChange('Gym')}
          />
          <span className="sports-button">🏋 Gym</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Golf')}
            onChange={() => handleCheckboxChange('Golf')}
          />
          <span className="sports-button">🏌🏽 Golf</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Bowling')}
            onChange={() => handleCheckboxChange('Bowling')}
          />
          <span className="sports-button">🎳 Bowling</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Running')}
            onChange={() => handleCheckboxChange('Running')}
          />
          <span className="sports-button">🏃 Running</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Bicycle')}
            onChange={() => handleCheckboxChange('Bicycle')}
          />
          <span className="sports-button">🚴‍♀️ Bicycle</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Boxing')}
            onChange={() => handleCheckboxChange('Boxing')}
          />
          <span className="sports-button">🥊 Boxing</span>
        </label>
        <label>
          <input
            type="checkbox"
            className="sports-checkbox"
            checked={selectedButtons.includes('Ping pong')}
            onChange={() => handleCheckboxChange('Ping pong')}
          />
          <span className="sports-button">🏓 Ping pong</span>
        </label>
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
