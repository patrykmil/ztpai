import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {ReactQueryDevtools} from '@tanstack/react-query-devtools'
import {createBrowserRouter, RouterProvider, useNavigate} from "react-router-dom";
import "./index.css";
import HomePage from "./HomePage.jsx";
import NotFound from "./ErrorPages/NotFound.jsx";
import ComponentList from "./ComponentPages/ComponentList.jsx";
import ComponentPage from "./ComponentPages/ComponentPage.jsx";
import axios from "axios";
import Register from "./Authentication/Register.jsx";
import Login from "./Authentication/Login.jsx";
import useAuthStore from "./store/authStore.js";
import ComponentCreate from "./ComponentPages/ComponentCreate.jsx";
import ComponentReplace from "./ComponentPages/ComponentReplace.jsx";

const router = createBrowserRouter([
    {path: "/", element: <HomePage/>},
    {path: "/components", element: <ComponentList/>},
    {path: "/components/:id", element: <ComponentPage/>},
    {path: "/create", element: <ComponentCreate/>},
    {path: "/modify/:id", element: <ComponentReplace/>},
    {path: "/register", element: <Register/>},
    {path: "/login", element: <Login/>},
    {path: "*", element: <NotFound/>}
])

const queryClient = new QueryClient();

export const api = axios.create({
    baseURL: 'http://localhost:8080'
});

api.interceptors.request.use((config) => {
    const token = useAuthStore.getState().token;
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

api.interceptors.response.use((response) => response, (error) => {
    if (error.response && error.response.status === 403) {
        if (window.location.pathname !== '/login') {
            useAuthStore.getState().logout();
            useNavigate("/login");
            alert("Session expired\nLog in to view this panel")
        }
    }
    return Promise.reject(error);
});

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router}/>
            <ReactQueryDevtools initialIsOpen={false}/>
        </QueryClientProvider>
    </StrictMode>
);