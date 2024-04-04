import Match from '../components/Match'
import EventHistoryItem from '../components/EventHistoryItem'
import { useEffect, useState } from 'react'
import {
  EventDTO,
  EventHistoryDTO,
  EventsControllerService,
  ExSecuredEndpointService,
  OpenAPI,
  UserInfoDTO,
} from '../generated/api'

function Home() {
  const page = 0
  const size = 4
  const sort = ['dateOfTheMatch,desc']
  const [eventsHistory, setEventsHistory] = useState<EventHistoryDTO[]>([])
  const [currentUser, setCurrentUser] = useState<UserInfoDTO>()
  const [upcomingMatch, setUpcomingMatch] = useState<EventDTO[]>([])

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const init = async () => {
      const fetchCurrentUser = await ExSecuredEndpointService.getUserMainPage()
      if (fetchCurrentUser) {
        setCurrentUser(fetchCurrentUser as UserInfoDTO)
      }
      if (currentUser && currentUser.sports) {
        const sportIds = currentUser.sports.map((s) => s.id)
        const fetchUpcomingEvents =
          await EventsControllerService.getUpcomingEvents(sportIds as number[])
        if (fetchUpcomingEvents) {
          setUpcomingMatch(fetchUpcomingEvents as EventDTO[])
        }
      }
    }
    init()
  }, [currentUser])

  useEffect(() => {
    OpenAPI.TOKEN = localStorage.getItem('token')!
    const fetchEvents = async () => {
      const response = await EventsControllerService.getEventsHistory(
        //did not work with sorting, has to be checked
        page,
        size,
        sort,
      )
      if (response && response.length > 0) {
        setEventsHistory(response)
      }
    }
    fetchEvents()
  })

  //TO DO: render ca three EventHistoryItems
  //TO DO: add an alternative view when there are no past events
  //TO DO: add an alternative view when there are no upcoming events

  return (
    <>
      <Match event={upcomingMatch[0]} />
      <EventHistoryItem eventHistoryDTO={eventsHistory[0]} />
    </>
  )
}

export default Home
