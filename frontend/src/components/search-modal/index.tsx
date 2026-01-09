"use client";

import searchRecipe from "@/actions/search-recipe";
import "./style.css";
import { RecipeResponse } from "@/interfaces/recipe";
import Link from "next/link"
import { useRouter } from "next/navigation";
import { ChangeEvent, useState } from "react";

export default function SearchModal() {
    const [search, setSearch] = useState<string>("");
    const [recipes, setRecipes] = useState<RecipeResponse[]>([]);
    const [error, setError] = useState<string | undefined>();
    const router = useRouter();

    const handleChange = async (event: ChangeEvent<HTMLInputElement>) => {
        setSearch(event.target.value);
        const response = await searchRecipe(event.target.value);

        if(response.status == 200)
            setRecipes([...response.data]);
        else setError(response.data);
    };

    const goToRecipe = (recipeId: number) => {
        router.push(`/recipe/${recipeId}`);
    };

    return (
        <div id="searchModal" className="modal" tabIndex={-1}>
            <div className="modal-dialog modal-lg modal-fullscreen-md-down">
                <div className="modal-content rounded-1">
                    <div className="modal-header">
                        <input type="text" value={search} onChange={handleChange} id="search-modal-input" className="form-control me-1" placeholder="Search"/>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body px-4" style={{ height: 750 }}>
                        {error && <h1 className="text-danger text-center fs-3">{error}</h1>}
                        {recipes.map((recipe, index) =>
                            <div key={recipe.id} className={`d-flex flex-row align-items-center border-bottom${index == 0 ? " border-top" : ""}`}>
                                <Link href="#" onClick={() => goToRecipe(recipe.id)} className=" col-12 fs-6 text-truncate my-auto py-2 text-decoration-none" data-bs-dismiss="modal">
                                    <span className="text-success me-3">{ recipe?.title }</span> { recipe?.ingredients }
                                </Link>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
}