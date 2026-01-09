"use server";

import bobFetch from "@/lib/bob-fetch";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function signIn(formData: FormData) {
    const response = await bobFetch("/api/sign-in", true, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ 
            username: formData.get("username"), 
            password: formData.get("password") 
        })
    });

    if(response.status == 200) {

        const cookieStore = await cookies();
        cookieStore.set("token", response.data, {
            httpOnly: true,
            secure: true,
            path: "/",
            sameSite: "none"
        });

        redirect("/");

    } else return response;
}