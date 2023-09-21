import { useState } from "react";
import Form from "../Form";
import "./css/MainCategory.css";

export default function CategoryMain() {
    const [url, setUrl] = useState("http://localhost:8080/catagory");
    const [name, setName] = useState('');
    const [newName, setNewName] = useState('');
    const [searched, setSearched] = useState('Output will be here');    
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
                                    id:"catagory",
                                    display:"Catagory",
                                    target:{name},
                                    changeValue:setName,
                                    required:true
                                },
                            ] 
                        }
                    />
                    {displayIndividual()}
                </div>
            );
        case "Add":
            return (  
                <div>
                    <Form func={AddIndivisual} values=
                        { 
                            [
                                {
                                    id:"catagory",
                                    display:"Catagory",
                                    target:{name},
                                    changeValue:setName,
                                    required:true
                                }

                            ] 
                        }
                    />
                    {displayIndividual()}
                </div>
            );
        case "Update":
            return (  
                <div>
                    <Form func={UpdateIndividual} values=
                        { 
                            [
                                {
                                    id:"catagory",
                                    display:"Catagory",
                                    target:{name},
                                    changeValue:setName,
                                    required:true
        
                                },
                                {
                                    id:"catagory",
                                    display:"Catagory",
                                    target:{newName},
                                    changeValue:setNewName,
                                    required:true
                                }
                            ] 
                        }
                    />
                    {displayIndividual()}
                </div>
            );
            case "Delete":
                return (  
                    <div>
                        <Form func={DeleteIndividual} values=
                            { 
                                [
                                    {
                                        id:"catagory",
                                        display:"Catagory",
                                        target:{name},
                                        changeValue:setName,
                                        required:true
                                    },
                                ] 
                            }
                        />
                        {displayIndividual()}
                    </div>
                );
            default:
                return (        
                    <div className="SearchAllContainer">
                        <button id="SearchAllButton" onClick={() => getAll()}>
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

    async function getIndividual() {
        try 
        {
            const request = await fetch(`${url}/get?givenCategory=${name}`);
            if (request.ok) {
            const response = await request.json();
            console.log(response['outcome']);
            if(response['outcome']===undefined){
                setSearched(`${response['name']}`);}
            else {
                setSearched("Player not found");
            }
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
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "name" : name,
            })
        });
        const response = await request.json();
        setSearched(response['output'])
    }

    async function UpdateIndividual() {
        getIndividual();
        const request = await fetch(`${url}/update`, {
            method:"PUT",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "name" : name,
                "newCategory" : {
                    "name" : newName,
                }
            })
        });
        const response = await request.json();
        setSearched(response['output']);

    }
    async function DeleteIndividual() {
        const request = await fetch(`${url}/delete`, {
            method:"DELETE",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                "name" : name,
            })
        });
        const response = await request.json();
        setSearched(response['output']);
    }

  function displayIndividual() {
      return (
        <div id="SearchIndivisualOutcome">
          {searched}
        </div>
      );

  }

    function displayValues() {
        if (responses !== undefined && responses.length > 0) {
            return (
            <ul id="AllActorResponses">
                {responses.map((category) => (
                <li key={category.id}>
                    <div className="btn">{category.name}</div>
                </li>
                ))}
            </ul>
            );
        } 
        else 
            return <p id="AllActorResponses">No responses to display.</p>;
        
    }

    return (
    <div>
        <div>
            <nav>
                <ul>
                    <li id="ActorGetIndivisual" onClick={() => setCurrentPage("GetIndivisual")}>Search Category</li>
                    <li id="ActorSearchAll" onClick={() => setCurrentPage("SearchAll")}>Search All</li>
                    <li id="ActorAdd" onClick={() => setCurrentPage("Add")}>Add Category</li>
                    <li id="ActorUpdate" onClick={() => setCurrentPage("Update")}>Update Category</li>
                    <li id="ActorDelete" onClick={() => setCurrentPage("Delete")}>Delete Category</li>
                </ul>
            </nav>
            {renderPage()}
        </div>
      
            


    </div>
    );
}
