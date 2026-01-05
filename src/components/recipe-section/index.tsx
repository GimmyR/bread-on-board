import Card from "@/components/card";
import { RecipeResponse } from "@/interfaces/recipe";

export default function RecipeSection({ recipes } : { recipes: RecipeResponse[] }) {
    return (
        <div className="container-fluid container-lg bg-light px-5 py-5" style={{ minHeight: '700px' }}>
            <div className="row row-cols-1 row-cols-lg-4 gy-4">
                {recipes.map(recipe =>
                <div key={recipe.id}>
                    <Card recipe={recipe}/>
                </div>)}
            </div>
        </div>
    )
}