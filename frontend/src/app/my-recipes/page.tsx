import { getToken } from "@/actions/get-token";
import RecipeSection from "@/components/recipe-section";
import bobFetch from "@/lib/bob-fetch";
import { Metadata } from "next";
import { redirect } from "next/navigation";

export const metadata: Metadata = {
    title: "Your recipes - Bread on Board",
    description: "List of recipes that your created in Bread on Board.",
};

export default async function YourRecipesPage() {
    const token = await getToken();

    if(!token)
        redirect("/sign-in");

    const response = await bobFetch("/api/my-recipes", true, { 
        headers: { 
            "Authorization": `Bearer ${token}` 
        } 
    });

    return (
        <div className="container-fluid container-lg min-vh-100 bg-light px-0 pt-5">
            <div className="pt-5 px-0 px-md-5">
                <h1 className="text-center text-decoration-underline fs-3 mb-lg-4 mb-4">Your recipes</h1>
                {response.status == 200 && <RecipeSection recipes={response.data}/>}
            </div>
        </div>
    );
}