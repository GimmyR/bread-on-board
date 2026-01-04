"use client";

import createRecipe from "@/actions/create-recipe";
import { RecipeStepForm } from "@/interfaces/recipe-step";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";

export default function CreateRecipeForm({ title } : { title: string }) {
    const [error, setError] = useState<string | undefined>();
    const [steps, setSteps] = useState<RecipeStepForm[]>([ { key: Date.now(), text: "" } ]);
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();

    const addStepRightAfter = (index: number) => {
        steps.splice(index + 1, 0, { key: Date.now(), text: "" });
        setSteps([...steps]);
    };

    const removeStepAt = (index: number) => {
        steps.splice(index, 1);
        setSteps([...steps]);
    };

    const handleChange = (event: ChangeEvent<HTMLTextAreaElement>, index: number) => {
        steps[index] = {...steps[index], text: event.target.value};
        setSteps([...steps]);
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        setIsLoading(true);
        event.preventDefault();
        const formData = new FormData(event.currentTarget);
        const result = await createRecipe(formData, steps);

        if(result.status == 201)
            router.push(`/recipe/${result.data.id}`);

        else { 
            setError(result.data);
        }
    };

    return (
        <form className="d-flex flex-column col-12 col-lg-6 mx-auto mt-0 mt-lg-5 mb-0 mb-lg-5" onSubmit={handleSubmit}>
            {error != null && <div className="alert alert-danger mb-4" role="alert">
                {error}
            </div>}
            <div className="mb-4">
                <label htmlFor="title" className="form-label text-success fw-bold">Titre de la recette</label>
                <input type="text" className="form-control" name="title" id="title" defaultValue={title}/>
            </div>
            <div className="mb-4">
                <label htmlFor="image" className="form-label text-success fw-bold">Image de la recette</label>
                <input type="file" id="image" className="form-control" accept="image/*" name="image"/>
            </div>
            <div className="mb-4">
                <label htmlFor="ingredients" className="form-label text-success fw-bold">Les ingrédients de la recette</label>
                <textarea id="ingredients" name="ingredients" className="form-control"></textarea>
            </div>
            {steps.map((step, index) => 
            <div className="d-flex flex-column mb-4" key={step.key}>
                <label htmlFor={`step-${index + 1}`} className="d-flex flex-row align-items-center form-label text-success fw-bold">
                    <button type="button" onClick={() => addStepRightAfter(index)} className="btn btn-outline-light border-0 px-0 me-2">
                        <i className="bi bi-plus-lg text-primary"></i>
                    </button>
                    Etape #{ index + 1 }
                    <button type="button" onClick={() => removeStepAt(index)} className="btn btn-outline-light border-0 px-0 ms-2">
                        <i className="bi bi-trash text-primary"></i>
                    </button>
                </label>
                <textarea className="form-control" id={`step-${index + 1}`} value={step.text} onChange={(e) => handleChange(e, index)}></textarea>
            </div>
            )}
            <div className="d-flex flex-row justify-content-end align-items-center">
                {isLoading && <div className="spinner-grow spinner-grow-sm text-secondary me-1" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>}
                <button type="submit" className={`btn btn-success ms-3${true ? "" : " disabled"}`}>
                    Enregistrer
                </button>
            </div>
        </form>
    );
}