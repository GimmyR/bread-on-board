import { jwtVerify } from "jose";

const secret = new TextEncoder().encode(process.env.JWT_SECRET);

export async function getSubjectFromToken(token: string | undefined) {

    if(token) {

        const { payload } = await jwtVerify(token, secret);
        return payload.sub;
        
    } else return undefined;

}