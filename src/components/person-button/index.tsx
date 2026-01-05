import signOut from "@/actions/sign-out";
import "./style.css";
import Link from "next/link";

export default function PersonButton({ token } : { token: string | undefined }) {
    return (
        <>
            {!token ?
                <Link href="/sign-in" className="btn btn-dark button-sign">Sign in</Link>
            :
                <Link href="#" className="btn btn-dark button-sign" onClick={signOut}>Sign out</Link>
            }
        </>
    );
}