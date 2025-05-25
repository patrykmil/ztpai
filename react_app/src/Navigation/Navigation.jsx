import {useState} from 'react';
import TopNavLeft from './TopNavLeft';
import TopNavRight from './TopNavRight';
import styles from './Navigation.module.css';
import useAuthStore from "../Authentication/AuthStore.js";

const Navigation = () => {
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
    const userInfo = useAuthStore()
    return (
        <nav className={styles.nav}>
            <TopNavLeft
                user={userInfo}
                isMobileMenuOpen={isMobileMenuOpen}
                setIsMobileMenuOpen={setIsMobileMenuOpen}
            />
            <TopNavRight user={userInfo}/>
        </nav>
    );
};

export default Navigation;