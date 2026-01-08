import type { Metadata } from "next";
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap-icons/font/bootstrap-icons.min.css';
import "./global.css";
import Bootstrap from "@/components/bootstrap";
import Header from "@/components/header";

export const metadata: Metadata = {
    title: "Bread on Board",
    description: "Create recipe for your favorite foods.",
};

export default function RootLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body>
                <Header/>
                {children}
                <Bootstrap />
            </body>
        </html>
    );
}
