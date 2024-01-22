import '../styles/SportEvent.css';

interface SportEventProps {
    event: {
        id: number;
        maxElo: number;
        minElo: number;
        dateEnd: string;
        dateStart: string;
        location: string;
        title: string;
        sport: string;
        playerOne: string;
        playerTwo?: string;

    }
}

function SportEvent({ event }: SportEventProps) {
    return (
        <>
            <div className="container-sm">
                <div className="event-card">
                    <div className="left">
                    <ul>
                        <li>📍{event.location}</li>
                        <li>🏅{event.minElo} - {event.maxElo}</li>
                        <li>📆{event.dateStart} to {event.dateEnd}</li>
                    </ul>
                    </div>
                    <div className="right">
                    <h3>{event.title}</h3>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SportEvent;
