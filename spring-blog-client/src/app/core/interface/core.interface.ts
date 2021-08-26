export interface AuditDate {
    createdAt:Date,
    createdBy:string,
    updatedAt:Date,
    updatedBy:string
}

export interface IdAndTitleAndSlugResponse {
    id:number,
    title:string
    slug:string
}

export interface AppResponse {
    success:boolean,
    message:string
}

export class BaseError {
    status:string = '';
    timestamp:string = '';
    message: string = '';
    detail: string = '';
    details: string = '';
}

export interface JwtAuthenticatedResponse {
    username: string,
    authorities: string[],
    accessToken: string,
    tokenType: string
}

export enum RoleNames {
    ROLE_USER = "ROLE_USER",
    ROLE_ADMIN = "ROLE_ADMIN"
}