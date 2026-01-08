import { RecipeStepResponse } from "@/interfaces/recipe-step";
import RecipeStep from "../recipe-step";

export default function RecipeSteps({ steps } : { steps: RecipeStepResponse[] }) {
    return (
        <div className="d-flex flex-column col-12 mt-3">
            {steps.map((step, index) => <RecipeStep key={step.id} index={index} step={step}/>)}
        </div>
    );
}