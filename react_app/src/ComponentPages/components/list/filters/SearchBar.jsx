import styles from "./../../Component.module.css"

const SearchBar = () => {
    return (
        <div className={styles.topSearch}>
            <p className={styles.text}>Browse all elements</p>
            <input className={styles.searchBar} type="text" name="search" placeholder="Search?"/>
        </div>
    )
}

export default SearchBar;