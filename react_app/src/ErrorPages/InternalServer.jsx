import ErrorTemplate from "./ErrorTemplate.jsx";

const InternalServer = () => {
    return <ErrorTemplate code={500} message={"Internal Server Error"}/>;
}

export default InternalServer;