"use client";

import signIn from "@/actions/sign-in";
import { FormEvent, useState } from "react";

export default function SignInForm() {
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const response = await signIn(new FormData(event.currentTarget));

        if(response.status != 200)
            setError(response.data);
    };

    return (
        <form onSubmit={handleSubmit} className="d-flex flex-column align-items-center col-12 col-lg-8 mt-5 py-5 px-3 px-lg-0 bg-light">
            <h1 className="text-success mb-5">Login</h1>
            {error && 
            <div className="alert alert-danger col-12 col-lg-6 mb-4" role="alert">
                { error }
            </div>}
            <div className="col-12 col-lg-6 mb-3">
                <div className="input-group">
                    <span className="input-group-text">
                        <i className="bi bi-person"></i>
                    </span>
                    <input type="text" name="username" className="form-control" placeholder="Username"/>
                </div>
            </div>
            <div className="col-12 col-lg-6 mb-4">
                <div className="input-group">
                    <span className="input-group-text">
                        <i className="bi bi-lock"></i>
                    </span>
                    <input type="password" name="password" className="form-control" placeholder="Password"/>
                </div>
            </div>
            <div className="d-flex flex-column col-12 col-lg-6 mb-5">
                <button type="submit" className="btn btn-success mb-2">Sign in</button>
                <button type="button" className="btn btn-dark" >Sign up</button>
            </div>
        </form>
    );
}