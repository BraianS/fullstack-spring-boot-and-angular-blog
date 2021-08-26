export interface UserSummary{
    id:number;
    name:string;
    username:string;
}

export class CommentRequest{
    content:string =  "";    

    constructor(){
     
    }
}

export interface PasswordPayload {
    oldPassword:string,
    newPassword:string,
    confirmPassword:string
}

export interface NamePayload {
    name:string
}

export interface EmailPayload {
    password:string,
    email:string
}