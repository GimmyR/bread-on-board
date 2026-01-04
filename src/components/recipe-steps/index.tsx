import { RecipeStepResponse } from "@/interfaces/recipe-step";

export default function RecipeSteps({ steps } : { steps: RecipeStepResponse[] }) {
    return (
        <ol className="pt-3">
            {steps.map(step => <li key={step.id} className="mb-4">{step.text}</li>)}
        </ol>
    );
}