import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Wrapper from './pages/AppWrapper'
import { AllSportsList } from './pages/AllSportsList'
import Index from './pages/Index'
import PrivateRoute from './components/PrivateRoute'
import NotFound from './pages/NotFound'
import { OpenAPI } from './generated/api'
import UserInfo from './pages/UserInfo'
import UserRating from './pages/UserRating'
import HostEvent from './pages/HostEvent'
import { useEffect } from 'react'

function App() {
  OpenAPI.TOKEN = localStorage.getItem('token')!

  useEffect(() => {
    document.title = "SPORTS MINGLE"
  })

  return (
    <BrowserRouter>
      <Routes>
        {/* public routes */}
        <Route path="/login" element={<Wrapper child={<Login />} />} />
        <Route path="/signup" element={<Wrapper child={<Signup />} />} />
        <Route
          path="/"
          element={
            <Wrapper child={<Index />} acitvateCheckRatingModal={true} />
          }
        />
        <Route
          path="/all-sports"
          element={<Wrapper child={<AllSportsList />} />}
        />
        <Route path="/*" element={<NotFound />}></Route>

        {/* private routes */}
        <Route element={<PrivateRoute />}>
          <Route path="/app" element={<Wrapper child={<Home />} />} />
          <Route path="/user-info" element={<Wrapper child={<UserInfo />} />} />
          <Route
            path="/user/:id/ratings"
            element={<Wrapper child={<UserRating />} />}
          />
          <Route path="/user-info" element={<Wrapper child={<UserInfo />} />} />
          <Route
            path="/host-event"
            element={<Wrapper child={<HostEvent />} />}
          />
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
