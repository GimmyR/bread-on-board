import './style.css';

export default function RecipeNotFound() {
    return (
        <div className="d-flex flex-column align-items-center padding-top">
            <h1 className="mt-5 mb-0 fw-bold font-size">404</h1>
            <h2 className="text-success">Recipe not found</h2>
        </div>
    );
}