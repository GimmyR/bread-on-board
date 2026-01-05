import isAuthor from "@/actions/is-author";
import CreateRecipeForm from "@/components/create-recipe-form";
import { RecipeResponse } from "@/interfaces/recipe";
import bobFetch from "@/lib/bob-fetch";
import { Metadata } from "next";
import { redirect } from "next/navigation";

type Props = {
    params: Promise<{ id: string }>
};

export const metadata: Metadata = {
    title: "Edit recipe - Bread on Board",
    description: "Edit your recipe in Bread on Board web application.",
};

export default async function EditRecipePage({ params } : Props) {
    const { id } = await params;
    const author = await isAuthor(id);

    if(!author)
        redirect("/sign-in");

    const responseRecipe = await bobFetch(`/api/recipes/${id}`);
    const recipe: RecipeResponse | undefined = responseRecipe.status == 200 ? responseRecipe.data : undefined;

    return (
        <div className="container-fluid container-lg min-vh-100 bg-light py-5 mt-5">
            <CreateRecipeForm recipe={recipe}/>
        </div>
    );
}