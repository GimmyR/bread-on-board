"use server";

import bobFetch from "@/lib/bob-fetch";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function removeRecipe(id: number | undefined) {
    if(!id)
        return { status: 400, data: "Recipe ID is missing" };

    const cookieStore = await cookies();
    const response = await bobFetch(`/api/recipe/delete/${id}`, true, { 
        method: "POST",
        headers: {
            "Authorization": `Bearer ${cookieStore.get("token")?.value as string}`
        }
    });

    if(response.status == 201)
        redirect("/");

    else return response;
}