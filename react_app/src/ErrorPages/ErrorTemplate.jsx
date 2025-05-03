import {Link} from "react-router-dom";
import styles from "./Error.module.css";

const ErrorTemplate = ({ code, message }) => {
    return (
        <div className={styles.content}>
            <h1 className={styles.code}>{code}</h1>
            <h2 className={styles.message}>{message}</h2>
            <Link to="/">
                <button className={styles.homeButton}>Home</button>
            </Link>
        </div>
    );
};

export default ErrorTemplate;