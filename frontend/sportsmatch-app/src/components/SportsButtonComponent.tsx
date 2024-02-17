import { useState } from 'react'
import '../styles/NewUserComponent.css'

function SportsButtonComponent() {
  const [selectedButtons, setSelectedButtons] = useState<string[]>([])

  const handleCheckboxChange = (buttonText: string) => {
    if (selectedButtons.includes(buttonText)) {
      setSelectedButtons(
        selectedButtons.filter((button) => button !== buttonText),
      )
    } else {
      setSelectedButtons([...selectedButtons, buttonText])
    }
  }

  return (
    <div className="wrapper">
      <form action="">
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
          <button id="more-sports-button">.&nbsp;&nbsp;.&nbsp;&nbsp;.</button>
        </div>
      </form>
    </div>
  )
}

export default SportsButtonComponent
