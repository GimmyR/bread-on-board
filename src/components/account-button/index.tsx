import signOut from "@/actions/sign-out";
import Link from "next/link"

export default function AccountButton() {
    return (
        <div className="dropdown">
            <Link href="#" className="nav-link text-light fw-bold" title="Your account" data-bs-toggle="dropdown" aria-expanded="false">
                <i className="bi bi-person-circle"></i>
            </Link>
            <ul className="dropdown-menu dropdown-menu-end text-center pb-3">
                <li><Link className="dropdown-item" href="/my-recipes">My recipes</Link></li>
                <li className="px-3"><Link className="btn btn-danger col-12 mt-2" href="#" onClick={signOut}>Sign out</Link></li>
            </ul>
        </div>
    );
}