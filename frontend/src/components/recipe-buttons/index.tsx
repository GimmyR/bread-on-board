import removeRecipe from "@/actions/remove-recipe";

type Props = {
    id: string | undefined,
    isLoading: boolean,
    handleError: (response: { status: number, data: any }) => void
};

export default function RecipeButtons({ id, isLoading, handleError } : Props) {
    const handleRemove = async () => {
        const response = await removeRecipe(id);
        handleError(response);
    };

    return (
        <div className="d-flex flex-row justify-content-end align-items-center mt-4">
            {isLoading &&
                <div className="spinner-grow spinner-grow-sm text-secondary me-1" role="status">
                    <span className="visually-hidden">Loading...</span>
                </div>
            }
            {id &&
                <button type="button" onClick={handleRemove} className={`btn btn-dark col-2 ms-3${!isLoading ? "" : " disabled"}`}>
                    Remove
                </button>
            }
            <button type="submit" className={`btn btn-success col-2 ms-3${!isLoading ? "" : " disabled"}`}>
                Save
            </button>
        </div>
    );
}