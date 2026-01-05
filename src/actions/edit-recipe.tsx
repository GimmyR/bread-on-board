"use server";

import { RecipeStepForm } from "@/interfaces/recipe-step";
import bobFetch from "@/lib/bob-fetch";
import { cookies } from "next/headers";
import editImage from "@/actions/edit-image";
import { saveSteps } from "./save-steps";

export default async function editRecipe(recipeId: string, formData: FormData, steps: RecipeStepForm[]) {
    const cookieStore = await cookies();
    const authorization = cookieStore.get("token") ? `Bearer ${cookieStore.get("token")?.value}` : "";
    const form = {
        title: formData.get("title") as string,
        ingredients: formData.get("ingredients") as string
    };

    const response = await bobFetch(`/api/recipe/edit/${recipeId}`, { 
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": authorization
        },
        body: JSON.stringify(form)
    });

    if(response.status == 201) {
        
        if(!formData.get("image"))
            return await editImage(formData, response, authorization, steps);

        else return await saveSteps(authorization, recipeId, steps);

    } else return response;
}