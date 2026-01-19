import { CLIENT_SIDE_TO_API, IMAGE_URL } from "./api";

export default function imageURL(filename: string) {
    if(IMAGE_URL)
        return `${IMAGE_URL}/${filename}`;
    else return `${CLIENT_SIDE_TO_API}/images/${filename}`;
}