/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlaceDTO } from './PlaceDTO';
export type EventDTO = {
    id?: number;
    dateStart: string;
    dateEnd: string;
    minElo: number;
    maxElo: number;
    title: string;
    player1Id?: number;
    player2Id?: number;
    sport: string;
    player1Name?: string;
    player2Name?: string;
    placeDTO: PlaceDTO;
};

