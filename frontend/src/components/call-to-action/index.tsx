import "./style.css";
import CallToActionForm from "./form";

export default function CallToAction() {
    return (
        <div id="call-to-action" className="mb-5 mb-md-0">
            <p className="col-12 col-lg-7 mx-auto text-center text-light fs-5 text-shadow">Bread on Board is a cooking recipes website and allows to<br/>create, edit or remove your own recipes.</p>
            <CallToActionForm/>
        </div>
    )
}