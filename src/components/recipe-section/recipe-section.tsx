import Card from "@/components/card/card";

export default function RecipeSection() {
    return (
        <div className="container-fluid container-lg bg-light px-5 py-5" style={{ minHeight: '700px' }}>
            <div className="row row-cols-1 row-cols-lg-4 gy-4">
                <div v-for="recipe in recipes">
                    <Card/>
                </div>
            </div>
        </div>
    )
}