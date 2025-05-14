import styles from "./../../Component.module.css"
import {useState} from "react";

const Types = () => {

    const [checkedState, setCheckedState] = useState({
        Buttons: false,
        Inputs: false,
        Checkboxes: false,
        'Radio buttons': false
    });

    const handleSelectAll = () => {
        const newState = Object.keys(checkedState).reduce((acc, key) => {
            acc[key] = true;
            return acc;
        }, {});
        setCheckedState(newState);
    };

    const handleSelectNone = () => {
        const newState = Object.keys(checkedState).reduce((acc, key) => {
            acc[key] = false;
            return acc;
        }, {});
        setCheckedState(newState);
    };

    return (
        <div className={styles.types}>
            <p>Show</p>
            <div>
                <button className={styles.selectCheckboxes} type="button" onClick={handleSelectAll}>All</button>
                <button className={styles.selectCheckboxes} type="button" onClick={handleSelectNone}>None</button>
            </div>
            {Object.keys(checkedState).map((type) => (
                <label key={type} className={styles.searchOptions}>
                    <input
                        type="checkbox"
                        className={styles.checkbox}
                        name="filters"
                        value={type}
                        checked={checkedState[type]}
                        onChange={(e) => setCheckedState({
                            ...checkedState,
                            [type]: e.target.checked
                        })}
                    />
                    <p>{type}</p>
                </label>
            ))}
        </div>
    )
}

export default Types;