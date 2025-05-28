import styles from "./../../Component.module.css"
import useComponentFilterStore from "./FilterStore.js";

const FilterButton = () => {
    const {searchQuery, types, sorting, setFilter} = useComponentFilterStore();

    const handleFilter = () => {
        setFilter({
            query: searchQuery,
            types,
            sorting
        });
    };

    return (
        <button onClick={handleFilter} className={styles.filterButton}>
            Apply
        </button>
    );
};

export default FilterButton;