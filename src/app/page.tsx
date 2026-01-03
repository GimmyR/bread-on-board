import CallToAction from "@/components/call-to-action";
import RecipeSection from "@/components/recipe-section";

export default async function HomePage() {
    const response = await fetch("http://localhost:8080/api/recipes");
    const recipes: Recipe[] = response.ok ? await response.json() : [];

    return (
        <div className="pt-5">
            <CallToAction/>
            <RecipeSection recipes={recipes}/>
        </div>
    )
}