import { ReactNode } from 'react'
import '../styles/Modal.css'

interface ModalProps {
  children?: ReactNode
  isOPen: boolean
  preventClosing: boolean
  toggle: () => void
}
export default function Modal(p: ModalProps) {
  return (
    <>
      {!p.preventClosing
        ? p.isOPen && (
            <div className="modal-overlay" onClick={p.toggle}>
              <div className="modal-box" onClick={(e) => e.stopPropagation()}>
                {p.children}
              </div>
            </div>
          )
        : p.isOPen && (
            <div className="modal-overlay">
              <div className="modal-box">{p.children}</div>
            </div>
          )}
    </>
  )
}
