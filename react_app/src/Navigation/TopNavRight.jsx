import {Link} from 'react-router-dom';
import styles from './Navigation.module.css';

const TopNavRight = ({user}) => (
        !user?.token ? (
            <div className={styles.topNavRight}>
                <Link to="/login" className={styles.rightMenuText}>
                    LOG IN
                </Link>
                <p style={{margin: "0 1rem"}}>or</p>
                <Link to="/register" className={styles.rightMenuText}>
                    REGISTER
                </Link>
            </div>
        ) : (
            <div className={styles.topNavRight}>
                <Link to={`/users/${user.username}`}>
                    <img src={`/public/avatars/${user.avatar}`}  alt={"Avatar"}></img>
                    <p className={styles.rightMenuText}>{user.username}</p>
                </Link>
            </div>
        )
    );

    export default TopNavRight;