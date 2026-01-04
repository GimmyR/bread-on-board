import CallToAction from "@/components/call-to-action";
import RecipeSection from "@/components/recipe-section";
import bobFetch from "@/lib/bob-fetch";

export default async function HomePage() {
    const response = await bobFetch("/api/recipes");

    if(response.status != 200)
        return null;

    return (
        <div className="pt-5">
            <CallToAction/>
            <RecipeSection recipes={response.data}/>
        </div>
    )
}