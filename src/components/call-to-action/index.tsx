import "./style.css";
import CallToActionForm from "./form";

export default function CallToAction() {
    return (
        <div className="container-fluid container-lg" id="call-to-action">
            <p className="col-12 col-lg-7 mx-auto text-center text-light fs-5 text-shadow">Bread on Board est une application web qui permet de consulter des recettes de cuisine mais également d'en créer.</p>
            <CallToActionForm/>
        </div>
    )
}