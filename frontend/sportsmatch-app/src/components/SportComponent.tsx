import React, { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../styles/Sport.css'

interface Props {
  sport: SportDTO
  onClickNext: (isSelected: boolean) => void
}

export function SportComponent(p: Props) {
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
        className={`container text-center ${className}`}
        style={{
          backgroundImage: `url(./assets/sport-component-boxing.jpg)`,
        }}
      >
        <div>
          <a
            href="#"
            onClick={() => {
              isSelected ? setSelected(false) : setSelected(true)
              changeClassName()
            }}
          >
            {p.sport.name}
          </a>
        </div>
      </div>
    )
  }
  return renderSportItem()
}
