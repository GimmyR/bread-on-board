import "./style.css";
import Link from "next/link";
import AccountButton from "../account-button";

export default function PersonButton({ token } : { token: string | undefined }) {
    return (
        <>
            {!token ?
                <Link href="/sign-in" className="btn btn-dark button-sign ms-2">Sign in</Link>
            :
                <AccountButton/>
            }
        </>
    );
}