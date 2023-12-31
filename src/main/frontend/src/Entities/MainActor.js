import { useState, useEffect } from "react";
import Form from "../Form";
import "./css/MainActor.css"

export default function ActorMenu(props) {
    const url = props.mainUrl+"/actor";
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [newFirstName, setNewFirstName] = useState('');
    const [newLastName, setNewLastName] = useState('');
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
                                    id:"firstName",
                                    display:"Forename",
                                    target:{firstName},
                                    changeValue:setFirstName,
                                    required:true
        
                                },
                                {
                                    id:"lastName",
                                    display:"Surname",
                                    target:{lastName},
                                    changeValue:setLastName,
                                    required:true
                                }
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
                                    id:"firstName",
                                    display:"Forename",
                                    target:{firstName},
                                    changeValue:setFirstName,
                                    required:true
        
                                },
                                {
                                    id:"lastName",
                                    display:"Surname",
                                    target:{lastName},
                                    changeValue:setLastName,
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
                                    id:"firstName",
                                    display:"Forename",
                                    target:{firstName},
                                    changeValue:setFirstName,
                                    required:true
        
                                },
                                {
                                    id:"lastName",
                                    display:"Surname",
                                    target:{lastName},
                                    changeValue:setLastName,
                                    required:true
                                },
                                {
                                    id:"newfirstName",
                                    display:"New Forename",
                                    target:{newFirstName},
                                    changeValue:setNewFirstName,
                                    required:true
        
                                },
                                {
                                    id:"newlastName",
                                    display:"New Surname",
                                    target:{newLastName},
                                    changeValue:setNewLastName,
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
                                        id:"firstName",
                                        display:"Forename",
                                        target:{firstName},
                                        changeValue:setFirstName,
                                        required:true
            
                                    },
                                    {
                                        id:"lastName",
                                        display:"Surname",
                                        target:{lastName},
                                        changeValue:setLastName,
                                        required:true
                                    }
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

    const ActorInfo = ({firstName, lastName}) => {
        const [films, setFilms] = useState([]);
      
        useEffect(() => {
          }, [films]);
      
        const getFilms = async () => {
          try {
            const request = await fetch(`${url}/films?firstName=${firstName}&lastName=${lastName}`);
            if (request.ok) {
              const response = await request.json();
              setFilms(response); 
            } else {
              console.error("Failed to fetch individual response");
            }
          } catch (error) {
            console.error("An error occurred while fetching individual response:", error);
          }
        };
      
        const handleFilms = () => {
          if (films.length > 0) {
            return (
              films.map((film) => (
                <ul key={film.title} className="slide-up">
                  <li>{film.title}</li>
                  <li>{film.description}</li>
                  <li>{film.release_year}</li>
                  <li>{film.rating}</li>
                  <li>{film.length} Minutes</li>
                  <hr></hr>
                </ul>
              ))
            );
          }
        };
      
        return (
          <>
            <div id="ActorName">{firstName} {lastName}</div>
            <button onClick={getFilms}>Get Films</button>
            <hr></hr>
            {handleFilms()}
          </>
        );
      };
    
      async function getIndividual() {
        try {
          const request = await fetch(`${url}/get?forename=${firstName}&surname=${lastName}`);
          if (request.ok) {
            const response = await request.json();
            if (response['output'] === undefined) {
              setSearched(
                <ActorInfo
                  firstName={response['first_name']}
                  lastName={response['last_name']}
                />
              );
            } else {
              setSearched("Actor doesn't exist");
            }
          } else {
            console.error("Failed to fetch individual response");
          }
        } catch (error) {
          console.error("An error occurred while fetching individual response:", error);
        }
      }
      

    async function AddIndivisual() {
        const request = await fetch(`${url}/add`, {
            method:"POST",
            headers: {
                "Content-Type" : "application/json;charset=UTF-8"
            },
            body: JSON.stringify({
                "first_name" : firstName,
                "last_name" : lastName
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
                "forename" : firstName,
                "surname" : lastName,
                "actor" : {
                    "first_name" : newFirstName,
                    "last_name" : newLastName
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
                "first_name" : firstName,
                "last_name" : lastName,
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

  useEffect(() => {
    getIndividual(); 
  }, [firstName, lastName]);

  function selectIndividual(first, last) {
    setCurrentPage("GetIndivisual");
    setFirstName(first);
    setLastName(last);
  }

    function displayValues() {
        if (responses !== undefined && responses.length > 0) {
            return (
            <ul id="AllActorResponses">
                {responses.map((actor) => (
                <li key={actor.id} onClick={() => selectIndividual(actor.first_name, actor.last_name)}>
                    <div>First Name: {actor.first_name}</div>
                    <div>Last Name: {actor.last_name}</div>
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
                    <li id="ActorGetIndivisual" onClick={() => setCurrentPage("GetIndivisual")}>Search individual</li>
                    <li id="ActorSearchAll" onClick={() => setCurrentPage("SearchAll")}>Search All</li>
                    <li id="ActorAdd" onClick={() => setCurrentPage("Add")}>Add Actor</li>
                    <li id="ActorUpdate" onClick={() => setCurrentPage("Update")}>Update Actor</li>
                    <li id="ActorDelete" onClick={() => setCurrentPage("Delete")}>Delete Actor</li>
                </ul>
            </nav>
            {renderPage()}
        </div>
      
            


    </div>
    );
}
