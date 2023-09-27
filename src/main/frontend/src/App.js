import { useState } from "react";
import ActorMenu from "./Entities/MainActor";
import AddressMain from "./Entities/MainAddress";
import CategoryMain from "./Entities/MainCategory";
import "./App.css"; 

export default function App() {
  const [currentPage, setCurrentPage] = useState("home");
  const webURL = "http://sakiladatabase-env.eba-pmf6c2dy.us-east-1.elasticbeanstalk.com";
  const localURL = "http://localhost:8080/"

  const renderPage = () => {
    switch (currentPage) {
      case "Actor":
        return <ActorMenu mainUrl={webURL} />;
      case "Address":
        return <AddressMain mainUrl={webURL}/>;
      case "Category":
        return <CategoryMain mainUrl={webURL}/>;
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
