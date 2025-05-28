import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./index.css";
import NotFound from "./ErrorPages/NotFound.jsx";
import ComponentList from "./ComponentPages/ComponentList.jsx";
import ComponentPage from "./ComponentPages/ComponentPage.jsx";
import axios from "axios";
import Register from "./Authentication/Register.jsx";
import Login from "./Authentication/Login.jsx";
import useAuthStore from "./Authentication/AuthStore.js";
import ComponentCreate from "./ComponentPages/ComponentCreate.jsx";
import ComponentModify from "./ComponentPages/ComponentModify.jsx";
import ComponentCollection from "./ComponentPages/ComponentCollection.jsx";
import MessageList from "./Messages/MessageList.jsx";
import TokenRefresher from "./Authentication/TokenRefresher.js";

const router = createBrowserRouter([
    {path: "/", element: <ComponentList/>},
    {path: "/components", element: <ComponentList/>},
    {path: "/components/:id", element: <ComponentPage/>},
    {path: "/create", element: <ComponentCreate/>},
    {path: "/modify/:id", element: <ComponentModify/>},
    {path: "/collection/:username", element: <ComponentCollection/>},
    {path: "/register", element: <Register/>},
    {path: "/login", element: <Login/>},
    {path:"/messages", element: <MessageList/>},
    {path: "*", element: <NotFound/>}
])

const queryClient = new QueryClient();

export const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true
});

let isRefreshing = false;
let refreshSubscribers = [];

const processQueue = (error, token = null) => {
    refreshSubscribers.forEach(callback => {
        if (error) {
            callback(error);
        } else {
            callback(token);
        }
    });
    refreshSubscribers = [];
};

api.interceptors.request.use((config) => {
    const token = useAuthStore.getState().token;
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

api.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 &&
            !originalRequest._retry &&
            !originalRequest.url?.includes('/refresh')) {

            if (isRefreshing) {
                return new Promise((resolve, reject) => {
                    refreshSubscribers.push((token) => {
                        if (token) {
                            originalRequest.headers.Authorization = `Bearer ${token}`;
                            resolve(api(originalRequest));
                        } else {
                            reject(error);
                        }
                    });
                });
            }

            originalRequest._retry = true;
            isRefreshing = true;

            try {
                const response = await api.post('/refresh');
                const { token } = response.data;

                useAuthStore.getState().setToken(token);

                originalRequest.headers.Authorization = `Bearer ${token}`;

                processQueue(null, token);

                return api(originalRequest);
            } catch (refreshError) {
                processQueue(refreshError);
                useAuthStore.getState().logout();

                if (window.location.pathname !== '/login' && window.location.pathname !== '/register') {
                    window.location.href = '/login';
                }

                return Promise.reject(refreshError);
            } finally {
                isRefreshing = false;
            }
        }

        if (error.response?.status === 401 &&
            originalRequest.url?.includes('/refresh')) {
            useAuthStore.getState().logout();

            if (window.location.pathname !== '/login' && window.location.pathname !== '/register') {
                window.location.href = '/login';
            }
        }

        return Promise.reject(error);
    }
);

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <TokenRefresher/>
            <RouterProvider router={router}/>
        </QueryClientProvider>
    </StrictMode>
);