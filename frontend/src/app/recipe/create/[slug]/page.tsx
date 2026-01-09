import CreateRecipeForm from "@/components/create-recipe-form";
import { Metadata } from "next";

export const metadata: Metadata = {
    title: "Create recipe - Bread on Board",
    description: "Create recipe of your favorite foods.",
};

export default async function CreateRecipePage({ params } : { params: Promise<{ slug: string }> }) {
    const { slug } = await params;
    const title = decodeURIComponent(slug);
    return (
        <div className="container-fluid container-lg min-vh-100 bg-light py-5 mt-5">
            <CreateRecipeForm title={title}/>
        </div>
    );
}