import styles from "../Component.module.css"


const AdminPopup = ({isVisible, onClose, onBan, onDelete}) => {
    return (
        <div className={`${styles.popup} ${isVisible ? styles.active : ''}`}>
            <div className={styles.popupContent}>
                <span className={styles.close} onClick={onClose}>&times;</span>
                <h2>Admin Actions</h2>
                <button onClick={onBan}>Ban author</button>
                <input type="text" id="reason" placeholder="Enter deletion reason"/>
                <button onClick={onDelete}>Delete</button>
            </div>
        </div>
    );
};

export default AdminPopup;