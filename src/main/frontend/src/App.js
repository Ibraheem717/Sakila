import { useState } from "react";
import ActorMenu from "./Actor/MainActor";

export default function App() {
    const [currentPage, setCurrentPage] = useState("home");

    const renderPage = () => {
      switch (currentPage) {
        case "Actor":
            return <ActorMenu />
      }
    };
  return (
    <div>
    <nav>
      <ul>
        <li onClick={() => setCurrentPage("Actor")}>Home</li>
      </ul>
    </nav>
    {renderPage()}
  </div>
  );
}
