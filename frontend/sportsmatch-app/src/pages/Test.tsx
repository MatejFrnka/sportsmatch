import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'

function Test() {
  const [message, setMessage] = useState('')
  const { testId } = useParams()

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
    </div>
  )
}
export default Test
