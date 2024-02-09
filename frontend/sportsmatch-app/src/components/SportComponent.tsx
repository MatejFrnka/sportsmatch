import { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../styles/Sport.css'

interface Props {
  sport: SportDTO
  onChange: () => void
}

export const SportComponent = (p: Props) => {
  const [isSelected, setSelected] = useState<boolean>(false)

  const [className, setClassName] = useState<string>(`unselected`)

  const changeClassName = () => {
    if (isSelected) {
      setClassName(`unselected`)
    } else {
      setClassName(`selected`)
    }
  }

  const renderSportItem = (): JSX.Element => {
    return (
      <div
        className={`container checkbox-wrapper text-center ${className}`}
        style={{
          backgroundImage: `url(./assets/sport-component-boxing.jpg)`,
        }}
      >
        <label>
          <input
            className="checkbox"
            id={p.sport.name}
            type="checkbox"
            checked={isSelected}
            onChange={() => {
              setSelected((prev) => !prev)
              changeClassName()
            }}
          />
          <span>{p.sport.name}</span>
        </label>
      </div>
    )
  }
  return renderSportItem()
}
