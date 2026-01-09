import Link from "next/link"

export default function NavIcon({ href, title, icon } : { href: string, title?: string; icon: string }) {
    return (
        <Link href={href} className="nav-link text-light fw-bold" title={title}>
            <i className={`bi bi-${icon}`}/>
        </Link>
    );
}