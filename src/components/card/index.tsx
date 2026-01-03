import Link from "next/link"

export default function Card({ recipe } : { recipe: Recipe }) {
    return (
        <div className="card col p-0">
            <img src={"http://localhost:8080/images/" + recipe.image} className="card-img-top" alt="recipe image" height="200"/>
            <div className="card-body">
                <p className="card-text">{ recipe.title }</p>
                <Link href="#" className="btn btn-success">Voir la recette</Link>
            </div>
        </div>
    )
}