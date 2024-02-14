import '../styles/SearchBar.css'
import { FaSearchLocation } from 'react-icons/fa'

function MainPageSearchBarComponent() {
  return (
    <div className="search-container">
      <form className="mainPage-form" action="">
        <input type="text" placeholder="Enter your location.." />
        <label htmlFor="search-btn"></label>
        <button className="search-container-btn" type="submit">
          <FaSearchLocation /> Search
        </button>
      </form>
    </div>
  )
}

export default MainPageSearchBarComponent
