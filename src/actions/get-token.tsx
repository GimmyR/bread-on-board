"use server";

import { RequestCookie } from "next/dist/compiled/@edge-runtime/cookies";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export async function getToken() {
    const cookieStore = await cookies();
    const cookie: RequestCookie | undefined = cookieStore.get("token");

    if(cookie)
        return cookie.value;
    else redirect("/sign-in");
}