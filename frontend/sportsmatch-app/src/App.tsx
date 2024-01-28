import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home'
import Login from './pages/Login'
import Test from './pages/Test'

function App() {
  return (
    <BrowserRouter>
      <div>
        <Routes>
          <Route path="login" element={<Login />} />
          <Route path="test" element={<Test />}>
            <Route index element={<Test />} />
            <Route path=":testId" element={<Test />} />
          </Route>
          <Route path="/" element={<Home />} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

export default App
