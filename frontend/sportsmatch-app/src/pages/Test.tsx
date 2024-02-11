import { useEffect, useState } from 'react'
import { useLocation, useParams } from 'react-router-dom'
import { SportDTO } from '../generated/api/models/SportDTO'

function Test() {
  const [message, setMessage] = useState('')
  const { testId } = useParams()

  const location = useLocation()

  const [sports, setSports] = useState<SportDTO[]>([])

  useEffect(() => {
    if (location.state != null) {
      setSports(location.state)
    }
  }, [location.state])

  const renderSports = sports.map((sport, index) => {
    return <div key={index}>{sport.name}</div>
  })

  useEffect(() => {
    if (testId) {
      setMessage('The number is ' + testId)
    } else {
      setMessage('The number was not provided.')
    }
  }, [testId])

  return (
    <div>
      <h1>{message}</h1>
      {<div>{renderSports}</div>}
    </div>
  )
}
export default Test
