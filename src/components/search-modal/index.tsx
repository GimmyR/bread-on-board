"use client";

import "./style.css";
import { RecipeResponse } from "@/interfaces/recipe";
import bobFetch from "@/lib/bob-fetch";
import Link from "next/link"
import { useRouter } from "next/navigation";
import { ChangeEvent, useState } from "react";

export default function SearchModal() {
    const [search, setSearch] = useState<string>("");
    const [recipes, setRecipes] = useState<RecipeResponse[]>([]);
    const [error, setError] = useState<string | undefined>();
    const router = useRouter();

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setSearch(event.target.value);
        searchRecipe(event.target.value);
    };

    const searchRecipe = async (toSearch: string) => {
        const response = await bobFetch(`/api/recipes?s=${encodeURIComponent(toSearch)}`);
        response.status == 200 ? setRecipes([...response.data]) : setError(response.data);
    };

    const goToRecipe = (recipeId: string) => {
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