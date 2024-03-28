import { useEffect, useState } from 'react'
import { TiStarOutline } from 'react-icons/ti'

interface StarsProp {
  numberOfStars: number
}

export default function Stars({ numberOfStars }: StarsProp) {
  const [stars, setStars] = useState<JSX.Element[]>([])

  useEffect(() => {
    const newStars: JSX.Element[] = [];
    for (let i = 0; i < numberOfStars; i++) {
      newStars.push(<TiStarOutline key={i} />);
    }
    setStars(newStars);
  }, [numberOfStars]);

  return (
    <>
      <div className="row star">
        <div className="col">{stars}</div>
      </div>
    </>
  )
}
