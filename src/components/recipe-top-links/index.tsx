import Link from "next/link";

export default function RecipeTopLinks({ recipeId, isAuthor } : { recipeId: string, isAuthor: boolean }) {
    return (
        <>
            {isAuthor ?
            <Link href={`/recipe/edit/${recipeId}`} className="ms-3">
                <i className="bi bi-pencil-square fs-5"></i>
            </Link>
            :
            <Link href="#" className="ms-3">
                <i className="bi bi-heart fs-5"></i>
            </Link>}
        </>
    );
}