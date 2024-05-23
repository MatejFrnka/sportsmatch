import CheckRatingModal from '../components/CheckRatingModal'
import Navbar from '../components/Navbar'

// Define the prop types for the component
interface WrapperProps {
  child: React.ReactNode // Accepts any valid React child (component, element, etc.)
  showNavbar?: boolean // Boolean to control the display of the navbar
  backgroundColor?: string
  acitvateCheckRatingModal?: boolean //boolean to control if the checkRatingComponent is activated
}

const Wrapper: React.FC<WrapperProps> = ({
  child,
  showNavbar = true,
  acitvateCheckRatingModal = false,
}) => {
  return (
    <div
      style={{
        height: '100%',
      }}
    >
      {showNavbar && <Navbar></Navbar>}
      <div className={'container-sm'} style={{ height: 'calc(100% - 60px)' }}>
        {child}
      </div>
      {acitvateCheckRatingModal && <CheckRatingModal></CheckRatingModal>}
    </div>
  )
}

export default Wrapper
