import {Link} from 'react-router-dom';
import styles from './Navigation.module.css';

const TopNavRight = () => (
    <div className={styles.topNavRight}>
        <Link to="/login" className={styles.rightMenuText}>
            LOG IN
        </Link>
        <p style={{margin: "0 1rem"}}>or</p>
        <Link to="/register" className={styles.rightMenuText}>
            REGISTER
        </Link>
    </div>
);

export default TopNavRight;