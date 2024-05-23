/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SportDTO } from '../models/SportDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SportControllerService {
    /**
     * @param page Zero-based page index (0..N)
     * @param size The size of the page to be returned
     * @param sort Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
     * @returns SportDTO OK
     * @throws ApiError
     */
    public static getSports(
        page?: number,
        size: number = 20,
        sort?: Array<string>,
    ): CancelablePromise<Array<SportDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/sports/all',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
}
