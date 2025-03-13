import {useQuery} from "@tanstack/react-query";
import {Navigate, Route, Routes} from "react-router-dom";
import "./App.css";

function Users() {
    const {data, isLoading, error} = useQuery({
        queryKey: ["userData"],
        queryFn: () =>
            fetch("http://localhost:8080/users")
                .then((response) => response.text())
    });

    if (isLoading) return <div>Loading...</div>;
    if (error) return <div>Error: {error.message}</div>;

    return (
        <div className="App">
            <p>Data from backend: {data}</p>
        </div>
    );
}

function App() {
    return (
        <Routes>
            <Route path="/users" element={<Users/>}/>
            <Route path="/" element={<Navigate to="/users" replace/>}/>
        </Routes>
    );
}

export default App;