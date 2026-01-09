"use server";

import bobFetch from "@/lib/bob-fetch";

export default async function searchRecipe(search: string) {
    return await bobFetch(`/api/recipes?s=${encodeURIComponent(search)}`, true);
}