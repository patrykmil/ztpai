import { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [data, setData] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080")
      .then((response) => response.text())
      .then((data) => setData(data))
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  return (
    <div className="App">
      <p>Data from backend: {data}</p>
    </div>
  );
}

export default App;
