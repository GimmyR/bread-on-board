import Link from "next/link"

export default function NavIcon({ title, icon } : { title?: string; icon: string }) {
    return (
        <Link href="#" className="nav-link text-light fw-bold">
            <i className={`bi bi-${icon}`}/>
        </Link>
    );
}