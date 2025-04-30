import styles from './Component.module.css';
import FieldInfo from "../../Authentication/components/FieldInfo.jsx";

const SelectField = ({ name, placeholder, field, options, onInput }) => {
    return (
        <div className={styles.field}>
            <input
                placeholder={placeholder}
                list={`${name}-list`}
                id={name}
                value={field.state.value}
                onChange={(e) => field.handleChange(e.target.value)}
                className={styles.input}
                onInput={onInput}
            />
            <datalist id={`${name}-list`} className={styles.datalist}>
                {options.map((option) => (
                    <option key={option.id} value={option.name} />
                ))}
            </datalist>
            <FieldInfo field={field}></FieldInfo>
        </div>
    );
};

export default SelectField;