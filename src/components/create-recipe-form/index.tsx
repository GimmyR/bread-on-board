"use client";

import createRecipe from "@/actions/create-recipe";
import { RecipeStepForm } from "@/interfaces/recipe-step";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useState } from "react";
import StepForm from "../step-form";
import RecipeButtons from "@/components/recipe-buttons";
import { RecipeResponse } from "@/interfaces/recipe";
import editRecipe from "@/actions/edit-recipe";

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

    const manageResult = (result: { status: number, data: any }) => {
        if(result.status == 201)
            router.push(`/recipe/${result.data.id}`);

        else { 
            handleError(result);
            setIsLoading(false);
        }
    };

    const handleError = (response: { status: number, data: any }) => {
        setError(response.status != 401 ? response.data : "You are not authenticated");
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        setIsLoading(true);
        event.preventDefault();
        const formData = new FormData(event.currentTarget);

        if(!recipe) {
            const result = await createRecipe(formData, steps);
            manageResult(result);
        } else {
            const result = await editRecipe(recipe.id, formData, steps);
            manageResult(result);
        }
    };

    return (
        <form className="d-flex flex-column col-12 col-lg-6 mx-auto mt-0 mt-lg-5 mb-0 mb-lg-5" onSubmit={handleSubmit}>
            {error != null && 
            <div className="alert alert-danger mb-5 py-2" role="alert">{error}</div>}
            <div className="mb-4">
                <label htmlFor="title" className="form-label text-success fw-bold">Title</label>
                <input type="text" className="form-control" name="title" id="title" defaultValue={recipe ? recipe.title : title}/>
            </div>
            <div className="mb-4">
                <label htmlFor="image" className="form-label text-success fw-bold">Image</label>
                <input type="file" id="image" className="form-control" accept="image/*" name="image"/>
            </div>
            <div className="mb-3">
                <label htmlFor="ingredients" className="form-label text-success fw-bold">Ingredients</label>
                <textarea id="ingredients" name="ingredients" className="form-control" defaultValue={recipe && recipe.ingredients}></textarea>
            </div>
            {steps.map((step, index) =>
            <StepForm key={step.id ? step.id : step.key} index={index} step={step} addStepRightAfter={addStepRightAfter} removeStepAt={removeStepAt} handleChange={handleChange}/>)}
            <RecipeButtons id={recipe ? recipe.id : undefined} isLoading={isLoading} handleError={handleError}/>
        </form>
    );
}