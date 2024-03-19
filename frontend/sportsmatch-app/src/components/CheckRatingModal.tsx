import { useEffect } from 'react'
import Modal from '../components/Modal'
import RateGameComponent from '../components/RateGameComponent'
import { RatingControllerService, ApiError, OpenAPI } from '../generated/api'
import useModal from '../hooks/UseModal'

export default function CheckRatingModal() {
  const { isOpen, toggle } = useModal()
  useEffect(() => {
    const init = async () => {
      try {
        OpenAPI.TOKEN = localStorage.getItem('token')!
        const response = await RatingControllerService.checkRating()
        if (response[0] && !isOpen) {
          toggle()
        }
      } catch (error) {
        console.error(error as ApiError)
      }
    }
    init()
  })

  return (
    <>
      <Modal isOpen={isOpen} toggle={toggle} preventClosing={true}>
        <RateGameComponent toggle={toggle} />
      </Modal>
    </>
  )
}
