import styles from "../Component.module.css";

const RefreshButton = ({refetch}) => {
    return (
        <button className={styles.refreshButton} onClick={() => refetch()}>Refresh</button>
    )
}

export default RefreshButton