import styles from "../Component.module.css";

const ChangeCodeButtons = ({activeTab, setActiveTab}) => (
    <div className={styles.changeCodeButtons}>
        <button
            className={`${styles.changeCode} ${activeTab === "html" ? styles.active : ""}`}
            type="button"
            onClick={() => setActiveTab("html")}
        >
            HTML
        </button>
        <button
            className={`${styles.changeCode} ${activeTab === "css" ? styles.active : ""}`}
            type="button"
            onClick={() => setActiveTab("css")}
        >
            CSS
        </button>
    </div>
);

export default ChangeCodeButtons;