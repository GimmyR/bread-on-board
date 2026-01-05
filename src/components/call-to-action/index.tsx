import "./style.css";
import CallToActionForm from "./form";

export default function CallToAction() {
    return (
        <div className="container-fluid container-lg" id="call-to-action">
            <p className="col-12 col-lg-7 mx-auto text-center text-light fs-5 text-shadow">Bread on Board is a cooking recipes website and allows to<br/>create, edit or remove your own recipes.</p>
            <CallToActionForm/>
        </div>
    )
}