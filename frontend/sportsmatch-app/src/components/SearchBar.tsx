import { TbSearch } from 'react-icons/tb'
import '../App.css'
import '../styles/Sport.css'

interface Params {
  onChange: (query: string) => void
}

export function SearchBar(p: Params) {
  const handleSearch = (e: { target: { value: string } }) => {
    p.onChange(e.target.value)
  }

  return (
    <div className="row">
      <div className="col">
        <TbSearch className="search-icon" />
        <input
          type="text"
          placeholder="Find your sports"
          className="input-search"
          onChange={handleSearch}
        />
      </div>
    </div>
  )
}
