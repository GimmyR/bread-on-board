import Link from "next/link";
import "./style.css";
import { Metadata } from "next";
import RecipeNotFound from "@/components/recipe-not-found";
import bobFetch from "@/lib/bob-fetch";
import imageURL from "@/lib/image-url";
import { RecipeResponse } from "@/interfaces/recipe";
import RecipeTopLinks from "@/components/recipe-top-links";
import RecipeSteps from "@/components/recipe-steps";
import isAuthor from "@/actions/is-author";

type Props = {
    params: Promise<{ id: string }>
};

export const metadata: Metadata = {
    title: "Get one recipe - Bread on Board",
    description: "Describe a recipe with its author, ingredients and steps.",
};

export default async function RecipePage({ params } : Props) {
    const { id } = await params;
    const responseRecipe = await bobFetch(`/api/recipes/${id}`);
    const recipe: RecipeResponse | null = responseRecipe.status == 200 ? responseRecipe.data : null;
    const author: boolean =  recipe ? await isAuthor(recipe.id) : false;

    return (
        <div className="container-fluid container-lg bg-light min-vh-100 pt-5">
            {recipe != null ?
            <div className="pt-5">
                <div className="d-flex flex-column align-items-center px-3">
                    <div className="d-flex flex-row align-items-center">
                        <h1 className="text-success mb-1">{recipe.title}</h1>
                        <RecipeTopLinks recipeId={recipe.id} isAuthor={author}/>
                    </div>
                    <h3 className="fs-5 mb-5">
                        par <Link href={`/account/${recipe.account.id}`} className="text-light-green text-decoration-none">{recipe.account.username}</Link>
                    </h3>
                    <img src={imageURL(recipe.image)} alt={recipe.title} className="img-fluid col-12 col-lg-4 mb-5"/>
                    <div className="d-flex flex-column col-12 col-lg-8 mt-2 mb-3">
                        <h2 className="text-success text-center text-decoration-underline fs-4 mb-3">Ingredients</h2>
                        <p className="text-center">{recipe.ingredients}</p>
                    </div>
                    <div className="d-flex flex-column align-items-center col-12 col-lg-8">
                        <h2 className="text-success text-decoration-underline fs-4">Steps to follow</h2>
                        <RecipeSteps steps={recipe.steps}/>
                    </div>
                </div>
            </div>
            :
            <div className="container-fluid bg-light vh-100">
                <RecipeNotFound/>
            </div>}
        </div>
    );
}