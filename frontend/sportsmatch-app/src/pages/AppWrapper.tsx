import Navbar from '../components/Navbar'

// Define the prop types for the component
interface WrapperProps {
  child: React.ReactNode // Accepts any valid React child (component, element, etc.)
  showNavbar?: boolean // Boolean to control the display of the navbar
  backgroundColor?: string
}

const Wrapper: React.FC<WrapperProps> = ({ child, showNavbar = true }) => {
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
    </div>
  )
}

export default Wrapper
