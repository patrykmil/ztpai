import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from '@tanstack/react-query-devtools'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./index.css";
import HomePage from "./HomePage.jsx";
import NotFound from "./ErrorPages/NotFound.jsx";
import ComponentList from "./ComponentPages/ComponentList.jsx";
import ComponentPage from "./ComponentPages/ComponentPage.jsx";
import axios from "axios";
import Register from "./Authentication/Register.jsx";
import Login from "./Authentication/Login.jsx";

const router = createBrowserRouter([
    {path: "/", element: <HomePage/>},
    {path: "/components", element: <ComponentList/>},
    {path: "/components/:id", element: <ComponentPage/>},
    {path: "/register", element: <Register/>},
    {path: "/login", element: <Login/>},
    {path: "*", element: <NotFound/>}
])

const queryClient = new QueryClient();

export const api = axios.create({
    baseURL: 'http://localhost:8080'
});

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router}/>
            <ReactQueryDevtools initialIsOpen={false}/>
        </QueryClientProvider>
    </StrictMode>
);