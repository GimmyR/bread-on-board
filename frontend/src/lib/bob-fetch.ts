import { CLIENT_SIDE_TO_API, SERVER_SIDE_TO_API } from "@/lib/api";

export default async function bobFetch(url: string, isServer: boolean, options?: RequestInit) {
    const response = await fetch((isServer ? SERVER_SIDE_TO_API : CLIENT_SIDE_TO_API) + url, options);
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