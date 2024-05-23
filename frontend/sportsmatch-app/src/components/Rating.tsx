import React, { useState } from 'react'
import {
  TiStarFullOutline,
  TiStarHalfOutline,
  TiStarOutline,
} from 'react-icons/ti'

interface RatingProps {
  className?: string // a className that can be passed to customize the styling of the component
  count: number // the number of stars to display in the rating
  value: number // the current value of the rating
  color?: string //the color of the stars when they are not being hovered over or clicked on
  hoverColor?: string // the color of the stars when they are being hovered over
  activeColor?: string // the color of the stars when they are being clicked on
  size?: number // the size of the stars in pixels
  edit?: boolean // a boolean value that determines whether the rating can be edited by the user
  isHalf?: boolean // a boolean value that determines whether the rating allows for half-star increments
  onChange?: (value: number) => void //a callback function that is called when the rating is changed
  emptyIcon?: React.ReactElement // an icon to be used for empty stars
  halfIcon?: React.ReactElement // an icon to be used for half stars
  fullIcon?: React.ReactElement // an icon to be used for full stars
}

const Rating: React.FC<RatingProps> = ({
  className,
  count,
  value,
  color = '#e85f29',
  hoverColor = '#e85f29',
  activeColor = '#e85f29',
  size,
  edit = false,
  isHalf = false,
  onChange,
  emptyIcon = <TiStarOutline />,
  halfIcon = <TiStarHalfOutline />,
  fullIcon = <TiStarFullOutline />,
}) => {
  const [hoverValue, setHoverValue] = useState<number | undefined>(undefined)

  const handleMouseMove = (index: number) => {
    if (!edit) {
      return
    }
    setHoverValue(index)
  }

  const handleMouseLeave = () => {
    if (!edit) {
      return
    }
    setHoverValue(undefined)
  }

  const handleClick = (index: number) => {
    if (!edit) {
      return
    }
    if (onChange) {
      onChange(index + 1)
    }
  }

  const stars = []

  for (let i = 0; i < count; i++) {
    let star: React.ReactElement
    if (isHalf && value - i > 0 && value - i < 1) {
      star = halfIcon
    } else if (i < value) {
      star = fullIcon
    } else {
      star = emptyIcon
    }

    if (hoverValue !== undefined) {
      if (i <= hoverValue) {
        star = fullIcon
      }
    }
    stars.push(
      <span
        key={i}
        style={{ cursor: 'pointer' }}
        onMouseMove={() => handleMouseMove(i)}
        onMouseLeave={() => handleMouseLeave}
        onClick={() => handleClick(i)}
      >
        {React.cloneElement(star, {
          size: size,
          color:
            i <= Number(hoverValue)
              ? hoverColor
              : i < value
                ? activeColor
                : color,
        })}
      </span>,
    )
  }
  return <div className={`rating ${className}`}>{stars}</div>
}

export default Rating
