import { useEffect, useState } from 'react'
import '../styles/MainPage.css'

function MainPageSliderComponent() {
  const [currentSlide, setCurrentSlide] = useState(0)

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentSlide((prevSlide) => (prevSlide >= 3 ? 0 : prevSlide + 1))
    }, 6000)

    return () => clearInterval(interval)
  }, [])

  const handleManualSlide = (index: number) => {
    setCurrentSlide(index)
  }

  return (
    <div className="slider">
      <div
        className="slides"
        style={{ transform: `translateX(-${currentSlide * 100}vw)` }}
      >
        <div className="slide">
          <img className="brighten" src="./assets/1.jpg" alt="" />
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
        <div className="slide">
          <img src="./assets/2.jpg" alt="" />
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
        <div className="slide">
          <img src="./assets/3.jpg" alt="" />
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
        <div className="slide">
          <img src="./assets/4.jpg" alt="" />
          <div className="slide-content">
            <h2>Find, Match, Play Now!</h2>
            <p>Your Next Challenge Awaits.</p>
            <label htmlFor="play-btn"></label>
            <button>Let’s play!</button>
          </div>
        </div>
      </div>

      <div className="navigation-manual">
        <label
          htmlFor="radio1"
          className={`manual-btn ${currentSlide === 0 ? 'active' : ''}`}
          onClick={() => handleManualSlide(0)}
        ></label>
        <label
          htmlFor="radio2"
          className={`manual-btn ${currentSlide === 1 ? 'active' : ''}`}
          onClick={() => handleManualSlide(1)}
        ></label>
        <label
          htmlFor="radio3"
          className={`manual-btn ${currentSlide === 2 ? 'active' : ''}`}
          onClick={() => handleManualSlide(2)}
        ></label>
        <label
          htmlFor="radio4"
          className={`manual-btn ${currentSlide === 3 ? 'active' : ''}`}
          onClick={() => handleManualSlide(3)}
        ></label>
      </div>
    </div>
  )
}

export default MainPageSliderComponent
