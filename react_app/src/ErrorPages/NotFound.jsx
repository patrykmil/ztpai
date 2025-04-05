import {Link} from "react-router-dom";
import styles from "./Error.module.css"
import {Helmet} from "react-helmet";

const NotFound = () => {
    return (
        <>
            <Helmet>
                <title>Page not found</title>
            </Helmet>
            <div className={styles.content}>
                <h1 className={styles.code}>404</h1>
                <h2 className={styles.message}>Page Not Found</h2>
                <Link to={"/"}>
                    <button className={styles.homeButton}>Home</button>
                </Link>
            </div>
        </>
    );
}

export default NotFound;