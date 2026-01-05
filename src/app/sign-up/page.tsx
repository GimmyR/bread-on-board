import SignInForm from "@/components/sign-in-form";
import { Metadata } from "next";

export const metadata: Metadata = {
    title: "Sign up - Bread on Board",
    description: "Sign up page for Bread on Board web application.",
};

export default function SignInPage() {
    return (
        <div className="container-fluid container-lg pt-5">
            <div className="d-flex flex-row justify-content-center">
                <SignInForm isSigningUp/>
            </div>
        </div>
    );
}