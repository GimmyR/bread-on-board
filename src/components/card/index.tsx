import { RecipeResponse } from "@/interfaces/recipe"
import imageURL from "@/lib/image-url"
import Link from "next/link"

export default function Card({ recipe } : { recipe: RecipeResponse }) {
    return (
        <div className="card col p-0">
            <img src={imageURL(recipe.image)} className="card-img-top" alt="recipe image" height="200"/>
            <div className="card-body">
                <p className="card-text">{ recipe.title }</p>
                <Link href={`/recipe/${recipe.id}`} className="btn btn-success">View details</Link>
            </div>
        </div>
    )
}