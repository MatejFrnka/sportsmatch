/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserInfoDTO } from '../models/UserInfoDTO';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class UserControllerService {
    /**
     * @param requestBody
     * @returns any OK
     * @throws ApiError
     */
    public static updateInfo(
        requestBody: UserInfoDTO,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/user/update',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @param username
     * @returns any OK
     * @throws ApiError
     */
    public static getUser(
        username: string,
    ): CancelablePromise<Record<string, any>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/user/get/{username}',
            path: {
                'username': username,
            },
        });
    }
}
