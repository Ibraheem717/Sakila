import { useState } from "react";
import ActorMenu from "./Entities/MainActor";
import AddressMain from "./Entities/MainAddress";
import CategoryMain from "./Entities/MainCategory";
import "./App.css"; 

export default function App() {
  const [currentPage, setCurrentPage] = useState("home");
  const webURL = "https://ibraheem.fergcb.uk";
  const localURL = "http://localhost:8080";
  const HOSTS = [webURL, localURL];
  const SELECT = 0;

  const renderPage = () => {
    switch (currentPage) {
      case "Actor":
        return <ActorMenu mainUrl={HOSTS[SELECT]} />;
      case "Address":
        return <AddressMain mainUrl={HOSTS[SELECT]}/>;
      case "Category":
        return <CategoryMain mainUrl={HOSTS[SELECT]}/>;
      default:
        return null;
    }
  };

  return (
    <div className="container">
      <nav>
        <ul>
          <li
            id="ActorMenuSelect"
            onClick={() => setCurrentPage("Actor")}
          >
            Actor
          </li>
          <li
            id="AddressMenuSelect"
            onClick={() => setCurrentPage("Address")}
          >
            Address
          </li>
          <li
            id="CategoryMenuSelect"
            onClick={() => setCurrentPage("Category")}
          >
            Category
          </li>
        </ul>
      </nav>
      {renderPage()}
    </div>
  );
}
