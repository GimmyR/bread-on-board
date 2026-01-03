import Link from "next/link";
import NavIcon from "@/components/nav-icon/nav-icon";
import SearchModal from "@/components/search-modal/search-modal";

export default function Header() {
    return (
        <>
            <nav className="navbar fixed-top navbar-expand bg-success">
                <div className="container-fluid-md container">
                    <Link className="navbar-brand text-light fw-bold" href="/">Bread on Board</Link>
                    <button className="btn btn-outline-light col-lg-4 text-start btn-search d-none d-lg-block" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <i className="bi bi-search"/> Chercher
                    </button>
                    <div>
                        <ul className="navbar-nav">
                            <li className="nav-item d-lg-none">
                                <button className="btn btn-outline-light border-0 pt-2 btn-search" data-bs-toggle="modal" data-bs-target="#searchModal">
                                    <i className="bi bi-search"/>
                                </button>
                            </li>
                            <li className="nav-item">
                                <NavIcon title="Créer une recette" icon="plus-lg"/>
                            </li>
                            <li className="nav-item">
                                {true ? <NavIcon title="Connexion" icon="person-circle"/>
                                :<NavIcon icon="person-circle" />}
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <SearchModal recipe={{ title: "", ingredients: "" }}/>
        </>
    )
}