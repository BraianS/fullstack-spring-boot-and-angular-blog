import { AuditDate } from "src/app/core/interface/core.interface";

export interface PostSummary {
    id:string,
    title:string,
    summary:string,
    createdAt:Date,
    categories: Array<IdAndTitleAndSlugResponse>
}

export interface IdAndTitleAndSlugResponse {
    id:number,
    title:string
    slug:string
}

export interface PostDetail {
    id:string,
    title:string,
    content:string,
    createdAt:Date,
    categories: Array<IdAndTitleAndSlugResponse>
    tags: Array<IdAndTitleAndSlugResponse>
    user: UserResponse
}

export interface UserResponse {
    id:string,
    username:string,
    name:string
}

export interface CommentResponse extends AuditDate {
    id:number,
    content:string,
    comments:CommentResponse[]
}

export interface TitlePayload {
    title:string
}

export interface ContentRequest {
    content:string
}

export class PostRequest {
    title:string = "";
    content:string = "";
    postStatus:Status = Status.PUBLIC;
    tags:string[] = [];
    categories:string[] = [];

    constructor(){}
}

export enum Status {
   PUBLIC = "Public",
   PRIVATE = "Private"
}