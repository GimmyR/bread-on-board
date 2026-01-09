"use server";

import bobFetch from "@/lib/bob-fetch";
import { getToken } from "./get-token";

export default async function isAuthor(recipeId: number) {
    const token = await getToken();

    if(!token)
        return false;

    const response = await bobFetch(`/api/recipe/author/${recipeId}`, true, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    });

    if(response.status != 200)
        return false;

    else return response.data;
}