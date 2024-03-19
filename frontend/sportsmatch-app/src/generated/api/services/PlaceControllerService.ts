/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlaceDTO } from '../models/PlaceDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PlaceControllerService {
    /**
     * @param requestBody
     * @returns string OK
     * @throws ApiError
     */
    public static addNewPlace(
        requestBody: PlaceDTO,
    ): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/places/add',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param name
     * @returns PlaceDTO OK
     * @throws ApiError
     */
    public static searchPlaces(
        name?: string,
    ): CancelablePromise<Array<PlaceDTO>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/places/search',
            query: {
                'name': name,
            },
        });
    }
}
