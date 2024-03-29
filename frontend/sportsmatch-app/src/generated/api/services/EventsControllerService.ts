/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EventDTO } from '../models/EventDTO';
import type { EventHistoryDTO } from '../models/EventHistoryDTO';
import type { Pageable } from '../models/Pageable';
import type { RequestEventDTO } from '../models/RequestEventDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class EventsControllerService {
    /**
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static addEvent(
        requestBody: EventDTO,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/event',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param id
     * @returns any OK
     * @throws ApiError
     */
    public static joinEvent(
        id: number,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/event/{id}/join',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @returns any OK
     * @throws ApiError
     */
    public static getEvent(
        id: number,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @returns any OK
     * @throws ApiError
     */
    public static deleteEvent(
        id: number,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/event/{id}',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param sportsIds
     * @returns any OK
     * @throws ApiError
     */
    public static getUpcomingEvents(
        sportsIds: Array<number>,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/upcoming-events',
            query: {
                'sportsIds': sportsIds,
            },
        });
    }
    /**
     * @param requestEventDto
     * @returns any OK
     * @throws ApiError
     */
    public static getNearbyEvents(
        requestEventDto: RequestEventDTO,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/nearby',
            query: {
                'requestEventDTO': requestEventDto,
            },
        });
    }
    /**
     * @param pageable
     * @returns EventHistoryDTO OK
     * @throws ApiError
     */
    public static getEventsHistory(
        pageable: Pageable,
    ): CancelablePromise<Array<EventHistoryDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/event-history',
            query: {
                'pageable': pageable,
            },
        });
    }
}
