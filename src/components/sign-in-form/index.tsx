"use client";

import signIn from "@/actions/sign-in";
import Link from "next/link";
import { FormEvent, useState } from "react";
import SignInInput from "../sign-in-input";
import signUp from "@/actions/sign-up";

export default function SignInForm({ isSigningUp = false } : { isSigningUp?: boolean }) {
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const formData = new FormData(event.currentTarget)

        if(isSigningUp) {
            const response = await signUp(formData);
            handleResponse(response);
        } else {
            const response = await signIn(formData);
            handleResponse(response);
        }
    };

    const handleResponse = (response: { status: number, data: any }) => {
        if(response.status != 200)
            setError(response.data);
    };

    return (
        <form onSubmit={handleSubmit} className="d-flex flex-column align-items-center col-12 col-lg-8 mt-5 py-5 px-3 px-lg-0 bg-light">
            <h1 className="text-success mb-5">Login</h1>
            {error && <div className="alert alert-danger col-12 col-lg-6 mb-4" role="alert">{ error }</div>}
            <SignInInput marginBottom={3} icon="person" type="text" name="username" placeholder="Username"/>
            {isSigningUp && <SignInInput marginBottom={3} icon="envelope" type="email" name="mail-address" placeholder="Mail address"/>}
            <SignInInput marginBottom={4} icon="lock" type="password" name="password" placeholder="Password"/>
            <div className="d-flex flex-column col-12 col-lg-6 mb-5">
                <button type="submit" className="btn btn-success mb-2">{isSigningUp ? "Sign up" : "Sign in"}</button>
                <Link href={`/${isSigningUp ? "sign-in" : "sign-up"}`} className="btn btn-dark" >{isSigningUp ? "Sign in" : "Sign up"}</Link>
            </div>
        </form>
    );
}