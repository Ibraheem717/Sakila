import { useState } from "react";

export default function filmActors () {
    const [url, setUrl] = useState("http://localhost:8080/filmActor");
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');

    async function GetActors() {
        const request = await fetch(`${url}/get/actor?forename=${firstName}&surname=${lastName}`);
        const response = await request.json();
    }
}