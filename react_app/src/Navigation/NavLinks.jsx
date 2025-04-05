import {Link} from 'react-router-dom';
import styles from './Navigation.module.css';

const NavLinks = ({isMobileMenuOpen}) => (
    <ul className={`${styles.navOptionsList} ${isMobileMenuOpen ? styles.active : ''}`}>
        <li>
            <Link to="/components" className={styles.menuItem}>
                <img src={"/public/icons/search_thick.svg"} alt="Browse Icon"/>
                BROWSE
            </Link>
        </li>
        <li>
            <Link to={'/login'} className={styles.menuItem}>
                <img src={"/public/icons/bookmark_fill.svg"} alt="Collection Icon"/>
                COLLECTION
            </Link>
        </li>
        <li>
            <Link to="/create" className={styles.menuItem}>
                <img src={"/public/icons/create.svg"} alt="Create Icon"/>
                CREATE
            </Link>
        </li>
        <li className={styles.mobileOption}>
            <Link to="/login" className={styles.menuItem}>LOG IN</Link>
        </li>
        <li className={styles.mobileOption}>
            <Link to="/register" className={styles.menuItem}>REGISTER</Link>
        </li>
    </ul>
);

export default NavLinks;