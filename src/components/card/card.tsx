import Link from "next/link"

export default function Card() {
    return (
        <div className="card col p-0">
            <img className="card-img-top" alt="recipe image" height="200"/>
            <div className="card-body">
            <p className="card-text">Recipe title</p>
            <Link href="#" className="btn btn-success">Voir la recette</Link>
            </div>
        </div>
    )
}