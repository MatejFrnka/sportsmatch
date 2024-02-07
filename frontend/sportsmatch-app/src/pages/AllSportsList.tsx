import { useState } from 'react'
import { SportComponent } from '../components/SportComponent'
import { SportDTO } from '../generated/api'

export function AllSportsList() {
  const sampleSport: SportDTO[] = [
    { name: 'Badminton' },
    { name: 'Tennis' },
    { name: 'Boxing' },
  ]

  const [selectedSports, setSelectedSports] = useState<SportDTO[]>([])

  const sportList = sampleSport.map((currentSport: SportDTO) => {
    return (
      <SportComponent
        key={currentSport.name}
        sport={currentSport}
        onClickNext={(isSelected: boolean) => {
          if (!isSelected) {
            setSelectedSports([...selectedSports, currentSport])
          } else {
            const updatedSports = selectedSports.filter(
              (sport) => sport.name !== currentSport.name,
            )
            setSelectedSports(updatedSports)
          }
        }}
      />
    )
  })

  return (
    <>
      <div className="container">{sportList}</div>
      <div>{selectedSports.length}</div>
    </>
  )
}
