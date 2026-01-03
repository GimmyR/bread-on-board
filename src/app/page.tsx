import CallToAction from "@/components/call-to-action";
import RecipeSection from "@/components/recipe-section";
import { API_URL } from "@/lib/api";

export default async function HomePage() {
    const response = await fetch(`${API_URL}/api/recipes`);
    const recipes: Recipe[] = response.ok ? await response.json() : [];

    return (
        <div className="pt-5">
            <CallToAction/>
            <RecipeSection recipes={recipes}/>
        </div>
    )
}