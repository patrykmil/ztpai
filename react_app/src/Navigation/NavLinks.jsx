import {Link} from 'react-router-dom';
import styles from './Navigation.module.css';

const NavLinks = ({user, isMobileMenuOpen = false}) => {
    console.log(user);
    return (
        <ul className={`${styles.navOptionsList} ${isMobileMenuOpen ? styles.active : ''}`}>
            <li>
                <Link to="/components" className={styles.menuItem}>
                    <img src="/icons/search_thick.svg" alt="Browse Icon"/>
                    Browse
                </Link>
            </li>
            <li>
                <Link
                    to={user?.username ? `/collection/${user.username}` : '/login'}
                    className={styles.menuItem}
                >
                    <img src="/icons/bookmark_fill.svg" alt="Collection Icon"/>
                    Collection
                </Link>
            </li>
            <li>
                <Link to="/create" className={styles.menuItem}>
                    <img src="/icons/create.svg" alt="Create Icon"/>
                    Create
                </Link>
            </li>
            {!user?.token ? (
                <>
                    <li className={styles.mobileOption}>
                        <Link to="/login" className={styles.menuItem}>LOG IN</Link>
                    </li>
                    <li className={styles.mobileOption}>
                        <Link to="/register" className={styles.menuItem}>REGISTER</Link>
                    </li>
                </>
            ) : (
                <li className={styles.mobileOption}>
                    <Link to={`/users/${user.username}`} className={styles.menuItem}>
                        <img src={`/avatars/${user.avatar}`}  alt={"Avatar"} className={styles.avatar}></img>
                    </Link>
                        <p className={styles.rightMenuText} onClick={user.logout}>Logout</p>
                </li>
            )}
        </ul>
    );
}

export default NavLinks;