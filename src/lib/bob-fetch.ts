import { API_URL } from "@/lib/api";

export default async function bobFetch(url: string, options?: RequestInit) {
    const response = await fetch(API_URL + url, options);
    const clone = response.clone();
    let data: string | any | undefined = undefined;

    try {

        data = await response.json();

    } catch(error: unknown) {

        data = await clone.text();

    } return { status: response.status, data: data };
}