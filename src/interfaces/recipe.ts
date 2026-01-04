import { AccountResponse } from "./account";
import { RecipeStepResponse } from "./recipe-step";

export interface RecipeResponse {
    id: string,
    title: string,
    ingredients: string,
    image: string,
    account: AccountResponse,
    steps: RecipeStepResponse[]
}