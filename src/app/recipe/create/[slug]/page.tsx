import CreateRecipeForm from "./form";

export default async function CreateRecipePage({ params } : { params: Promise<{ slug: string }> }) {
    const { slug } = await params;
    const title = decodeURIComponent(slug);
    return (
        <div className="container-fluid container-lg bg-light py-5 mt-5">
            <CreateRecipeForm title={title}/>
        </div>
    );
}