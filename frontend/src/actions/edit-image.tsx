"use server";

import { RecipeResponse } from "@/interfaces/recipe";
import bobFetch from "@/lib/bob-fetch";
import { saveSteps } from "@/actions/save-steps";
import { RecipeStepForm } from "@/interfaces/recipe-step";

export default async function editImage(formData: FormData, response: { status: number, data: RecipeResponse }, authorization: string, steps: RecipeStepForm[]) {
    const formImage = new FormData();
    
    if(formData.get("image") instanceof File) {

        formImage.append("image", formData.get("image") as File);
        const recipe: RecipeResponse = response.data;
        const responseImage = await bobFetch(`/api/recipe/edit-image/${recipe.id}`, true, {
            method: "POST",
            headers: { "Authorization": authorization },
            body: formImage
        });

        if(responseImage.status == 201)
            return await saveSteps(authorization, recipe.id, steps);
            
        else return responseImage;

    } else return { status: 400, data: "The image of the recipe is missing." }
}