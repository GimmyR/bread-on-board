import { getToken } from "@/actions/get-token";
import RecipeSection from "@/components/recipe-section";
import bobFetch from "@/lib/bob-fetch";
import { Metadata } from "next";
import { redirect } from "next/navigation";

export const metadata: Metadata = {
    title: "My recipes - Bread on Board",
    description: "List of recipes that your created in Bread on Board.",
};

export default async function MyRecipes() {
    const token = await getToken();

    if(!token)
        redirect("/sign-in");

    const response = await bobFetch("/api/my-recipes", { 
        headers: { 
            "Authorization": `Bearer ${token}` 
        } 
    });

    return (
        <>
            {response.status == 200 && <RecipeSection recipes={response.data}/>}
        </>
    );
}