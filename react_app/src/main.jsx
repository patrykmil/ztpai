import {StrictMode} from "react";
import {createRoot} from "react-dom/client";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import "./index.css";
import HomePage from "./HomePage.jsx";
import ErrorNotFound from "./ErrorNotFound.jsx";
import Components from "./Components.jsx";

const router = createBrowserRouter([
    {path: "/", element: <HomePage/>},
    {path: "/components", element: <Components/>},
    {path: "*", element: <ErrorNotFound/>}
])
const queryClient = new QueryClient();

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <QueryClientProvider client={queryClient}>
            <RouterProvider router={router}/>
        </QueryClientProvider>
    </StrictMode>
);