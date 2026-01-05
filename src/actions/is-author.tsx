"use server";

import bobFetch from "@/lib/bob-fetch";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function isAuthor(recipeId: string) {
    const cookieStore = await cookies();
    const response = await bobFetch(`/api/recipe/author/${recipeId}`, {
        headers: {
            "Authorization": `Bearer ${cookieStore.get("token")?.value as string}`
        }
    });

    if((response.status == 200 && !response.data) || response.status != 200)
        return redirect("/");
}