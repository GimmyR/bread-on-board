"use client";

import redirectToCreate from "@/actions/redirect-to-create";

export default function CallToActionForm() {
    return (
        <form className="col-10 col-lg-6 mx-auto" action={redirectToCreate}>
            <div className="input-group">
                <input type="text" className="form-control" placeholder="Title of your recipe" name="title"/>
                <button type="submit" className="btn btn-success col-3 col-lg-2">Create</button>
            </div>
        </form>
    );
}