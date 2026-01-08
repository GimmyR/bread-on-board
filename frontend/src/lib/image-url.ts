import { API_URL } from "./api";

export default function imageURL(filename: string) {
    return `${API_URL}/images/${filename}`;
}