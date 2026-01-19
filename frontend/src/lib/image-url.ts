import { IMAGE_URL } from "./api";

export default function imageURL(filename: string) {
    return `${IMAGE_URL}/${filename}`;
}