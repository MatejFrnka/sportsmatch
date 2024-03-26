/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { RatingDTO } from '../models/RatingDTO';
import type { UserRatingDTO } from '../models/UserRatingDTO';
import type { UserRatingStatsDTO } from '../models/UserRatingStatsDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class RatingControllerService {
    /**
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static addRating(
        requestBody: RatingDTO,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/rating/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param id
     * @returns UserRatingStatsDTO default response
     * @throws ApiError
     */
    public static getSummary(
        id: number,
    ): CancelablePromise<UserRatingStatsDTO> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/rating/{id}/summary',
            path: {
                'id': id,
            },
        });
    }
    /**
     * @param id
     * @param page Zero-based page index (0..N)
     * @param size The size of the page to be returned
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
     * @returns UserRatingDTO OK
     * @throws ApiError
     */
    public static getAllByUser(
        id: number,
        page?: number,
        size: number = 20,
        sort?: Array<string>,
    ): CancelablePromise<Array<UserRatingDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/rating/{id}/all',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static checkRating(): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/rating/check',
        });
    }

}
