"use server";

import { revalidatePath } from "next/cache";
import { cookies } from "next/headers";

export default async function signOut() {
    const cookieStore = await cookies();
    cookieStore.delete("token");
    revalidatePath("/");
}