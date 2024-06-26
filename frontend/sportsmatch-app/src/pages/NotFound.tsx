import { useNavigate } from 'react-router-dom'
import '../styles/Sport.css'

export default function NotFound() {
  const mainPageURL = '/'
  const navigate = useNavigate()

  return (
    <>
      <div className="container-sm">
        <div className="row">
          <div className="col">
            <h1>Page not found</h1>
          </div>
        </div>
        <div className="row">
          <div className="col">
            <button type="submit" onClick={() => navigate(mainPageURL)}>
              Go to home screen
            </button>
          </div>
        </div>
      </div>
    </>
  )
}
