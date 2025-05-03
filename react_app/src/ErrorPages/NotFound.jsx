import ErrorTemplate from "./ErrorTemplate.jsx";

const NotFound = () => {
    return <ErrorTemplate code={404} message={"Page Not Found"}/>;
}

export default NotFound;