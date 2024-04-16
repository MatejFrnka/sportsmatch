import { render, screen } from '@testing-library/react'
import SportEvent from './SportEvent'
import { EventDTO } from '../generated/api'

describe('SportEvent', async () => {
  const mockEvent: EventDTO = {
    id: 1,
    maxElo: 2000,
    minElo: 1800,
    dateEnd: '27.01.2024 17:00',
    dateStart: '26.01.2024 15:00',
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
  it('renders the component with correct data', () => {
    render(<SportEvent event={mockEvent} />)

    // Assert that the rendered component contains the expected data
    expect(screen.getByText(`Test Location`)).toBeInTheDocument()
    expect(
      screen.getByText(`${mockEvent.minElo} - ${mockEvent.maxElo}`),
    ).toBeInTheDocument()
    expect(screen.getByText(`${mockEvent.dateStart}`)).toBeInTheDocument()
    expect(screen.getByText(`${mockEvent.dateEnd}`)).toBeInTheDocument()
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
