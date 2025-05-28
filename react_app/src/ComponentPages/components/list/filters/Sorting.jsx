import styles from "./../../Component.module.css"

const Sorting = ({sorting, setSorting}) => {
    return (
        <div className={styles.sort}>
            <p>Sort by</p>
            <select
                value={sorting}
                onChange={(e) => setSorting(e.target.value)}
            >
                <option value="most likes">Most likes</option>
                <option value="newest">Newest</option>
                <option value="oldest">Oldest</option>
            </select>
        </div>
    )
}
export default Sorting;