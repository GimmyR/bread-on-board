export default function SignInPage() {
    const errorMessage = null;
    return (
        <div className="container-fluid container-lg pt-5">
            <div className="d-flex flex-row justify-content-center">
                <form className="d-flex flex-column align-items-center col-12 col-lg-8 mt-5 py-5 px-3 px-lg-0 bg-light">
                    <h1 className="text-success mb-5">Connexion</h1>
                    {errorMessage && 
                    <div className="alert alert-danger col-12 col-lg-6 mb-4" role="alert" v-if="errorMessage != null">
                        { errorMessage }
                    </div>}
                    <div className="col-12 col-lg-6 mb-3">
                        <div className="input-group">
                            <span className="input-group-text">
                                <i className="bi bi-person"></i>
                            </span>
                            <input type="text" className="form-control" placeholder="Nom d'utilisateur"/>
                        </div>
                    </div>
                    <div className="col-12 col-lg-6 mb-4">
                        <div className="input-group">
                            <span className="input-group-text">
                                <i className="bi bi-lock"></i>
                            </span>
                            <input type="password" className="form-control" placeholder="Mots de passe"/>
                        </div>
                    </div>
                    <div className="d-flex flex-column col-12 col-lg-6 mb-5">
                        <button type="submit" className="btn btn-success mb-2">Se connecter</button>
                        <button type="button" className="btn btn-dark" >S'inscrire</button>
                    </div>
                </form>
            </div>
        </div>
    );
}