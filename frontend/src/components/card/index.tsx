import "./style.css";
import { RecipeResponse } from "@/interfaces/recipe"
import imageURL from "@/lib/image-url"
import Link from "next/link"

export default function Card({ recipe } : { recipe: RecipeResponse }) {
    return (
        <div className="card p-0">
            <div className="image-card" style={{ backgroundImage: `url(${imageURL(recipe.image)})` }}></div>
            <div className="card-body">
                <p className="card-text">{ recipe.title }</p>
                <Link href={`/recipe/${recipe.id}`} className="btn btn-success">View details</Link>
            </div>
        </div>
    )
}