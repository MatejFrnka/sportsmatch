import { useState } from 'react'
import { SportDTO } from '../generated/api'
import '../styles/Sport.css'

interface Props {
  sport: SportDTO
  onClickNext: (isSelected: boolean) => void
}

export function SportComponent(p: Props) {
  const [isSelected, setSelected] = useState<boolean>(false)

  const renderSportItem = (): JSX.Element => {
    if (isSelected) {
      return (
        <div
          className="container text-center selected"
          style={{
            backgroundImage: `url(./assets/sport-component-boxing.jpg)`,
          }}
        >
          <div>
            <div>
              <a
                href="#"
                onClick={() =>
                  isSelected ? setSelected(false) : setSelected(true)
                }
              >
                {p.sport.name}
              </a>
            </div>
          </div>
        </div>
      )
    } else {
      return (
        <div
          className="container-xxl text-center unselected"
          style={{
            backgroundImage: `url(./assets/sport-component-boxing.jpg)`,
          }}
        >
          <div className="row">
            <div>
              <a
                href="#"
                onClick={() => {
                  isSelected ? setSelected(false) : setSelected(true)
                  p.onClickNext(isSelected)
                }}
              >
                {p.sport.name}
              </a>
            </div>
          </div>
        </div>
      )
    }
  }
  return renderSportItem()
}
