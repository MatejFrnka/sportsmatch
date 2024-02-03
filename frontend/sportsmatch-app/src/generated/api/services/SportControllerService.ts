/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pageable } from '../models/Pageable';
import type { SportDTO } from '../models/SportDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class SportControllerService {
    /**
     * @param pageable
     * @returns SportDTO OK
     * @throws ApiError
     */
    public static getSports(
        pageable: Pageable,
    ): CancelablePromise<Array<SportDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/sports/all',
            query: {
                'pageable': pageable,
            },
        });
    }
}
