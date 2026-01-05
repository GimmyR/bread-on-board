import { jwtVerify } from "jose";
import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

const secret = new TextEncoder().encode(process.env.JWT_SECRET);

export async function getSubjectFromToken(token: string | undefined) {

    if(token) {

        const { payload } = await jwtVerify(token, secret);
        return payload.sub;
        
    } else return undefined;

}

export async function getToken() {
    const cookieStore = await cookies();
    const cookie: RequestCookie | undefined = cookieStore.get("token");

    if(cookie)
        return cookie.value;
    else redirect("/sign-in");
}