import { useState } from 'react'
import { TbSearch } from 'react-icons/tb'
import '../App.css'
import '../styles/Sport.css'

interface Params {
  onChange: (query: string) => void
}

export function SearchBar(p: Params) {
  const [searchQuery, setSearchQuery] = useState('')

  const handleSearch = (e: { target: { value: string } }) => {
    const query = e.target.value
    p.onChange(query)
    setSearchQuery(query)
  }

  return (
    <div className="row">
      <div className="col">
        <TbSearch className="search-icon" />
        <input
          type="text"
          placeholder="Find your sports"
          className="input-search"
          value={searchQuery}
          onChange={handleSearch}
        />
      </div>
    </div>
  )
}
