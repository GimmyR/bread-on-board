type Props = {
    isLoading: boolean
};

export default function SaveRecipeButton({ isLoading } : Props) {
    return (
        <div className="d-flex flex-row justify-content-end align-items-center mt-4">
            {isLoading &&
                <div className="spinner-grow spinner-grow-sm text-secondary me-1" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            }
            <button type="submit" className={`btn btn-success ms-3${!isLoading ? "" : " disabled"}`}>
                Save recipe
            </button>
        </div>
    );
}