import styles from "../Component.module.css"

const RadioButton = ({ activeView, setActiveView }) => {
    return (
        <div className={styles.radio}>
            <button
                className={`${styles.btn} ${styles.left} ${activeView === 'sets' ? styles.chosen : ''}`}
                type="button"
                onClick={() => setActiveView('sets')}
            >
                Self made
            </button>
            <button
                className={`${styles.btn} ${styles.right} ${activeView === 'liked' ? styles.chosen : ''}`}
                type="button"
                onClick={() => setActiveView('liked')}
            >
                Liked
            </button>
        </div>
    )
}

export default RadioButton;