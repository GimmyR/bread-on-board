"use server";

import { RecipeStepForm } from "@/interfaces/recipe-step";
import bobFetch from "@/lib/bob-fetch";
import { cookies } from "next/headers";
import editImage from "@/actions/edit-image";

export default async function createRecipe(formData: FormData, steps: RecipeStepForm[]) {
    const cookieStore = await cookies();
    const authorization = cookieStore.get("token") ? `Bearer ${cookieStore.get("token")?.value}` : "";
    const form = {
        title: formData.get("title") as string,
        ingredients: formData.get("ingredients") as string
    };

    const response = await bobFetch("/api/recipe/create", true, { 
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": authorization
        },
        body: JSON.stringify(form)
    });

    if(response.status == 201)
        return await editImage(formData, response, authorization, steps);
        
    else return response;
}