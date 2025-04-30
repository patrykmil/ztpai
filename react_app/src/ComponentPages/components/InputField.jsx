import styles from './Component.module.css';
import FieldInfo from "../../Authentication/components/FieldInfo.jsx";

const InputField = ({ name, placeholder, field }) => {
    return (
        <div className={styles.field}>
            <input
                placeholder={placeholder}
                id={name}
                value={field.state.value}
                onChange={(e) => field.handleChange(e.target.value)}
                className={styles.input}
            />
            <FieldInfo field={field}></FieldInfo>
        </div>
    );
};

export default InputField;