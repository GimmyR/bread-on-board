import { AccountResponse } from "./account";
import { RecipeStepResponse } from "./recipe-step";

export interface RecipeResponse {
    id: number,
    title: string,
    ingredients: string,
    image: string,
    account: AccountResponse,
    steps: RecipeStepResponse[]
}