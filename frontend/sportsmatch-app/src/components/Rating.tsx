import React, { useState } from 'react'
import {
  TiStarFullOutline,
  TiStarHalfOutline,
  TiStarOutline,
} from 'react-icons/ti'

interface RatingProps {
  className?: string
  count: number
  value: number
  color?: string
  hoverColor?: string
  activeColor?: string
  size?: number
  edit?: boolean
  isHalf?: boolean
  onChange?: (value: number) => void
  emptyIcon?: React.ReactElement
  halfIcon?: React.ReactElement
  fullIcon?: React.ReactElement
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
