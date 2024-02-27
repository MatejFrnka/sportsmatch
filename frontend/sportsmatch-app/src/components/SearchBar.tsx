import { TbSearch } from 'react-icons/tb'
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
      <div className="col" style={{ position: 'relative' }}>
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
