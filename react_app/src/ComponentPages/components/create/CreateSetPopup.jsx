import styles from "./../Component.module.css";

const CreateSetPopup = ({isVisible, onClose, onSubmit}) => {
    return (
        <div className={`${styles.popup} ${isVisible ? styles.active : ''}`}>
            <div className={styles.popupContent}>
                <span className={styles.close} onClick={onClose}>&times;</span>
                <h2>Create new set</h2>
                <input type="text" id="newSetName" placeholder="Enter new set name"/>
                <button onClick={onSubmit}>Create</button>
            </div>
        </div>
    );
};

export default CreateSetPopup;