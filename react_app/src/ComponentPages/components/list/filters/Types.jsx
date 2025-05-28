import styles from "./../../Component.module.css"

const Types = ({types, setTypes}) => {
    const handleSelectAll = () => {
        const newState = Object.keys(types).reduce((acc, key) => {
            acc[key] = true;
            return acc;
        }, {});
        setTypes(newState);
    };

    const handleSelectNone = () => {
        const newState = Object.keys(types).reduce((acc, key) => {
            acc[key] = false;
            return acc;
        }, {});
        setTypes(newState);
    };

    return (
        <div className={styles.types}>
            <p>Show</p>
            <div>
                <button className={styles.selectCheckboxes} type="button" onClick={handleSelectAll}>All</button>
                <button className={styles.selectCheckboxes} type="button" onClick={handleSelectNone}>None</button>
            </div>
            {Object.keys(types).map((type) => (
                <label key={type} className={styles.searchOptions}>
                    <input
                        type="checkbox"
                        className={styles.checkbox}
                        name="filters"
                        value={type}
                        checked={types[type]}
                        onChange={(e) => setTypes({
                            ...types,
                            [type]: e.target.checked
                        })}
                    />
                    <p>{type.charAt(0).toUpperCase() + type.slice(1)}</p>
                </label>
            ))}
        </div>
    )
}

export default Types;