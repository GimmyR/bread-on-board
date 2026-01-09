"use server";

import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { cookies } from "next/headers";

export async function getToken() {
    const cookieStore = await cookies();
    const cookie: RequestCookie | undefined = cookieStore.get("token");

    if(cookie)
        return cookie.value;

    else return undefined
}