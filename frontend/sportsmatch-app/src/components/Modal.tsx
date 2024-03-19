import { ReactNode } from 'react'
import '../styles/Modal.css'

interface ModalProps {
  children?: ReactNode // React child with content of the popup window
  isOpen: boolean // boolean that controlls if modal is displayed
  preventClosing: boolean // if false popup window closes after clicking on the overlay
  toggle: () => void // a callback function to controll the value of isOpen
}
export default function Modal(p: ModalProps) {
  return (
    <>
      {!p.preventClosing
        ? p.isOpen && (
            <div className="modal-overlay" onClick={p.toggle}>
              <div className="modal-box" onClick={(e) => e.stopPropagation()}>
                {p.children}
              </div>
            </div>
          )
        : p.isOpen && (
            <div className="modal-overlay">
              <div className="modal-box">{p.children}</div>
            </div>
          )}
    </>
  )
}
