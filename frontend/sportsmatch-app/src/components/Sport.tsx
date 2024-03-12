import { SportDTO } from '../generated/api'
import '../styles/SportButton.css'

interface SportProps extends SportDTO {
  selected: boolean
  onSelect: () => void
}

export default function Sport({ emoji, name, selected, onSelect }: SportProps) {
  return (
    <>
      <div
        className={`sport-checkbox
        d-flex
        justify-content-center
        ${selected ? 'selected-btn' : 'unselected-btn'}`}
      >
        <input
          type="checkbox"
          id={`sport-${name}`}
          checked={selected}
          onChange={onSelect}
        />
        <label htmlFor={`sport-${name}`}>
          <span>{emoji}</span>
          <span>{name}</span>
        </label>
      </div>
    </>
  )
}
