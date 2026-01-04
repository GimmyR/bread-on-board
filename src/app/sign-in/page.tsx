import SignInForm from "./form";

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