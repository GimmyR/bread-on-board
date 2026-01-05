import { getToken } from "@/actions/get-token";
import RecipeSection from "@/components/recipe-section";
import bobFetch from "@/lib/bob-fetch";
import { Metadata } from "next";

export const metadata: Metadata = {
    title: "My recipes - Bread on Board",
    description: "List of recipes that your created in Bread on Board.",
};

export default async function MyRecipes() {
    const response = await bobFetch("/api/my-recipes", { 
        headers: { 
            "Authorization": `Bearer ${await getToken()}` 
        } 
    });

    return (
        <RecipeSection recipes={response.data}/>
    );
}