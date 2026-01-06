export async function GET() {
    return new Response(JSON.stringify({ message: "All good !" }), {
        status: 200,
        headers: {
            "Content-Type": "application/json"
        }
    });
}