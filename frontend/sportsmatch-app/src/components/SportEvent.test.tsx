import { render, screen } from '@testing-library/react'
import SportEvent from './SportEvent'
import { EventDTO } from '../generated/api'

describe('SportEvent', async () => {
  const mockEvent: EventDTO = {
    id: 1,
    maxElo: 2000,
    minElo: 1800,
    dateEnd: [2024, 9, 20, 17, 0],
    dateStart: [2024, 9, 20, 15, 0],
    placeDTO: {
      name: 'Test Location',
      address: 'address',
      latitude: 51,
      longitude: 30,
    },
    title: 'Test Event',
    sport: 'Test Sport',
    player1Name: 'Player One',
    player2Name: 'Player Two',
  }

  const formatDate = (dateArray: string) => {
    const [year, month, day, hours, minutes] = dateArray
    const formattedDate = `${day.toString().padStart(2, '0')}-${month
      .toString()
      .padStart(
        2,
        '0',
      )}-${year} ${hours}:${minutes.toString().padStart(2, '0')}`
    return formattedDate
  }

  it('renders the component with correct data', () => {
    render(<SportEvent event={mockEvent} />)

    // Convert dateStart and dateEnd arrays to formatted strings
    const formattedDateStart = formatDate(mockEvent.dateStart)
    const formattedDateEnd = formatDate(mockEvent.dateEnd)

    console.log('formattedDateStart:', formattedDateStart)
    console.log('formattedDateEnd:', formattedDateEnd)

    // Assert that the rendered component contains the expected data
    expect(screen.getByText(`Test Location`)).toBeInTheDocument()
    expect(
      screen.getByText(`${mockEvent.minElo} - ${mockEvent.maxElo}`),
    ).toBeInTheDocument()
    expect(screen.getByText(formattedDateStart)).toBeInTheDocument()
    expect(screen.getByText(formattedDateEnd)).toBeInTheDocument()
    expect(screen.getByText(mockEvent.title)).toBeInTheDocument()

    // Check for the presence of react-icons
    expect(screen.getByTestId('luMapPin')).toBeInTheDocument()
    expect(screen.getByTestId('luMedal')).toBeInTheDocument()
    expect(screen.getByTestId('luCalendarCheck')).toBeInTheDocument()
    expect(screen.getByTestId('luCalendarX')).toBeInTheDocument()
  })

  it('renders the component without playerTwo when it is not provided', () => {
    const { player2Id, ...mockEventWithoutPlayerTwo } = mockEvent

    render(<SportEvent event={mockEventWithoutPlayerTwo} />)

    // Assert that the rendered component does not contain the playerTwo information
    expect(screen.queryByText(`Player Two`)).not.toBeInTheDocument()
  })
})
