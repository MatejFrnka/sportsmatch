import '../styles/SportEvent.css';

interface SportEventProps {
    Event: {
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

function SportEvent({ Event }: SportEventProps) {
    return (
        <>
            <div className="container-sm">
                <div className="event-card">
                    <div className="left">
                    <ul>
                        <li>📍{Event.location}</li>
                        <li>🏅{Event.minElo} - {Event.maxElo}</li>
                        <li>📆{Event.dateStart} to {Event.dateEnd}</li>
                    </ul>
                    </div>
                    <div className="right">
                    <h3>{Event.title}</h3>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SportEvent;
