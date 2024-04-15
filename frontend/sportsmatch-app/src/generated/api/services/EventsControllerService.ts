/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { EventDTO } from '../models/EventDTO';
import type { EventHistoryDTO } from '../models/EventHistoryDTO';
import type { HostEventDTO } from '../models/HostEventDTO';
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
        requestBody: HostEventDTO,
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
     * @returns EventDTO OK
     * @throws ApiError
     */
    public static getUpcomingMatches(): CancelablePromise<Array<EventDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/upcoming-matches',
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
     * @param sportsName
     * @param longitude
     * @param latitude
     * @param page Zero-based page index (0..N)
     * @param size The size of the page to be returned
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
     * @returns EventDTO OK
     * @throws ApiError
     */
    public static getNearbyEvents(
        sportsName?: Array<string>,
        longitude?: number,
        latitude?: number,
        page?: number,
        size: number = 20,
        sort?: Array<string>,
    ): CancelablePromise<Array<EventDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/nearby',
            query: {
                'sportsName': sportsName,
                'longitude': longitude,
                'latitude': latitude,
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
    /**
     * @param page Zero-based page index (0..N)
     * @param size The size of the page to be returned
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
     * @returns EventHistoryDTO OK
     * @throws ApiError
     */
    public static getEventsHistory(
        page?: number,
        size: number = 20,
        sort?: Array<string>,
    ): CancelablePromise<Array<EventHistoryDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/event/event-history',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
}
