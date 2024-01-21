import React from "react";

interface SportEventProps {
    Event: {
        dateStart: string;
        dateEnd: string;
        location: string;
        minElo: number;
        maxElo: number;
        title: string;
        playerOne: string;
        playerTwo?: string;
    }
}

function SportEvent({Event}: SportEventProps) {    
    return (
        <>
            <p>{Event.title}</p>
            <p>{Event.location}</p>
            <p>{Event.minElo} - {Event.maxElo}</p>
            <p>{Event.dateStart} - {Event.dateEnd}</p>
            <p>{Event.playerOne}</p>
        </>
    );
}

export default SportEvent;
