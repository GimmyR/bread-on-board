"use server";

import { redirect } from "next/navigation";

export default async function redirectToCreate(formData: FormData) {

    redirect(`/recipe/create/${encodeURIComponent(formData.get("title") as string)}`);

}