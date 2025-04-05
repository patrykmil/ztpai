import {useState} from 'react';
import TopNavLeft from './TopNavLeft';
import TopNavRight from './TopNavRight';
import styles from './Navigation.module.css';

const Navigation = ({user}) => {
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

    return (
        <nav className={styles.nav}>
            <TopNavLeft
                isMobileMenuOpen={isMobileMenuOpen}
                setIsMobileMenuOpen={setIsMobileMenuOpen}
            />
            <TopNavRight/>
        </nav>
    );
};

export default Navigation;