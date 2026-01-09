import { CLIENT_SIDE_TO_API } from "./api";

export default function imageURL(filename: string) {
    return `${CLIENT_SIDE_TO_API}/images/${filename}`;
}