/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserDTO } from './UserDTO';
export type EventHistoryDTO = {
    userScore?: number;
    opponentScore?: number;
    opponent?: UserDTO;
    dateOfTheMatch?: string;
    status?: EventHistoryDTO.status;
};
export namespace EventHistoryDTO {
    export enum status {
        MATCH = 'MATCH',
        MISMATCH = 'MISMATCH',
        WAITING_FOR_RATING = 'WAITING_FOR_RATING',
        INVALID_PLAYER = 'INVALID_PLAYER',
    }
}

