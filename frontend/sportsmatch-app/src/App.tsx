import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home'
import Test from './pages/Test'
import Login from './pages/Login'
import Signup from './pages/Signup'
import { AllSportsList } from './pages/AllSportsList'
import NewUser from './pages/NewUser'

function App() {
  return (
    <BrowserRouter>
      <div>
        <Routes>
          <Route path="login" element={<Login />} />
          <Route path="signup" element={<Signup />} />
          <Route path="allSports" element={<AllSportsList />} />
          <Route path="newuser" element={<NewUser />} />
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
