import {Link} from "react-router-dom";

const ErrorNotFound = () => {
    return (
        <>
            <h1>Error 404</h1>
            <Link to={"/"}>
                <button>Home</button>
            </Link>
        </>
    );
}

export default ErrorNotFound;