import {Link} from 'react-router-dom';
import NavLinks from './NavLinks';
import styles from './Navigation.module.css';

const TopNavLeft = ({user, isMobileMenuOpen, setIsMobileMenuOpen}) => (
    <div className={styles.topNavLeft}>
        <Link to="/start">
            <img className={styles.logo} src="/images/logo.svg" alt="Logo"/>
        </Link>
        <NavLinks user={user} isMobileMenuOpen={isMobileMenuOpen}/>
        <button
            className={`${styles.mobileMenuIcon} ${styles.menuItem}`}
            onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
        >
            <img src="/icons/menu.svg" alt="Menu Icon"/>
        </button>
    </div>
);

export default TopNavLeft;