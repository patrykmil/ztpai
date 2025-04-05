import Navigation from "./Navigation/Navigation.jsx";
import {Helmet} from "react-helmet";

const HomePage = () => {
    return (
        <>
            <Helmet>
                <title>Home Page</title>
            </Helmet>
            <Navigation/>
            <h1 style={{marginTop: "7rem"}}>Home</h1>
        </>
    )
}

export default HomePage;