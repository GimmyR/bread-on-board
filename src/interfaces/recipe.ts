import { AccountResponse } from "./account";

export interface RecipeResponse {
    id: string,
    title: string,
    ingredients: string,
    image: string,
    account: AccountResponse
}