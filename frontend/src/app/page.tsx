import CallToAction from "@/components/call-to-action";
import RecipeSection from "@/components/recipe-section";
import bobFetch from "@/lib/bob-fetch";

export default async function HomePage() {
    const response = await bobFetch("/api/recipes", true);

    if(response.status != 200)
        return null;

    return (
        <div className="container-fluid container-lg min-vh-100 bg-light px-0">
            <CallToAction/>
            <RecipeSection recipes={response.data}/>
        </div>
    )
}