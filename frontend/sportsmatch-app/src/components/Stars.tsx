import { useEffect, useState } from 'react'
import { TiStarFullOutline, TiStarOutline } from 'react-icons/ti'

interface StarsProp {
  numberOfStars: number
  isSolid?: boolean
}

export default function Stars({ numberOfStars, isSolid }: StarsProp) {
  const [stars, setStars] = useState<JSX.Element[]>([])

  useEffect(() => {
    const newStars: JSX.Element[] = []
    for (let i = 0; i < numberOfStars; i++) {
      if (isSolid === true) {
        newStars.push(<TiStarFullOutline key={i} />)
      } else{
        newStars.push(<TiStarOutline key={i} />)
      }
    }
    setStars(newStars)
  }, [numberOfStars])

  return <>{stars}</>
}