import "./call-to-action.css";

export default function CallToAction() {
    return (
        <div className="container-fluid container-lg" id="call-to-action">
            <p className="col-12 col-lg-7 mx-auto text-center text-light fs-5 text-shadow">Bread on Board est une application web qui permet de consulter des recettes de cuisine mais également d'en créer.</p>
            <form className="col-10 col-lg-6 mx-auto"> {/* @submit.prevent="validate" */}
                <div className="input-group">
                    <input type="text" className="form-control" placeholder="Nouvelle recette" /*v-model="title"*//>
                    <button type="submit"className="btn btn-success col-3 col-lg-2">Créer</button>
                </div>
            </form>
        </div>
    )
}