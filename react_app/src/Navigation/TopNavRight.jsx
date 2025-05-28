import {Link} from 'react-router-dom';
import styles from './Navigation.module.css';

const TopNavRight = ({user}) => (
    !user?.token ? (
        <div className={styles.topNavRight}>
            <Link to="/login" className={styles.rightMenuText}>
                Log in
            </Link>
            <p className={styles.marginSides}>or</p>
            <Link to="/register" className={styles.rightMenuText}>
                Register
            </Link>
        </div>
    ) : (
        <div className={styles.topNavRight}>
            <Link to={`/messages`}>
                <img src={`/avatars/${user.avatar}`} alt={"Avatar"}></img>
            </Link>
            <p className={styles.rightMenuText} onClick={user.logout}>Logout</p>
        </div>
    )
);

export default TopNavRight;