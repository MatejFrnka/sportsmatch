import { render, screen } from "@testing-library/react"
import SportEvent from "./SportEvent";

describe("SportEvent", async () => {
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
      };
    it('renders the component with correct data', () => {
        render(<SportEvent event={mockEvent} />);
    
        // Assert that the rendered component contains the expected data
        expect(screen.getByText(`📍${mockEvent.location}`)).toBeInTheDocument();
        expect(screen.getByText(`🏅${mockEvent.minElo} - ${mockEvent.maxElo}`)).toBeInTheDocument();
        expect(screen.getByText(`📆${mockEvent.dateStart} to ${mockEvent.dateEnd}`)).toBeInTheDocument();
        expect(screen.getByText(mockEvent.title)).toBeInTheDocument();
      });
    
      it('renders the component without playerTwo when it is not provided', () => {
        const { playerTwo, ...mockEventWithoutPlayerTwo } = mockEvent;
    
        render(<SportEvent event={mockEventWithoutPlayerTwo} />);
    
        // Assert that the rendered component does not contain the playerTwo information
        expect(screen.queryByText(`Player Two`)).not.toBeInTheDocument();
      });
    
});