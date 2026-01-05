"use client";

import createRecipe from "@/actions/create-recipe";
import { RecipeStepForm } from "@/interfaces/recipe-step";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";
import StepForm from "../step-form";
import SaveRecipeButton from "../save-recipe-button";
import { RecipeResponse } from "@/interfaces/recipe";

type Props = {
    title?: string,
    recipe?: RecipeResponse
};

const createRecipeStep = () => {
    return { key: Date.now(), text: "" };
};

const initSteps = (recipe: RecipeResponse | undefined) => {
    if(!recipe)
        return useState<RecipeStepForm[]>([ createRecipeStep() ]);

    else {
        const steps = recipe.steps.map(step => { 

            return { 
                id: step.id, 
                text: step.text 
            };

        }); return useState<RecipeStepForm[]>([...steps]);
    }
};

export default function CreateRecipeForm({ title, recipe } : Props) {
    const [error, setError] = useState<string | undefined>();
    const [steps, setSteps] = initSteps(recipe);
    const [isLoading, setIsLoading] = useState(false);
    const router = useRouter();

    const addStepRightAfter = (index: number) => {
        steps.splice(index + 1, 0, createRecipeStep());
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
            setError(result.status != 401 ? result.data : "You are not authenticated");
            setIsLoading(false);
        }
    };

    return (
        <form className="d-flex flex-column col-12 col-lg-6 mx-auto mt-0 mt-lg-5 mb-0 mb-lg-5" onSubmit={handleSubmit}>
            {error != null && 
            <div className="alert alert-danger mb-5 py-2" role="alert">{error}</div>}
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
            <StepForm key={step.id ? step.id : step.key} index={index} step={step} addStepRightAfter={addStepRightAfter} removeStepAt={removeStepAt} handleChange={handleChange}/>)}
            <SaveRecipeButton isLoading={isLoading}/>
        </form>
    );
}