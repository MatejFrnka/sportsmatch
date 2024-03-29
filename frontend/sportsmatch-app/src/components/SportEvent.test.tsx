import { render, screen } from '@testing-library/react'
import SportEvent from './SportEvent'

describe('SportEvent', async () => {
  const mockEvent = {
    id: 1,
    maxElo: 2000,
    minElo: 1800,
    dateEnd: '2024-01-27',
    dateStart: '2024-01-26',
    location: 'Test Location',
    title: 'Test Event',
    sport: 'Test Sport',
    playerOne: 'Player One',
    playerTwo: 'Player Two',
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
    const { playerTwo, ...mockEventWithoutPlayerTwo } = mockEvent

    render(<SportEvent event={mockEventWithoutPlayerTwo} />)

    // Assert that the rendered component does not contain the playerTwo information
    expect(screen.queryByText(`Player Two`)).not.toBeInTheDocument()
  })
})
