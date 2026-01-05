import { Metadata } from "next";
import SignInForm from "./form";

export const metadata: Metadata = {
    title: "Sign in - Bread on Board",
    description: "Sign in page for Bread on Board web application.",
};

export default function SignInPage() {
    const errorMessage = null;

    return (
        <div className="container-fluid container-lg pt-5">
            <div className="d-flex flex-row justify-content-center">
                <SignInForm/>
            </div>
        </div>
    );
}