export interface SignInPayload {
    name: string,
    username: string,
    password: string,
    email: string
}

export interface LoginPayload {
    usernameOrEmail: string,
    password: string
}