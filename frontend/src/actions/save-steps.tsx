"use server";

import { RecipeStepForm } from "@/interfaces/recipe-step";
import bobFetch from "@/lib/bob-fetch";

export async function saveSteps(authorization: string, recipeId: string, steps: RecipeStepForm[]) {
    const responseSteps = await bobFetch("/api/recipe-steps/save", {

        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": authorization
        },
        body: JSON.stringify({ recipeId: recipeId, steps: steps })

    }); return responseSteps;
}