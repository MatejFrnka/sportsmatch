import '../styles/SportEvent.css';

interface SportEventProps {
    Event: {
        dateStart: string;
        dateEnd: string;
        location: string;
        minElo: number;
        maxElo: number;
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
                        <li>📆{Event.dateStart} : {Event.dateEnd}</li>
                    </ul>
                    </div>
                    <div className="right">
                    <h3>{Event.sport}</h3>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SportEvent;
