"use client";

import { RecipeStepResponse } from "@/interfaces/recipe-step";
import { ChangeEvent, useState } from "react";

type Props = {
    index: number,
    step: RecipeStepResponse
};

export default function RecipeStep({ index, step }: Props) {
    const [checked, setChecked] = useState(false);

    const handleCheckStep = (event: ChangeEvent<HTMLInputElement>) => {
        setChecked(!checked);
    };

    return (
        <div className={`d-flex flex-row justify-content-between align-items-center border-bottom ${index == 0 && "border-top"} px-3 py-3`}>
            <span className="text-success text-center align-self-start">{index + 1}.</span>
            <span className={`text-center ps-2 pe-4 ${checked && "text-secondary text-decoration-line-through"}`}>{step.text}</span>
            <input className="form-check-input border-2 border-success" type="checkbox" id="checkDefault" onChange={handleCheckStep}/>
        </div>
    );
}