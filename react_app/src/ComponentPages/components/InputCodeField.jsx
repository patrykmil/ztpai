import styles from "./Component.module.css"

const InputCodeField = ({start, language, activeTab, onInput}) => {

    return (
        <textarea
            name={`language-${language}`}
            onInput ={onInput}
            className={`${styles.codeContainer} ${styles.autoResize} ${activeTab === language ? styles.active : ""}`}
            defaultValue={start}
       />
    );
};

export default InputCodeField;
