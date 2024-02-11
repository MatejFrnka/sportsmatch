import { useState } from 'react'
import '../styles/MainPage.css'

function MainPageSportsBtnComponent() {
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
    <div className="mainPage-wrapper">
      <form action="">
        <p className="mainPage-p">Select your sports</p>
        <div className="mainPage-sports-container">
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Tennis')}
              onChange={() => handleCheckboxChange('Tennis')}
            />
            <span className="mainPage-sports-button">🥎 Tennis</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Gym')}
              onChange={() => handleCheckboxChange('Gym')}
            />
            <span className="mainPage-sports-button">🏋 Gym</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Golf')}
              onChange={() => handleCheckboxChange('Golf')}
            />
            <span className="mainPage-sports-button">🏌🏽 Golf</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Bowling')}
              onChange={() => handleCheckboxChange('Bowling')}
            />
            <span className="mainPage-sports-button">🎳 Bowling</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Running')}
              onChange={() => handleCheckboxChange('Running')}
            />
            <span className="mainPage-sports-button">🏃 Running</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Bicycle')}
              onChange={() => handleCheckboxChange('Bicycle')}
            />
            <span className="mainPage-sports-button">🚴‍♀️ Bicycle</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Boxing')}
              onChange={() => handleCheckboxChange('Boxing')}
            />
            <span className="mainPage-sports-button">🥊 Boxing</span>
          </label>
          <label>
            <input
              type="checkbox"
              className="mainPage-sports-checkbox"
              checked={selectedButtons.includes('Ping pong')}
              onChange={() => handleCheckboxChange('Ping pong')}
            />
            <span className="mainPage-sports-button">🏓 Ping pong</span>
          </label>
          <button id="mainPage-more-sports-button">
            .&nbsp;&nbsp;.&nbsp;&nbsp;.
          </button>
        </div>
      </form>
    </div>
  )
}

export default MainPageSportsBtnComponent
