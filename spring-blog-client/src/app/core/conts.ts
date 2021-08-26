import { Observable } from "rxjs";

export enum PostsEndPoints {
    GET_POSTS_ORDER_BY_CREATED_AT_DESC = "/post/",
    FIND_BY_ID = "/post/",
    SAVE = "/post/",
    DELETE_BY_ID = "/post/"
}

export enum TagsEndPoints {
    TAG = "/tag/",
    SEARCH_TITLE = "/tag/search"
}

export enum CategoryEndPoints {
    CATEGORY = "/category/",
    SEARCH_TITLE = "/category/search"
}

export enum AuthenticateEndPoints{
    SIGN_IN = "/auth/signin",
    SIGN_UP = "/auth/signup"
}

export enum UserEndPoints {
    USER = "/user/",
    USER_POSTS = "/user/submitted",
    CHECK_EMAIL_AVAILABILITY = "/user/checkEmailAvailability",
    CHECK_USERNAME_AVAILABILITY = "/user/checkUsernameAvailability"
}

export  enum CommentEndPoints{
    COMMENT ="/comment/"
}

export interface CrudOperations {
    findById(id:number): Observable<any>;
    save(body: any): Observable<any>;
    deleteById(id:number): Observable<any>;
    update(id:number,body: any): Observable<any>;
    findAll(): Observable<any>;
}

export enum Pagination {
   PAGE_SIZE = 20,
   PAGE_NUMBER = 0
}