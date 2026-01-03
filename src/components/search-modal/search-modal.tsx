import Link from "next/link"

export default function SearchModal({ recipe } : { recipe: { title: string, ingredients: string } }) {
    return (
        <div id="searchModal" className="modal" tabIndex={-1}>
            <div className="modal-dialog modal-lg modal-fullscreen-md-down">
                <div className="modal-content rounded-1">
                    <div className="modal-header">
                        <input type="text" className="form-control me-1" placeholder="Rechercher une recette" v-model="search"/>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body px-4" style={{ height: 750 }}>
                        <div className="d-flex flex-row align-items-center" v-for="recipe in recipes">
                            <Link className="fs-6 text-truncate my-auto py-2 text-decoration-none" href="#" data-bs-dismiss="modal">
                                <span className="text-success me-3">{ recipe?.title }</span> { recipe?.ingredients }
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}