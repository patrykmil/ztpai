// import {useState} from 'react';
// import {Link} from 'react-router-dom';
// import styles from './Navigation.module.css';
// import useAuthStore from "../store/authStore.js";
//
// const MobileMenu = () => {
//     const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
//     const userInfo = useAuthStore()
//     return (
//         <>
//             <ul className={`${styles.navOptionsList} ${isMobileMenuOpen ? styles.active : ''}`}>
//                 <li>
//                     <Link to="/components" className={styles.menuItem}>
//                         <img src="/icons/search_thick.svg" alt="Browse Icon"/>
//                         BROWSE
//                     </Link>
//                 </li>
//                 <li>
//                     <Link to={'/login'} className={styles.menuItem}>
//                         <img src="/icons/bookmark_fill.svg" alt="Collection Icon"/>
//                         COLLECTION
//                     </Link>
//                 </li>
//                 <li>
//                     <Link to="/create" className={styles.menuItem}>
//                         <img src="/icons/create.svg" alt="Create Icon"/>
//                         CREATE
//                     </Link>
//                 </li>
//                 {userInfo.token.empty() ? (
//                     <>
//                     <li className={styles.mobileOption}>
//                         <Link to="/login" className={styles.menuItem}>LOG IN</Link>
//                     </li>
//                     <li className={styles.mobileOption}>
//                         <Link to="/register" className={styles.menuItem}>REGISTER</Link>
//                     </li>
//                 </>
//             ) : null}
//             </ul>
//             <button
//                 className={`${styles.mobileMenuIcon} ${styles.menuItem} ${styles.menuShow}`}
//                 onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
//             >
//                 <img src="/icons/menu.svg" alt="Menu Icon"/>
//             </button>
//         </>
//     );
// };
//
// export default MobileMenu;