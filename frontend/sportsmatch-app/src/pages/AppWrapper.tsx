import Navbar from '../components/Navbar'

// Define the prop types for the component
interface WrapperProps {
  child: React.ReactNode // Accepts any valid React child (component, element, etc.)
  showNavbar?: boolean // Boolean to control the display of the navbar
  backgroundColor?: string
}

const defaultBackgroundColor = '#e9e6e6' // Example default background color

const Wrapper: React.FC<WrapperProps> = ({
  child,
  showNavbar = true,
  backgroundColor = defaultBackgroundColor,
}) => {
  return (
    <div
      style={{
        backgroundColor: backgroundColor,
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
