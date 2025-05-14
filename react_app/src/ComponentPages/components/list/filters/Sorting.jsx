import styles from "./../../Component.module.css"

const Sorting = () => {
    return (
        <div className={styles.sort}>
            <p>Sort by</p>
            <select>
                <option value="Most likes">Most likes</option>
                <option value="Newest">Newest</option>
                <option value="Oldest">Oldest</option>
            </select>
        </div>
    )
}

export default Sorting;