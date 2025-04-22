import {Link} from "react-router-dom";
import styles from "./Error.module.css"

const InternalServer = () => {
    return (
        <>
            <div className={styles.content}>
                <h1 className={styles.code}>500</h1>
                <h2 className={styles.message}>Internal Server Error</h2>
                <Link to={"/"}>
                    <button className={styles.homeButton}>Home</button>
                </Link>
            </div>
        </>
    );
}

export default InternalServer;