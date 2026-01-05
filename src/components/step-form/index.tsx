import { RecipeStepForm } from "@/interfaces/recipe-step";
import { ChangeEvent } from "react";

type Props = {
    index: number,
    step: RecipeStepForm,
    addStepRightAfter: (index: number) => void,
    removeStepAt: (index: number) => void,
    handleChange: (event: ChangeEvent<HTMLTextAreaElement>, index: number) => void
};

export default function StepForm({ index, step, addStepRightAfter, removeStepAt, handleChange } : Props) {
    return (
        <div className="d-flex flex-column mb-4">
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
    );
}