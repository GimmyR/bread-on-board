import Card from "@/components/card";
import { RecipeResponse } from "@/interfaces/recipe";

export default function RecipeSection({ recipes } : { recipes: RecipeResponse[] }) {
    return (
        <div className="d-flex flex-row flex-wrap justify-content-center justify-content-md-start">
            {recipes.map(recipe =>
                <div key={recipe.id} className="col-10 col-sm-8 col-md-4 col-lg-4 col-xxl-3 p-3">
                    <Card recipe={recipe}/>
                </div>
            )}
        </div>
    )
}