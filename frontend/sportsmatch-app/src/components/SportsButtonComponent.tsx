import { useCallback, useEffect, useState } from 'react'
import '../styles/NewUserComponent.css'
import { SportControllerService, SportDTO } from '../generated/api'
import Modal from './Modal'
import useModal from '../hooks/UseModal'
import { AllSportsList } from './AllSportsList'

function SportsButtonComponent({
  onSportSelectionChange,
  clearFilters,
}: {
  onSportSelectionChange: (selectedSports: string[]) => void
  clearFilters?: boolean
}) {
  const [selectedButtonSports, setselectedButtonSports] = useState<string[]>([])
  const { isOpen, toggle } = useModal()

  useEffect(() => {
    if (clearFilters) {
      setselectedButtonSports([])
    }
  }, [clearFilters])

  const handleCheckboxChange = (buttonText: string) => {
    setselectedButtonSports((prevState) => {
      if (prevState.includes(buttonText)) {
        return prevState.filter((button) => button !== buttonText)
      } else {
        return [...prevState, buttonText]
      }
    })
  }

  const memoizedOnSportSelectionChange = useCallback(onSportSelectionChange, [
    onSportSelectionChange,
  ])

  useEffect(() => {
    memoizedOnSportSelectionChange(selectedButtonSports)
  }, [selectedButtonSports, memoizedOnSportSelectionChange])

  // opens the more sports popup window
  const handleMoreSportsButton = () => {
    toggle()
  }

  // sports from the backend
  const [sports, setSports] = useState<SportDTO[]>([])

  // fetching sports from the backend
  useEffect(() => {
    const fetchSports = async () => {
      setSports((await SportControllerService.getSports(0, 5)) as SportDTO[])
    }
    fetchSports()
  }, [])

  return (
    <div className="wrapper">
      <p>Select your sports</p>
      <div className="sports-container">
        {sports.map((s, index) => (
          <label key={index}>
            <input
              type="checkbox"
              className="sports-checkbox"
              checked={selectedButtonSports.includes(s.name!)}
              onChange={() => handleCheckboxChange(s.name!)}
            />
            <span className="sports-button">{s.emoji + ' ' + s.name}</span>
          </label>
        ))}
        <label>
          <input
            className="more-sports-button"
            type="button"
            value=".&nbsp;&nbsp;.&nbsp;&nbsp;."
            onClick={handleMoreSportsButton}
          />
        </label>
        <Modal isOpen={isOpen} preventClosing={false} toggle={toggle}>
          <AllSportsList
            selectedButtonSports={selectedButtonSports}
            toggle={toggle}
            onSelect={(s: string[]) => {
              setselectedButtonSports(s)
            }}
          />
        </Modal>
      </div>
    </div>
  )
}

export default SportsButtonComponent
