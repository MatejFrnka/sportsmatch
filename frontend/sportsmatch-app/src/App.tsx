import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import Home from './pages/Home'
import Test from './pages/Test'
import Login from './pages/Login'
import Signup from './pages/Signup'
import Wrapper from './pages/AppWrapper'
import { AllSportsList } from './pages/AllSportsList'
import NewUser from './pages/NewUser'
import UserPage from './pages/UserPage'
import Index from './pages/Index'
import PrivateRoute from './components/PrivateRoute'
import NotFound from './pages/NotFound'
import { OpenAPI } from './generated/api'

function App() {
  const getToken = async () => {
    const result = localStorage.getItem('token')
    return result
  }
  getToken().then((res) => {
    OpenAPI.TOKEN = res!
  })

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Wrapper child={<Login />} />} />
        <Route path="/signup" element={<Wrapper child={<Signup />} />} />
        <Route
          path="/allSports"
          element={<Wrapper child={<AllSportsList />} />}
        />
        <Route path="/newuser" element={<Wrapper child={<NewUser />} />} />
        <Route element={<PrivateRoute />}>
          <Route path="/index" element={<Wrapper child={<Index />} />} />
          <Route path="/user" element={<UserPage />}></Route>
        </Route>
        <Route path="/test" element={<Test />}>
          <Route index element={<Test />} />
          <Route path=":testId" element={<Test />} />
        </Route>
        <Route path="/" element={<Wrapper child={<Home />} />} />
        <Route path="/*" element={<NotFound />}></Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
