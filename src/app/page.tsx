import CallToAction from "@/components/call-to-action/call-to-action";
import RecipeSection from "@/components/recipe-section/recipe-section";

export default function HomePage() {
    return (
        <div className="pt-5">
            <CallToAction/>
            <RecipeSection/>
        </div>
    )
}