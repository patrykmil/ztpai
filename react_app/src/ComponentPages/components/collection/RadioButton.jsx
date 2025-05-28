import styles from "../Component.module.css"
import {useQueryClient} from "@tanstack/react-query";

const RadioButton = ({activeView, setActiveView}) => {
    const queryClient = useQueryClient();
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
                onClick={() => {
                    queryClient.invalidateQueries(["liked_components"]);
                    setActiveView('liked')
                }}
            >
                Liked
            </button>
        </div>
    )
}

export default RadioButton;