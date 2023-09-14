import { useState } from "react"

export default function App() {
    const [url, setUrl] = useState("http://localhost:8080/demo");
    const [responses, setReponses] = useState();
    
    async function getAll() {
        const request = await fetch(url + "/all");
        var response = await request.json();
        setReponses(response)
    }

    function displayValues() {
        if (responses !== undefined && responses.length > 0) {
            return (
                <ul>
                    {responses.map((actor) => (
                        <li key={actor.id}>
                            <div>First Name: {actor.first_name}</div>
                            <div>Last Name: {actor.last_name}</div>
                            <div>Last Update: {actor.last_update}</div>
                        </li>
                    ))}
                </ul>   
            );
        } else {
            return <p>No responses to display.</p>;
        }
    }

    return (
        <div>
            <button onClick={() => getAll()}>
                Press here to get all responses
            </button>
            {displayValues()} 
        </div>
    );
}   