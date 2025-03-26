import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from '@tanstack/react-query-devtools'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./index.css";
import HomePage from "./HomePage.jsx";
import ErrorNotFound from "./ErrorNotFound.jsx";
import ComponentList from "./ComponentList.jsx";
import axios from "axios";

const router = createBrowserRouter([
    {path: "/", element: <HomePage/>},
    {path: "/components", element: <ComponentList/>},
    {path: "*", element: <ErrorNotFound/>}
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