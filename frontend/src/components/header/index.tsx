import Link from "next/link";
import NavIcon from "@/components/nav-icon";
import SearchModal from "@/components/search-modal";
import { cookies } from "next/headers";
import PersonButton from "../person-button";

export default async function Header() {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value;
    const placeholder = encodeURIComponent("Your recipe title");

    return (
        <>
            <nav className="navbar fixed-top navbar-expand bg-success">
                <div className="container-fluid">
                    <Link className="navbar-brand text-light fw-bold" href="/">Bread on Board</Link>
                    <button className="btn btn-outline-light col-lg-4 text-start btn-search d-none d-lg-block" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i className="bi bi-search me-1"/> Search
                    </button>
                    <div>
                        <ul className="navbar-nav">
                            <li className="nav-item d-lg-none">
                                <button className="btn btn-outline-light border-0 pt-2 btn-search" data-bs-toggle="modal" data-bs-target="#searchModal">
                                    <i className="bi bi-search"/>
                                </button>
                            </li>
                            <li className="nav-item">
                                <NavIcon href={`/recipe/create/${placeholder}`} title="Créer une recette" icon="plus-lg"/>
                            </li>
                            <li className="nav-item">
                                <PersonButton token={token}/>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <SearchModal/>
        </>
    )
}