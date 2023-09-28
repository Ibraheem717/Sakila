import { useState } from "react";
import Form from "../Form";
import "./css/MainAddress.css";

export default function AddressMain(props) {
    const url = props.mainUrl+"/address";
    const [address, setAddress] = useState('');
    const [addressTwo, setAddressTwo] = useState('');
    const [district, setDistrict] = useState('');
    const [city, setCity] = useState('');
    const [postCode, setPostCode] = useState('');
    const [phone, setPhone] = useState('');
    const [newaddress, setNewAddress] = useState('');
    const [newaddressTwo, setNewAddressTwo] = useState('');
    const [newdistrict, setNewDistrict] = useState('');
    const [newcity, setNewCity] = useState('');
    const [newpostCode, setNewPostCode] = useState('');
    const [newphone, setNewPhone] = useState('');
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
                                    id:"address",
                                    display:"Address",
                                    target:{address},
                                    changeValue:setAddress,
                                    required:true
        
                                },
                                {
                                    id:"address2",
                                    display:"Address 2",
                                    target:{addressTwo},
                                    changeValue:setAddressTwo
                                },
                                {
                                    id:"district",
                                    display:"District",
                                    target:{district},
                                    changeValue:setDistrict,
                                    required:true
                                },
                                {
                                    id:"city",
                                    display:"City",
                                    target:{city},
                                    changeValue:setCity,
                                    required:true
        
                                },
                                {
                                    id:"postcode",
                                    display:"Post Code",
                                    target:{postCode},
                                    changeValue:setPostCode
                                },
                                {
                                    id:"phone",
                                    display:"Phone",
                                    target:{phone},
                                    changeValue:setPhone,
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
                                    id:"address",
                                    display:"Address",
                                    target:{address},
                                    changeValue:setAddress,
                                    required:true
        
                                },
                                {
                                    id:"address2",
                                    display:"Address 2",
                                    target:{addressTwo},
                                    changeValue:setAddressTwo
                                },
                                {
                                    id:"district",
                                    display:"District",
                                    target:{district},
                                    changeValue:setDistrict,
                                    required:true
                                },
                                {
                                    id:"city",
                                    display:"City",
                                    target:{city},
                                    changeValue:setCity,
                                    required:true
        
                                },
                                {
                                    id:"postcode",
                                    display:"Post Code",
                                    target:{postCode},
                                    changeValue:setPostCode
                                },
                                {
                                    id:"phone",
                                    display:"Phone",
                                    target:{phone},
                                    changeValue:setPhone,
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
                                    id:"address",
                                    display:"Address",
                                    target:{address},
                                    changeValue:setAddress,
                                    required:true
        
                                },
                                {
                                    id:"address2",
                                    display:"Address 2",
                                    target:{addressTwo},
                                    changeValue:setAddressTwo
                                },
                                {
                                    id:"district",
                                    display:"District",
                                    target:{district},
                                    changeValue:setDistrict,
                                    required:true
                                },
                                {
                                    id:"city",
                                    display:"City",
                                    target:{city},
                                    changeValue:setCity,
                                    required:true
        
                                },
                                {
                                    id:"postcode",
                                    display:"Post Code",
                                    target:{postCode},
                                    changeValue:setPostCode
                                },
                                {
                                    id:"phone",
                                    display:"Phone",
                                    target:{phone},
                                    changeValue:setPhone,
                                    required:true
                                },
                                {
                                    id:"newAddress",
                                    display:"New Address",
                                    target:{newaddress},
                                    changeValue:setNewAddress,
                                    required:true
        
                                },
                                {
                                    id:"newAddress2",
                                    display:"New Address 2",
                                    target:{newaddressTwo},
                                    changeValue:setNewAddressTwo
                                },
                                {
                                    id:"newDistrict",
                                    display:"New District",
                                    target:{newdistrict},
                                    changeValue:setNewDistrict,
                                    required:true
                                },
                                {
                                    id:"newCity",
                                    display:"New City",
                                    target:{newcity},
                                    changeValue:setNewCity,
                                    required:true
        
                                },
                                {
                                    id:"newPostcode",
                                    display:"New Post Code",
                                    target:{newpostCode},
                                    changeValue:setNewPostCode
                                },
                                {
                                    id:"newPhone",
                                    display:"New Phone",
                                    target:{newphone},
                                    changeValue:setNewPhone,
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

    async function getIndividual() {
        try {
            const request = await fetch(`${url}/get?address=${address}&address2=${addressTwo}&district=${district}&city=${city}&postCode=${postCode}&phone=${phone}`);
            if (request.ok) {
                const response = await request.json();
                console.log(response['outcome']);
                if(response['outcome']===undefined){
                    setSearched(sortAddress(response));}
                else {
                    setSearched("Player not found");
                }
            } 
            else {
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
                "address" : address,
                "address2" : addressTwo,
                "district" : district,
                "postal_code" : postCode,
                "phone" : phone,
                "city" : city
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
                "address" : address,
                "address2" : addressTwo,
                "district" : district,
                "postal_code" : postCode,
                "phone" : phone,
                "city" : city,
                "newCity" : newcity,
                "newAddress" : {
                    "address" : newaddress,
                    "address2" : newaddressTwo,
                    "district" : newdistrict,
                    "postal_code" : newpostCode,
                    "phone" : newphone,
                    "city" : newcity,
                }
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

    function sortAddress(address) {
        return (
            <div>
                <div>Address: {address.address}</div>
                <div>Address Two: {address.address2}</div>
                <div>District: {address.district}</div>
                <div>City: {address.city_id.city}</div>
                <div>Postal Code: {address.postal_code}</div>
                <div>Phone: {address.phone}</div>
            </div>
        );
    }

    function displayValues() {
        if (responses !== undefined && responses.length > 0) {
            return (
            <ul id="AllAddressResponses">
                {responses.map((address) => (
                <li key={address.address_id}>
                    {sortAddress(address)}
                </li>
                ))}
            </ul>
            );
        } 
        else 
            return <p id="AllAddressResponses">No responses to display.</p>;
        
    }

    return (
    <div>
        <div>
            <nav>
                <ul>
                    <li id="AddressGetIndivisual" onClick={() => setCurrentPage("GetIndivisual")}>Search individual</li>
                    <li id="AddressSearchAll" onClick={() => setCurrentPage("SearchAll")}>Search All</li>
                    <li id="AddressAdd" onClick={() => setCurrentPage("Add")}>Add Address</li>
                    <li id="AddressUpdate" onClick={() => setCurrentPage("Update")}>Update Address</li>
                </ul>
            </nav>
            {renderPage()}
        </div>
      
            


    </div>
    );
}
