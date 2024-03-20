import Spinner from 'react-bootstrap/Spinner'

function LoadingSpinner() {
  return (
    <>
      <div className="row load-anim">
        <div className="col">
          <div className="row">
            <div className="col d-flex justify-content-center">
              <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
              </Spinner>
            </div>
          </div>
          <div className="row load-msg">
            <div className="col d-flex justify-content-center">
              <h5>Awaiting Nearby Events</h5>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default LoadingSpinner
