import styles from "./../../Component.module.css"

const FilterButton = ({ searchQuery, types, sorting, setFilter }) => {
    const handleClick = () => {
        setFilter({
            query: searchQuery,
            types: types,
            sorting: sorting
        });
    };

    return (
        <button
            className={styles.filterButton}
            type="button"
            onClick={handleClick}
        >
            Apply
        </button>
    )
}

export default FilterButton;