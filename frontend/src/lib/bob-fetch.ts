import { API_URL } from "@/lib/api";

export default async function bobFetch(url: string, options?: RequestInit) {
    const response = await fetch(API_URL + url, options);
    const clone = response.clone();

    try {

        return { 
            status: response.status, 
            data: await response.json() 
        };

    } catch(error: unknown) {

        return { 
            status: response.status, 
            data: await clone.text()
        };

    } 
}