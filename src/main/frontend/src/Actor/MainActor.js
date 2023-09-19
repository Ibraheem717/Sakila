import { useState } from "react";
import Form from "../Form";

export default function ActorMenu() {
    const [url, setUrl] = useState("http://localhost:8080/actor");
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [searched, setSearched] = useState('');
    const [searchedFirstName, setSearchedFirstName] = useState('');
    const [searchedLastName, setSearchedLastName] = useState('');
    const [responses, setResponses] = useState();
    const [currentPage, setCurrentPage] = useState("home");

    const renderPage = () => {
      switch (currentPage) {
        case "GetIndivisual":
            return (  
                <div>
                    <Form func={getIndividual} values=
                        { 
                            [
                                {
                                    id:"firstName",
                                    display:"Forename",
                                    target:{firstName},
                                    changeValue:setFirstName
        
                                },
                                {
                                    id:"lastName",
                                    display:"Surname",
                                    target:{lastName},
                                    changeValue:setLastName
                                }
                            ] 
                        }
                    />
                    {displayIndividual()}
                </div>
            );
        case "SearchAll":
            return (        
                <div>
                    <button onClick={() => getAll()}>
                        Press here to get all responses
                    </button>
                    {displayValues()}            
                </div>
            );
      }
    };

    async function getAll() {
        try 
        {
            const request = await fetch(`${url}/all`);
            if (request.ok) {
                const response = await request.json();
                setResponses(response);
            } 
            else 
                console.error("Failed to fetch all responses");
        } 
        catch (error) {
            console.error("An error occurred while fetching all responses:", error);
        }
    }

    async function getIndividual(e) {
        e.preventDefault();
        try 
        {
            const request = await fetch(`${url}/get?forename=${firstName}&surname=${lastName}`);
            if (request.ok) {
            const response = await request.json();
            if (response['outcome'] === undefined) {
                setSearchedFirstName(response['first_name']);
                setSearchedLastName(response['last_name']);
            }
            setSearched(response['outcome']);
            } else {
            console.error("Failed to fetch individual response");
            }
        } 
        catch (error) {
            console.error("An error occurred while fetching individual response:", error);
        }
    }

    async function AddIndivisual() {
        const request = await fetch(`${url}/add`, {
            method:"POST",
            headers: {
                "Content-Type" : "application-json"
            },
            body: JSON.stringify({
                "first_name" : firstName,
                "last_name" : lastName
            })
        });
        
    }

  function displayIndividual() {
    if (searched === undefined) {
      return (
        <div>
          {searchedFirstName} {searchedLastName}
        </div>
      );
    }
    return null;
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
        } 
        else 
            return <p>No responses to display.</p>;
        
    }

    return (
    <div>
        <div>
            <nav>
                <ul>
                    <li onClick={() => setCurrentPage("GetIndivisual")}>Search individual</li>
                    <li onClick={() => setCurrentPage("SearchAll")}>Search All</li>
                    <li onClick={() => setCurrentPage("Add")}>Add Actor</li>
                </ul>
            </nav>
            {renderPage()}
        </div>
      
            


    </div>
    );
}
