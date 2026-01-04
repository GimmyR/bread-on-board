import NavIcon from "@/components/nav-icon";

export default function PersonButton({ username } : { username: string | undefined }) {
    return (
        <>
            {!username ?
                <NavIcon href="/sign-in" title="Connexion" icon="person-circle"/>
            :
                <NavIcon href="#" title={username} icon="person-circle" />
            }
        </>
    );
}