import styles from "./../../Component.module.css"

const SearchBar = ({ searchQuery, setSearchQuery }) => {

    const showFilterPaneMobile = () => {
        const filterPane = document.querySelector(`.${styles.filter}`);
        filterPane.classList.toggle(styles.active);
    };

    return (
        <div className={styles.topSearch}>
            <button className={styles.filterMobile}
                    type="button"
                    onClick={showFilterPaneMobile}
            >Filters</button>
            <p className={styles.text}>Browse all elements</p>
            <input
                className={styles.searchBar}
                type="text"
                name="search"
                placeholder="Search?"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
            />
        </div>
    )
}
export default SearchBar;